package beprogressive.uniclient.ui

import android.net.Uri
import androidx.lifecycle.*
import beprogressive.uniclient.data.ClientUser
import beprogressive.uniclient.data.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository, state: SavedStateHandle) :
    ViewModel() {
    private val _clientUser = MutableLiveData<ClientUser>()
    val clientUser: LiveData<ClientUser> = _clientUser

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
        getSavedClientUser()
    }

    private fun getSavedClientUser() {
        viewModelScope.launch {
            repository.getSavedClientUser().collectLatest {
                _clientUser.value = it
            }
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