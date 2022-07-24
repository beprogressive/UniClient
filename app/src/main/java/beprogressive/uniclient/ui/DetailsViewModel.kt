package beprogressive.uniclient.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import beprogressive.uniclient.data.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(repository: UsersRepository, state: SavedStateHandle): ViewModel() {
    val user = repository.observeUser(state.get<String>("userId")!!)
}