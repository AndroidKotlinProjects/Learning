package com.example.composedroom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WordsViewModel(
    private val database: WordRoomDatabase
): ViewModel() {

    val allWords = database.wordDao().getAllAlphabetical().asLiveData()

    fun insertWord(word: Word) = viewModelScope.launch {
        println("debug: WordsViewModel::insertWord called with Word = ${word.word}")
        database.wordDao().insert(word)
    }

}

class WordViewModelFactory(private val wordsDatabase: WordRoomDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordsViewModel(wordsDatabase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}