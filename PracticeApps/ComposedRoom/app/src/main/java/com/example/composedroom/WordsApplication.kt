package com.example.composedroom

import android.app.Application

class WordsApplication: Application() {
    val wordsDatabase by lazy { WordRoomDatabase.getDatabase(this) }
}