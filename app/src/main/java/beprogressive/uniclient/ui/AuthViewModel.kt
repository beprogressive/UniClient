package beprogressive.uniclient.ui

import androidx.lifecycle.*
import beprogressive.uniclient.data.UsersRepository
import beprogressive.uniclient.data.remote.github.GitHubDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: UsersRepository, state: SavedStateHandle) :
    ViewModel() {

    data class LoginUiState(
        val gitHubAuth: Boolean = false,
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val isUserLoggedIn: Boolean = false
    )

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

//    val user: LiveData<UserItem> = repository


    fun loginButtonClicked() {
        viewModelScope.launch {
            _uiState.value = LoginUiState(gitHubAuth = true)
        }
    }

    fun clearUiState() {
        viewModelScope.launch {
            _uiState.value = LoginUiState()
        }
    }

    fun getAuthUrl(): String {
        return GitHubDataSource.getAuthUrl()
    }
}