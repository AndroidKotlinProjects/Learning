package com.littleboysoftware.roomdeveloperguide.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.littleboysoftware.roomdeveloperguide.database.getDatabase
import com.littleboysoftware.roomdeveloperguide.domain.User
import com.littleboysoftware.roomdeveloperguide.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application): ViewModel() {

    private val repository = UserRepository(getDatabase(application))
    private val _userState: MutableStateFlow<List<User>> = MutableStateFlow(listOf())
    val userState: StateFlow<List<User>> get() = _userState
    val userStateFlow = repository.userFlow

    fun pushUser() = viewModelScope.launch(Dispatchers.IO) {
        repository.addUser()
    }

    fun pullUsers() = viewModelScope.launch(Dispatchers.IO) {
        _userState.value = repository.getUsers()
    }

    fun deleteUsers() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteUsers()
    }

}

class MainViewModelFactory(val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(application) as T;
    }
}
