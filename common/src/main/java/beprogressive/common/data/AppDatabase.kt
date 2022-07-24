package beprogressive.common.data

import androidx.room.Database
import androidx.room.RoomDatabase
import beprogressive.common.model.UserItem
import beprogressive.common.model.UserItemDao

@Database(entities = [UserItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userItemDao(): UserItemDao
}