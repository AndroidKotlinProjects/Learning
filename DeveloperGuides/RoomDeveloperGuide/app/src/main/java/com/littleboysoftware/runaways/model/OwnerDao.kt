package com.littleboysoftware.runaways.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface OwnerDao {
    @Insert
    suspend fun insertAll(vararg owners: DatabaseOwner)

    @Query("SELECT * FROM owners")
    fun getAll(): Flow<List<DatabaseOwner>>

    @Query("SELECT * FROM owners WHERE name LIKE :ownerName")
    fun _getOwner(ownerName: String): Flow<DatabaseOwner>
    fun getOwner(ownerName: String) = _getOwner(ownerName).distinctUntilChanged()

    @Transaction
    @Query("SELECT * FROM owners")
    fun getOwnersWithDogs(): Flow<List<OwnerWithDogs>>
}