package com.example.roomofdogs.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface OwnerDao {
    @Insert
    fun insertAll(vararg owners: DatabaseOwner)

    @Query("SELECT * FROM owners")
    fun getAll(): Flow<List<DatabaseOwner>>

    @Query("SELECT * FROM owners WHERE name LIKE :ownerName")
    fun _getOwner(ownerName: String): Flow<DatabaseOwner>
    fun getOwner(ownerName: String) = _getOwner(ownerName).distinctUntilChanged()

    @Transaction
    @Query("SELECT * FROM owners")
    fun getOwnersWithDogs(): Flow<List<OwnerWithDogs>>
}