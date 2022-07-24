package beprogressive.uniclient.data

import beprogressive.common.model.UserItem

object ClientUser {
    var userName: String = ""
    private set
    var accessToken: String = ""
        private set
    var apiType: UserItem.ApiKey = UserItem.ApiKey.GitHub
        private set

    fun setUser(userName: String, accessToken: String, apiType: UserItem.ApiKey): ClientUser {
        this.userName = userName
        this.accessToken = accessToken
        this.apiType = apiType
        return this
    }

}