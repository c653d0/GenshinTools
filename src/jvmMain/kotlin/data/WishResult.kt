package data

import com.google.gson.annotations.SerializedName

data class WishResult(
    @SerializedName("data")
    val data:WishResultContent
)

data class WishResultContent(
    @SerializedName("list")
    val list:ArrayList<WishResultData>
)

data class WishResultData(
    @SerializedName("uid")
    val uid:String,
    @SerializedName("gacha_type")
    val gachaType:String,
    @SerializedName("item_id")
    val itemId:String,
    @SerializedName("count")
    val count:String,
    @SerializedName("time")
    val time:String,
    @SerializedName("name")
    val name:String,
    @SerializedName("item_type")
    val itemType:String,
    @SerializedName("rank_type")
    val rankType:String,
    @SerializedName("id")
    var id:String
){
    override fun toString():String{
        return "name=$name\n" +
                "\titemType=$itemType\n" +
                "\trankType=$rankType\n" +
                "\ttime=$time"
    }
}

