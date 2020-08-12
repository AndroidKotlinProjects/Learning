package com.example.roomofdogs.model

import android.content.Context
import androidx.room.Database
import androidx.room.Fts4
import androidx.room.Room
import androidx.room.RoomDatabase

@Fts4
@Database(entities = [DatabaseOwner::class, DatabaseDog::class], version = 1)
abstract class DogDatabase: RoomDatabase() {
    abstract val ownerDao: OwnerDao
}

private lateinit var INSTANCE: DogDatabase

fun getDatabase(context: Context): DogDatabase {
    synchronized(DogDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context, DogDatabase::class.java, "dog_db")
                .build()
        }
        return INSTANCE
    }
}