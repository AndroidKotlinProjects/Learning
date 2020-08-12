package com.littleboysoftware.runaways.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface DogDao {

    @Insert
    suspend fun insertAll(vararg dogs: DatabaseDog): List<Long>

    @Delete
    fun deleteAll(vararg dogs: DatabaseDog): Int

    @Update
    fun updateDog(dog: DatabaseDog): Int

    @Query("SELECT * FROM dogs")
    fun getDogs(): Flow<List<DatabaseDog>>

    @Query("SELECT * FROM dogs WHERE name LIKE :dogName")
    fun _getDog(dogName: String): Flow<DatabaseDog>
    fun getDog(dogName: String) = _getDog(dogName).distinctUntilChanged()
}