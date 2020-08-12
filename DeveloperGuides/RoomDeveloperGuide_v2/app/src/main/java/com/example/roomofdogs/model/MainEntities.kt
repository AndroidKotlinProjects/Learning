package com.example.roomofdogs.model

import androidx.room.*

@Entity(tableName = "owners")
data class DatabaseOwner (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "owner_id") val ownerId: Int = 0,
    val name: String,
    val address: String
)

data class Qualities (
    val cuteness: String,
    @ColumnInfo(name = "bark_volume") val barkVolume: Int
)

@Entity(tableName = "dogs")
data class DatabaseDog constructor(
    @PrimaryKey val name: String,
    val breed: String,
    val age: Int,
    @Embedded(prefix = "quality_") val qualities: Qualities,
    @ColumnInfo(name = "owner") val ownerId: Int
)

data class OwnerWithDogs(
    @Embedded val owner: DatabaseOwner,
    @Relation(parentColumn = "owner_id", entityColumn = "owner") val dogs: List<DatabaseDog>
)