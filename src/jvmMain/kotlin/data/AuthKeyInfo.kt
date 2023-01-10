package data

import com.google.gson.annotations.SerializedName

data class AuthKeyInfo(
    @SerializedName("data")
    val data:AuthKeyData
)

data class AuthKeyData(
    @SerializedName("authkey_ver")
    val authKeyVer:String,
    @SerializedName("authkey")
    val authKey:String
)
