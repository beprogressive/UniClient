package beprogressive.uniclient.data

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import beprogressive.common.R
import beprogressive.common.model.UserItem

object ClientUser {
    var userName: String = ""
    private set
    var accessToken: String = ""
        private set
    var userImage: String = ""
        private set
    var apiType: UserItem.ApiKey = UserItem.ApiKey.GitHub
        private set

    fun setUser(userName: String, accessToken: String, userImage: String, apiType: UserItem.ApiKey): ClientUser {
        this.userName = userName
        this.accessToken = accessToken
        this.userImage = userImage
        this.apiType = apiType
        return this
    }

}