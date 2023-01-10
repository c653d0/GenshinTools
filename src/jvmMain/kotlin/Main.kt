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

    val cookie = "_MHYUUID=42cf0628-4abf-4b84-ba8a-afe0d673993c; UM_distinctid=1855823b22b260-0dfdcfe6269ccf-7a575473-144000-1855823b22cffa; mi18nLang=zh-cn; DEVICEFP_SEED_ID=78ebdea191ef74cc; DEVICEFP_SEED_TIME=1672585729956; _ga=GA1.1.408361308.1672585719; account_mid_v2=0vuvyd2iqz_mhy; account_id_v2=80048193; DEVICEFP=38d7ecc1823e8; _ga_KS4J8TXSHQ=GS1.1.1673332964.4.0.1673332965.0.0.0; login_uid=80048193; login_ticket=NyrR9GW4cnZpGE9zyDWff6wegLV0TEUjAg5D7HYe"


    GenshinDataHelper().getAuthKey(cookie)
    println()

}
