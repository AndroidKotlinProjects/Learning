package com.littleboysoftware.roomwordsample

import kotlinx.coroutines.flow.Flow

class WordRepository(private val wordDao: WordDao) {

    val allWords: Flow<List<Word>> = wordDao.getAllAlphabetical()

    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }

}