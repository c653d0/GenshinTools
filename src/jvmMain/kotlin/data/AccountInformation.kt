package data

import com.google.gson.annotations.SerializedName

data class AccountInformation(
    val code:String,
    val data:AccountInfoData
)

data class AccountInfoData(
    @SerializedName("account_info")
    val accountInfo:AccountInfo,
)

data class AccountInfo(
    @SerializedName("account_id")
    val accountId:String,
    @SerializedName("weblogin_token")
    val webLoginToken:String
)