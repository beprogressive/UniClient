package beprogressive.uniclient.ui

import android.net.Uri
import androidx.lifecycle.*
import beprogressive.uniclient.data.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: UsersRepository, state: SavedStateHandle) :
    ViewModel() {
    private val _accessToken = MutableLiveData<String>()
    val accessToken: LiveData<String> = _accessToken

    sealed class Event {
        //        object NavigateToSettings: Event()
        object Idle : Event()
        data class Error(val text: String) : Event()
        data class Message(val text: String) : Event()
        object ShowAuth : Event()
    }

    private val _uiEvent: MutableStateFlow<Event> = MutableStateFlow(Event.Idle)
    val uiEvent: StateFlow<Event> = _uiEvent.asStateFlow()

    init {
        getSavedAccessToken()
    }

    private fun getSavedAccessToken() {
        viewModelScope.launch {
//            repository.getSavedAccessToken().collectLatest {
//                _accessToken.value = it
//            }
        }
    }

    fun uiStateDone() {
        _uiEvent.update {
            Event.Idle
        }
    }

    fun showAuth() {
        _uiEvent.update {
            Event.ShowAuth
        }
    }

    fun handleAuth(response: Uri) {
        viewModelScope.launch {
            repository.auth(response)
        }
    }
}