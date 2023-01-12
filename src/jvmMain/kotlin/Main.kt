import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import util.GenshinDataHelper
import util.InternetConnectHelper


/*fun main() = application {
    Window(onCloseRequest = ::exitApplication) {

    }
}*/

fun main() {








    runBlocking {
        getData()
    }





}

suspend fun getData(){
    val cookie = "_MHYUUID=3d0bf323-1092-4c3d-b6b7-59eccff617bc; DEVICEFP_SEED_ID=13ac95ac6893be15; DEVICEFP_SEED_TIME=1673504155998; DEVICEFP=38d7ecca1c6cf; login_uid=80048193; login_ticket=WvXFAnkxXvrNwBB7FuMWoKgLOMZHyZ7rDrT4CNCw"


    val headers = HashMap<String,String>()
    headers["cookie"] = cookie

    val genshinDataHelper = GenshinDataHelper()

    //请求account id
    val accountInfo = genshinDataHelper.getAccountId(cookie)
    //请求tokens
    val tokens = genshinDataHelper.getTokenList(
        accountInfo.data.accountInfo.webLoginToken,
        accountInfo.data.accountInfo.accountId,
        headers
    )

    //拼接新的cookie
    val newCookie = StringBuffer()
    newCookie.append("stuid=${accountInfo.data.accountInfo.accountId};")
    for (token in tokens.data.list) {
        newCookie.append("${token.name}=${token.token};")
    }
    newCookie.append(cookie)

    //将新的cookie放入header
    headers["cookie"] = newCookie.toString()

    val users = genshinDataHelper.getUserInfo(headers)

    println("The json string is :"+Gson().toJson(users))

    val authKey = genshinDataHelper.getAuthKey(newCookie.toString(),users.list.userList[0])

    genshinDataHelper.getData(authKey.authKey,authKey.authKeyVer,users.list.userList[0])
}
