package beprogressive.uniclient.repo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import beprogressive.uniclient.SettingsSerializer
import com.gmail.beprogressive.it.uniclient.ProtoSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Singleton

class MainRepoImpl(private val settings: DataStore<ProtoSettings>): MainRepo {

//    private val saved get() = settings.data.take(1)

    override suspend fun saveAccessToken(accessToken: String) {
        settings.updateData {
            it.toBuilder().setAccessToken(accessToken).build()
        }
    }

    override suspend fun getSavedAccessToken(): Flow<String> = settings.data.transform { flowCollector ->
        emit(flowCollector.accessToken)
    }
}

@Module
@InstallIn(SingletonComponent::class)
class SettingsModule {

    @Provides
    @Singleton
    fun provideSettings(@ApplicationContext context: Context) = MainRepoImpl(context.dataStore)

    private val Context.dataStore: DataStore<ProtoSettings> by dataStore(
        fileName = "app_settings.proto",
        serializer = SettingsSerializer
    )
}