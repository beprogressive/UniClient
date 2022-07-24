package beprogressive.uniclient.data.remote

import beprogressive.common.model.UserItem
import beprogressive.uniclient.data.ClientUser


interface DataCollector {
    suspend fun collectUsers()

    suspend fun auth(type: UserItem.ApiKey, response: String): ClientUser?

    fun getClientUser(type: UserItem.ApiKey, token: String): ClientUser?
}