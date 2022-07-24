package beprogressive.common.model

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun toApiKey(value: String?): UserItem.ApiKey {
        return when (value) {
            UserItem.ApiKey.GitHub.name -> UserItem.ApiKey.GitHub
            else -> UserItem.ApiKey.DailyMotion
        }
    }

    @TypeConverter
    fun fromApiKey(value: UserItem.ApiKey): String {
        return value.name
    }
}