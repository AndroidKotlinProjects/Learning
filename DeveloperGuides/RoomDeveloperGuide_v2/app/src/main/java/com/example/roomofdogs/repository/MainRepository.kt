package com.example.roomofdogs.repository

import com.example.roomofdogs.model.OwnerDao
import com.example.roomofdogs.model.OwnerWithDogs
import kotlinx.coroutines.flow.Flow

class MainRepository(val ownerDao: OwnerDao) {
    val ownersWithDogs: Flow<List<OwnerWithDogs>> = ownerDao.getOwnersWithDogs()
}