package data

import com.google.gson.annotations.SerializedName

data class Tokens(
    @SerializedName("retcode")
    val retCode:String,
    @SerializedName("message")
    val message:String,
    @SerializedName("data")
    val data:TokensData
)

data class TokensData(

    val list:ArrayList<TokenInfo>
)

data class TokenInfo(
    @SerializedName("name")
    val name:String,
    @SerializedName("token")
    val token:String
)
