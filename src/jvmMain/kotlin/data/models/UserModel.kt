package data.models

import com.google.gson.annotations.SerializedName

data class UserModel(
    val self: String,
    val accountId: String,
    val emailAddress: String,
    val displayName: String?,
    val avatarUrls: UserAvatarsUrl?
)

data class UserAvatarsUrl(
    @SerializedName("48x48")
    val fortyEight:String?,

    @SerializedName("24x24")
    val twentyFour:String?,

    @SerializedName("16x16")
    val sixteen:String?,

    @SerializedName("32x32")
    val thirtyTwo:String?
)
