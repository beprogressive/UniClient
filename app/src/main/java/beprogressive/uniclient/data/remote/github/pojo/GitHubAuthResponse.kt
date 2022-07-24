package beprogressive.uniclient.data.remote.github.pojo


import com.google.gson.annotations.SerializedName

data class GitHubAuthResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("scope")
    val scope: String,
    @SerializedName("token_type")
    val tokenType: String
)