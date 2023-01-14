import com.google.gson.Gson
import data.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.runBlocking
import util.FileOperateHelper
import util.GenshinDataHelper


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
    val cookie = ""

    val genshinDataHelper = GenshinDataHelper()

    var gachaType:Int
    val result = ArrayList<ArrayList<FinalWishData>>()

    val userData = genshinDataHelper.getUserData(cookie)
    genshinDataHelper.setList(userData.user.gameUid)
    val analyseDataList = ArrayList<AnalyseData>()

    var gachaTypeString = ""

    repeat(4){
        gachaType = GachaType.typeArray[it]
        gachaTypeString = when(gachaType){
            GachaType.Character -> "角色活动祈愿"
            GachaType.Weapon -> "武器活动祈愿"
            GachaType.Permanent -> "常驻祈愿"
            GachaType.FreshMan -> "新手祈愿"
            else -> "错误类型"
        }
        println("正在获取$gachaTypeString")
        val data = genshinDataHelper.getAllData(cookie,gachaType,userData)
        result.add(genshinDataHelper.mergeData(data,gachaType))
        val analyseData = genshinDataHelper.analyseData(result[it],gachaType)
        analyseDataList.add(analyseData)
        delay(50)
    }

    repeat(4){
        println(analyseDataList[it])
    }

    val dataToFile = DataToFile(
        userData.user.gameUid,
        System.currentTimeMillis().toString(),
        result[0],
        result[1],
        result[2],
        result[3]
    )

    val content = Gson().toJson(dataToFile)

    FileOperateHelper().writeToFile(userData.user.gameUid,content)

}
