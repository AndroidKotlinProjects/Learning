package com.littleboysoftware.runaways.repository

import com.littleboysoftware.runaways.model.*
import kotlinx.coroutines.flow.Flow

class MainRepository(val ownerDao: OwnerDao, val dogDao: DogDao) {
    val ownersWithDogs: Flow<List<OwnerWithDogs>> = ownerDao.getOwnersWithDogs()

    suspend fun insertOwner(owner: DatabaseOwner) {
        ownerDao.insertAll(owner)
    }

    suspend fun insertDog(dog: DatabaseDog) {
        dogDao.insertAll(dog)
    }
}