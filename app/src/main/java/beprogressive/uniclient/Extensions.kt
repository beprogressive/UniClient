package beprogressive.uniclient

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.gmail.beprogressive.it.uniclient.ProtoSettings
import timber.log.Timber

inline fun <reified T> T.log(message: String) {
    Timber.v(message)
}

val Context.dataStore: DataStore<ProtoSettings> by dataStore(
    fileName = "app_settings.proto",
    serializer = SettingsSerializer
)
