package beprogressive.common.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "Users", primaryKeys = ["userId", "apiKey"])
open class UserItem(
    open val userId: String,
    open val name: String,
    open var imageUrl: String = "",
    open val apiKey: ApiKey
) {

    var description = ""

    var isFavorite = false

    enum class ApiKey {
        GitHub, DailyMotion
    }
}

fun String.toApiKey() = when (this) {
    "GitHub" -> UserItem.ApiKey.GitHub
    else -> UserItem.ApiKey.DailyMotion
}

@Dao
abstract class UserItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(userItem: UserItem): Long

    /** Transaction for save local params */
    @Transaction
    open fun upsertItem(userItem: UserItem) {
        val id: Long = insert(userItem)
        if (id == -1L) {
            update(userItem.userId, userItem.apiKey, userItem.description, userItem.imageUrl)
        }
    }

    @Query("UPDATE Users SET imageUrl = :imageUrl, description = :description WHERE userId = :userId AND apiKey = :apiKey")
    abstract fun update(
        userId: String,
        apiKey: UserItem.ApiKey,
        description: String,
        imageUrl: String
    )

    @Delete
    abstract fun delete(userItem: UserItem): Int

    @Query("DELETE FROM Users")
    abstract fun clearData()

    @Query("SELECT * FROM Users ORDER BY name")
    abstract fun observeUsers(): LiveData<List<UserItem>>

    @Query("SELECT * FROM Users")
    abstract fun getUsers(): List<UserItem>

    @Query("SELECT * FROM Users WHERE userId = :userId")
    abstract fun getUserById(userId: String): UserItem?

    @Query("SELECT * FROM Users WHERE userId = :userId")
    abstract fun observeUserById(userId: String): LiveData<UserItem?>

    @Query("SELECT * FROM Users WHERE name = :name")
    abstract fun observeUsersByName(name: String): LiveData<List<UserItem>>

    @Query("SELECT * FROM Users WHERE name = :name")
    abstract fun getUsersByFullName(name: String): List<UserItem>

    @Query("SELECT * FROM Users WHERE isFavorite = 1")
    abstract fun observeFavoritesUsers(): LiveData<List<UserItem>>

    fun switchUserItemFavorite(userItem: UserItem) {
        updateFavoriteUser(userItem.userId, userItem.apiKey, !userItem.isFavorite)
    }

    @Query("UPDATE Users SET isFavorite = :isFavorite WHERE userId = :userId AND apiKey = :apiKey")
    abstract fun updateFavoriteUser(userId: String, apiKey: UserItem.ApiKey, isFavorite: Boolean)
}