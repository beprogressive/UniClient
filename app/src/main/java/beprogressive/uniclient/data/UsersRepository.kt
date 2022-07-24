package beprogressive.uniclient.data

import android.net.Uri
import androidx.lifecycle.LiveData
import beprogressive.common.model.UserItem

interface UsersRepository {

    suspend fun collectUsers()

    fun observeUsers(): LiveData<List<UserItem>>

    fun observeUser(userId: String): LiveData<UserItem?>

    suspend fun switchUserFavorite(userItem: UserItem)
}