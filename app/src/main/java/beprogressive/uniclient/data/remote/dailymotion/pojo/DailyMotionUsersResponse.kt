package beprogressive.uniclient.data.remote.dailymotion.pojo


import com.google.gson.annotations.SerializedName

data class DailyMotionUsersResponse(
    @SerializedName("explicit")
    val explicit: Boolean,
    @SerializedName("has_more")
    val hasMore: Boolean,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("list")
    val list: List<DailyMotionUserItem>,
    @SerializedName("page")
    val page: Int,
    @SerializedName("total")
    val total: Int
) {
    data class DailyMotionUserItem (
        @SerializedName("id")
        val id: String,
        @SerializedName("screenname")
        val screenname: String
    )
}