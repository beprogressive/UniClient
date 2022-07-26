package beprogressive.uniclient.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import beprogressive.uniclient.data.remote.github.GitHubDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel :
    ViewModel() {

    data class LoginUiState(
        val gitHubAuth: Boolean = false,
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val isUserLoggedIn: Boolean = false
    )

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

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