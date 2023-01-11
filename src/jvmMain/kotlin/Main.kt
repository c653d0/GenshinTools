import util.GenshinDataHelper
import util.InternetConnectHelper


/*fun main() = application {
    Window(onCloseRequest = ::exitApplication) {

    }
}*/

fun main() {
    //var result = StringBuffer()
    val connectHelper = InternetConnectHelper()
    val args = HashMap<String,String>()

    val time = System.currentTimeMillis().toString().substring(0,10)
    //args["t"] = time

    //test args
    val loginTicket = "gtScYNwI0ViPsOBCm8hYnNKfNgfmpFNuJDDNtFkr"
    val uid = "80048193"

    val cookie = "_MHYUUID=2aceab9c-4517-4d87-a252-2f52cf5b1d91; DEVICEFP_SEED_ID=bdf9153fe4943bf3; DEVICEFP_SEED_TIME=1673414485804; DEVICEFP=38d7ecc5e579a; login_uid=80048193; login_ticket=7Z8mpOM6VPo8LBed094D2fJR2NmTl5Ps7koOOvFT"


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


    val authKey = genshinDataHelper.getAuthKey(newCookie.toString(),users.list.userList[0])

    genshinDataHelper.getData(authKey.authKey,authKey.authKeyVer,users.list.userList[0])

}
