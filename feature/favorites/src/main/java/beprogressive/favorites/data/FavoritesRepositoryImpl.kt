package beprogressive.favorites.data

import beprogressive.common.model.UserItem
import beprogressive.common.model.UserItemDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class FavoritesRepositoryImpl (private val usersDao: UserItemDao, private val ioDispatcher: CoroutineDispatcher):
    FavoritesRepository {

    override fun observeUsers() = usersDao.observeFavoritesUsers()

    override suspend fun switchUserFavorite(userItem: UserItem) {
        withContext(ioDispatcher) {
            usersDao.switchUserItemFavorite(userItem)
        }
    }
}