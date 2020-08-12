package com.littleboysoftware.roomwordsample

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WordViewModel(application: Application): AndroidViewModel(application) {
    private val repository: WordRepository
    val allWords: Flow<List<Word>>

    init {
        val wordDao = WordRoomDatabase.getDatabase(application, viewModelScope).wordDao
        repository = WordRepository(wordDao)
        allWords = repository.allWords
    }

    fun insertWord(word: Word) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(word)
    }

    fun testViewModelScopeLifetime() = viewModelScope.launch {
        while (true) {
            println("debug: viewmodel coro still running...")
            delay(500L)
        }
    }

    override fun onCleared() {
        super.onCleared()
        println("debug: viewmodel onCleared was called!")
    }
}