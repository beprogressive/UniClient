package beprogressive.uniclient.data.remote

import beprogressive.common.model.UserItem


interface DataCollector {
    suspend fun collectUsers()

    suspend fun auth(type: UserItem.ApiKey, response: String)
}