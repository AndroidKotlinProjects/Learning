package com.littleboysoftware.roomdeveloperguide.repository

import com.littleboysoftware.roomdeveloperguide.database.DatabaseUser
import com.littleboysoftware.roomdeveloperguide.database.UserDatabase
import com.littleboysoftware.roomdeveloperguide.database.asDomainModel
import com.littleboysoftware.roomdeveloperguide.domain.User
import com.littleboysoftware.roomdeveloperguide.domain.asDatabaseModel
import com.littleboysoftware.roomdeveloperguide.domain.createRandomUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class UserRepository(private val userDatabase: UserDatabase) {

    val userFlow = userDatabase.getUserDao().getAllAsFlow()
        .map<List<DatabaseUser>, List<User>> { it.asDomainModel() }
        .flowOn(Dispatchers.IO)

    suspend fun addUser() {
        userDatabase.getUserDao().insert(createRandomUser().asDatabaseModel())
    }

    suspend fun getUsers(): List<User> {
        return userDatabase.getUserDao().getAll().asDomainModel()
    }

    suspend fun deleteUsers() {
        userDatabase.getUserDao().deleteAll()
    }


}