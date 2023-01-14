package data

import com.google.gson.annotations.SerializedName

data class FinalWishData(
    var name:String,
    var itemType:String,
    var rankType:String,
    var time:String,
    var total:Int,
    var guarantee:Int
)
