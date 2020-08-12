package com.example.roomofdogs.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.roomofdogs.model.OwnerWithDogs
import com.example.roomofdogs.repository.MainRepository
import kotlinx.coroutines.flow.Flow

class MainViewModel(val repository: MainRepository): ViewModel() {
    val ownersWithDogs: Flow<List<OwnerWithDogs>> = repository.ownersWithDogs
}