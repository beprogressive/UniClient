package beprogressive.favorites.di

import beprogressive.common.data.AppDatabase
import beprogressive.favorites.data.FavoritesRepository
import beprogressive.favorites.data.FavoritesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher


@InstallIn(ViewModelComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideFavoritesRepository(
        database: AppDatabase,
        ioDispatcher: CoroutineDispatcher
    ): FavoritesRepository {
        return FavoritesRepositoryImpl(
            database.userItemDao(), ioDispatcher
        )
    }
}