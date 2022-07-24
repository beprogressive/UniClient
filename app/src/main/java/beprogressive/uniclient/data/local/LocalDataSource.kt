package beprogressive.uniclient.data.local

import androidx.lifecycle.LiveData
import beprogressive.common.model.UserItem

interface LocalDataSource {

    fun observeUsers(): LiveData<List<UserItem>>

    fun observeUser(userId: String): LiveData<UserItem?>

    suspend fun switchUserFavorite(userItem: UserItem)
}