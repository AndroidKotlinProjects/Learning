package com.littleboysoftware.stateflowexample

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@ExperimentalCoroutinesApi
class MainViewModel: ViewModel() {
    /* Similar to LiveData, we create a private mutable reference for internal use, and a
    * non-private immutable reference for users to use. */
    private val _countState = MutableStateFlow<Int>(0)
    val countState: StateFlow<Int> = _countState

    fun incrementCount() = ++_countState.value
    fun decrementCount() = --_countState.value
}