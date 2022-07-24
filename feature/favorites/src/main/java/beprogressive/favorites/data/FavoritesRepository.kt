package beprogressive.favorites.data

import androidx.lifecycle.LiveData
import beprogressive.common.model.UserItem

interface FavoritesRepository {
    fun observeUsers(): LiveData<List<UserItem>>
    suspend fun switchUserFavorite(userItem: UserItem)
}