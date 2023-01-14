package data

data class DataToFile(
    val uid:String,
    val time:String,
    val freshmanList:ArrayList<FinalWishData>,
    val permanentList:ArrayList<FinalWishData>,
    val characterList:ArrayList<FinalWishData>,
    val weaponList:ArrayList<FinalWishData>,
)
