package data

import com.google.gson.annotations.SerializedName

data class GenshinUserInfo(
    @SerializedName("retcode")
    val retCode:String,
    @SerializedName("message")
    val message:String,
    @SerializedName("data")
    val list:GenshinUserList
)

data class GenshinUserList(
    @SerializedName("list")
    val userList:ArrayList<GenshinUserData>
)

data class GenshinUserData(
    @SerializedName("game_biz")
    val gameBiz:String,
    @SerializedName("region")
    val region:String,
    @SerializedName("game_uid")
    val gameUid:String,
    @SerializedName("nickname")
    val nickName:String,
    @SerializedName("level")
    val level:String,
    @SerializedName("region_name")
    val regionName: String
)