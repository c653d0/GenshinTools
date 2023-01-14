package data

import com.google.gson.annotations.SerializedName

data class WishResult(
    @SerializedName("data")
    val data:WishResultContent
)

data class WishResultContent(
    @SerializedName("list")
    var list:ArrayList<WishResultData>
)

data class WishResultData(
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
)
{
    override fun toString():String{
        return "name=$name\n" +
                "\titemType=$itemType\n" +
                "\trankType=$rankType\n" +
                "\ttime=$time"
    }

    override fun equals(other: Any?): Boolean {
        other as FinalWishData
        return time == other.time && name == other.name
    }
}

