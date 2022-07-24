package beprogressive.favorites.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import beprogressive.common.model.UserItem
import beprogressive.favorites.data.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val repository: FavoritesRepository): ViewModel() {

    private val _uiEvent: MutableStateFlow<Event> = MutableStateFlow(Event.Idle)
    val uiEvent: StateFlow<Event> = _uiEvent.asStateFlow()

    val entryList = repository.observeUsers()

    private val _currentItem: MutableLiveData<UserItem?> = MutableLiveData(null)
    val currentItem: LiveData<UserItem?> = _currentItem

    sealed class Event {
        object Idle : Event()
        data class Error(val text: String) : Event()
        data class Message(val text: String) : Event()
        data class ShowDetails(val item: UserItem) : Event()
    }

    fun switchUserFavorite(item: UserItem) {
        viewModelScope.launch {
            repository.switchUserFavorite(item)
        }
    }

    fun uiStateDone() {
        _uiEvent.update {
            Event.Idle
        }
    }
}