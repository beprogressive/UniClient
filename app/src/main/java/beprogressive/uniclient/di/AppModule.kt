package beprogressive.uniclient.di

import android.content.Context
import beprogressive.common.data.AppDatabase
import beprogressive.uniclient.data.*
import beprogressive.uniclient.data.local.LocalDataSource
import beprogressive.uniclient.data.local.LocalDataSourceImpl
import beprogressive.uniclient.data.remote.DataCollector
import beprogressive.uniclient.data.remote.RemoteDataCollector
import beprogressive.uniclient.data.remote.RemoteDataSource
import beprogressive.uniclient.data.remote.dailymotion.DailyMotionDataSource
import beprogressive.uniclient.data.remote.github.GitHubDataSource
import beprogressive.uniclient.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LocalSourceData

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class GHSourceData

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class DMSourceData

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class CollectorData

    @Singleton
    @CollectorData
    @Provides
    fun provideDataCollector(
        @AppModule.GHSourceData gitHubDataSource: RemoteDataSource,
        @AppModule.DMSourceData dailyMotionDataSource: RemoteDataSource,
        database: AppDatabase,
    ): DataCollector {
        return RemoteDataCollector(
            gitHubDataSource, dailyMotionDataSource, database.userItemDao()
        )
    }

    @Singleton
    @LocalSourceData
    @Provides
    fun provideLocalDataSource(
        @ApplicationContext context: Context,
        database: AppDatabase,
        ioDispatcher: CoroutineDispatcher
    ): LocalDataSource {
        return LocalDataSourceImpl(
            context.dataStore,
            database.userItemDao(),
            ioDispatcher
        )
    }

    @Singleton
    @GHSourceData
    @Provides
    fun provideGitHubDataSource(): RemoteDataSource {
        return GitHubDataSource
    }

    @Singleton
    @DMSourceData
    @Provides
    fun provideDailyMotionDataSource(): RemoteDataSource {
        return DailyMotionDataSource
    }

//    @Singleton
//    @Provides
//    fun provideDataBase(@ApplicationContext context: Context): AppDatabase {
//        return Room.databaseBuilder(
//            context.applicationContext,
//            AppDatabase::class.java,
//            "database.db"
//        ).build()
//    }

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}


/**
 * The binding for UsersRepository is on its own module so that we can replace it easily in tests.
 */
@Module
@InstallIn(ViewModelComponent::class)
object UsersRepositoryModule {
    @Provides
    fun provideUsersRepository(
    @AppModule.CollectorData remoteDataCollector: DataCollector,
    @AppModule.LocalSourceData localDataSource: LocalDataSource,
    ): UsersRepository {
        return UsersRepositoryImpl(remoteDataCollector, localDataSource)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
object AuthRepositoryModule {
    @Provides
    fun provideAuthRepository(
        @AppModule.CollectorData remoteDataCollector: DataCollector,
        @AppModule.LocalSourceData localDataSource: LocalDataSource,
    ): AuthRepository {
        return AuthRepositoryImpl(remoteDataCollector, localDataSource)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
object MainRepositoryModule {
    @Provides
    fun provideMainRepository(
        authRepository: AuthRepository,
        @AppModule.CollectorData remoteDataCollector: DataCollector,
        @AppModule.LocalSourceData localDataSource: LocalDataSource,
    ): MainRepository {
        return MainRepositoryImpl(authRepository, remoteDataCollector, localDataSource)
    }
}

//@Module
//@InstallIn(SingletonComponent::class)
//class SettingsModule {
//
//    @Provides
//    @Singleton
//    fun provideSettings(@ApplicationContext context: Context) = MainRepoImpl(context.dataStore)
//
//    private val Context.dataStore: DataStore<ProtoSettings> by dataStore(
//        fileName = "app_settings.proto",
//        serializer = SettingsSerializer
//    )
//}

//@Module
//@InstallIn(SingletonComponent::class)
//object DataCollectorRepositoryModule {
//    @Singleton
//    @Provides
//    fun provideDataCollector(
//        @AppModule.GHDataSource gitHubDataSource: DataSource,
//        @AppModule.DMDataSource dailyMotionDataSource: DataSource,
//        ioDispatcher: CoroutineDispatcher
//    ): DataCollector {
//        return RemoteDataCollector(gitHubDataSource, dailyMotionDataSource, ioDispatcher)
//    }
//}


//@InstallIn(SingletonComponent::class)
//@Module
//class DatabaseModule {
//    @Provides
//    fun provideUserItemDao(appDatabase: AppDatabase): UserItemDao {
//        return appDatabase.userItemDao()
//    }
//
//    @Provides
//    @Singleton
//    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
//        return Room.databaseBuilder(
//            appContext,
//            AppDatabase::class.java,
//            "database.db"
//        ).build()
//    }
//}

//@InstallIn(ViewModelComponent::class)
//@Module
//abstract class RepositoryModule {
//    @Binds
//    abstract fun provideDetailsRepo(impl: DetailsRepoImpl): DetailsRepo
//
//    @Binds
//    abstract fun provideUserRepo(impl: UserRepoImpl): UserRepo
//
//    @Binds
//    abstract fun provideAuthRepo(impl: AuthRepoImpl): AuthRepo
//
//    @Binds
//    abstract fun provideMainRepo(impl: MainRepoImpl): MainRepo
//}
//
//@InstallIn(SingletonComponent::class)
//@Module
//abstract class DataSourceModule {
//    @Binds
//    abstract fun provideDataSource(impl: WebDataSource): DataSource
//}
//
//@InstallIn(SingletonComponent::class)
//@Module
//class HTTPProviderModule {
//    @Provides
//    @Singleton
//    fun provideRequestQueue(@ApplicationContext appContext: Context): RequestQueue {
//        return Volley.newRequestQueue(appContext)
//    }
//}