package util

import com.google.gson.Gson
import data.*
import java.net.URLEncoder
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class GenshinDataHelper {
    fun getAccountId(cookie:String):AccountInformation{
        var result = AccountInformation("", AccountInfoData(AccountInfo("","")));
        val string:String
        val time = getCurrentTime()
        val url = "https://webapi.account.mihoyo.com/Api/login_by_cookie?t=$time"

        val headers = HashMap<String,String>()
        headers["cookie"] = cookie

        val httpHelper = InternetConnectHelper()
        string = httpHelper.httpConnectByGet(url,5000,headers)
        try {
            val gson = Gson()
            result = gson.fromJson(string,AccountInformation::class.java)
        }catch (e:Exception){
            println("Class: GenshinDataHelper; Function: getAccountId; occur error:$e")
        }
        return result
    }

    fun getTokenList(webLoginToken:String, accountId:String,headers:Map<String,String>):Tokens{
        var result = Tokens("","", TokensData(ArrayList()))

        val url = "https://api-takumi.mihoyo.com/auth/api/getMultiTokenByLoginTicket?login_ticket=" +
                "$webLoginToken&token_types=3&uid=$accountId"
        val httpHelper = InternetConnectHelper()
        val string = httpHelper.httpConnectByGet(url, 5000, headers)
        try {
            result = Gson().fromJson(string,Tokens::class.java)
        }catch (e:Exception){
            println("GenshinDataHelper: getTokenList occur error $e")
        }

        return result
    }

    fun getUserInfo(headers: Map<String, String>):GenshinUserInfo{
        var userInfo = GenshinUserInfo("","", GenshinUserList(ArrayList()))
        val url = "https://api-takumi.mihoyo.com/binding/api/getUserGameRolesByCookie?game_biz=hk4e_cn"
        val string = InternetConnectHelper().httpConnectByGet(url,5000,headers)
        try {
            userInfo = Gson().fromJson(string,GenshinUserInfo::class.java)
        }catch (e:Exception){
            println("GenshinDataHelper.Class: getUserInfo() occur error: $e")
        }
        return userInfo
    }

    fun getAuthKey(newCookie: String, user:GenshinUserData):AuthKeyData{
        var result = AuthKeyInfo(AuthKeyData("",""))

        val headers = HashMap<String,String>()

        headers.remove("cookie")
        headers["Content-Type"] = "application/json; charset=utf-8"
        headers["Host"] = "api-takumi.mihoyo.com"
        headers["Accept"] = "application/json, text/plain, */*"
        headers["Referer"] = "https://webstatic.mihoyo.com"
        headers["x-rpc-app_version"] = "2.28.1"
        headers["x-rpc-client_type"] = "5"
        headers["x-rpc-device_id"] = "CBEC8312-AA77-489E-AE8A-8D498DE24E90"
        //headers["CBEC8312-AA77-489E-AE8A-8D498DE24E90"] = "com.mihoyo.hyperion"
        headers["DS"] = getDs()
        headers["Cookie"] = newCookie.toString()

        val paramData = HashMap<String,String>();
        paramData["auth_appid"] = "webview_gacha"
        paramData["game_biz"] = user.gameBiz
        paramData["game_uid"] = user.gameUid
        paramData["region"] = user.region

        val string = InternetConnectHelper().httpConnectByPost(
            "https://api-takumi.mihoyo.com/binding/api/genAuthKey",
            5000,
            paramData,
            headers
        )

        try {
            result = Gson().fromJson(string,AuthKeyInfo::class.java)
        }catch (e:Exception){
            println("GenshinDataHelper: getAuthKey occur error : $e")
        }

        return result.data
    }


    fun getData(authKey:String, authKeyVer: String, userData:GenshinUserData){
        val header = HashMap<String,String>()
        val gachaType = 301
        var endId = "0"
        var page=0
        var size=20

        val wishList = ArrayList<WishResult>()
        var wishResult:WishResult = WishResult(WishResultContent(ArrayList<WishResultData>()))

        while (true){
            val key = readln()
            if(key == "l"){
                page++
                if(wishResult.data.list.size!=0){
                    endId = wishResult.data.list[wishResult.data.list.size-1].id
                }
                var gameVersion = "CNRELiOS3.0.0_R10283122_S10446836_D10316937"
                val url = "https://hk4e-api.mihoyo.com/event/gacha_info/api/getGachaLog?" +
                        "win_mode=fullscreen&authkey_ver=$authKeyVer&sign_type=2&auth_appid=webview_gacha&init_type=$gachaType&" +
                        "lang=zh-cn&device_type=mobile&game_version=$gameVersion&" +
                        "plat_type=ios&authkey=${URLEncoder.encode(authKey,"UTF-8")}&region=${userData.region}&" +
                        "&game_biz=${userData.gameBiz}&gacha_type=${gachaType}&page=$page&size=$size&end_id=$endId"
                val string = InternetConnectHelper().httpConnectByGet(url, 5000, header)
                println(string)
                wishResult = Gson().fromJson(string,WishResult::class.java)
                if(wishResult.data.list.size == 0){
                    break
                }
                wishList.add(wishResult)
            }

            if(page>100)
                break
        }

        for (wishContentList in wishList) for (wishResultData in wishContentList.data.list) {
            println(wishResultData)
        }

    }


    private fun getCurrentTime():String {
        return System.currentTimeMillis().toString().substring(0,10)
    }

    private fun getDs():String{
        val n = "ulInCDohgEs557j0VsPDYnQaaz6KJcv5"
        //val n = "xV8v4Qu54lUKrEYFZkJhB8cuOh9Asafs"
        val i = getCurrentTime()
        val r = (100001..200000).random().toString()
        val c = md5Encode("salt=$n&t=$i&r=$r")
        return "${i},${r},${c}"
    }

    private fun md5Encode(text: String): String {
        try {
            //获取md5加密对象
            val instance: MessageDigest = MessageDigest.getInstance("MD5")
            //对字符串加密，返回字节数组
            val digest:ByteArray = instance.digest(text.toByteArray())
            var sb : StringBuffer = StringBuffer()
            for (b in digest) {
                //获取低八位有效值
                var i :Int = b.toInt() and 0xff
                //将整数转化为16进制
                var hexString = Integer.toHexString(i)
                if (hexString.length < 2) {
                    //如果是一位的话，补0
                    hexString = "0" + hexString
                }
                sb.append(hexString)
            }
            return sb.toString()

        } catch (e: NoSuchAlgorithmException) {
            println("Class: GenshinDataHelper; Method: md5Encode; occur error: $e")
        }

        return ""
    }

}