package beprogressive.uniclient.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import beprogressive.common.model.UserItem
import beprogressive.uniclient.data.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val repository: UsersRepository) : ViewModel() {

    val entryList = repository.observeUsers()

    private val _currentItem: MutableLiveData<UserItem?> = MutableLiveData(null)
    val currentItem: LiveData<UserItem?> = _currentItem

    sealed class Event {
//        object NavigateToSettings: Event()
        data class ShowSnackBar(val text: String): Event()
        data class ShowToast(val text: String): Event()
    }

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            repository.collectUsers()
        }
    }

    fun switchUserFavorite(item: UserItem) {
        viewModelScope.launch {
            repository.switchUserFavorite(item)
        }
    }
}