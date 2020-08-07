package com.littleboysoftware.roomdeveloperguide.database

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    fun insert(vararg databaseUsers: DatabaseUser)

    @Delete
    fun delete(vararg databaseUsers: DatabaseUser)

    @Query("SELECT * FROM databaseuser")
    fun getAll(): List<DatabaseUser>

    @Query("SELECT * FROM databaseuser")
    fun getAllAsFlow(): Flow<List<DatabaseUser>>

    @Query("SELECT * FROM databaseuser WHERE uid IN (:userIds)")
    fun getAllInIdRange(vararg userIds: Int): List<DatabaseUser>

    @Query("SELECT * FROM databaseuser WHERE first_name LIKE :firstName AND last_name LIKE :lastName LIMIT 1")
    fun findByName(firstName: String, lastName: String): DatabaseUser

    @Query("DELETE FROM databaseuser")
    fun deleteAll()
}

private lateinit var INSTANCE: UserDatabase

@Database(entities = arrayOf(DatabaseUser::class), version = 1)
abstract class UserDatabase: RoomDatabase() {
    abstract fun getUserDao(): UserDao
}

fun getDatabase(context: Context): UserDatabase {
    synchronized(UserDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context, UserDatabase::class.java, "user_db")
                .build()
        }
        return INSTANCE
    }
}
