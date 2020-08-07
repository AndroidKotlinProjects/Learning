package com.littleboysoftware.roomdeveloperguide.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.littleboysoftware.roomdeveloperguide.domain.User

@Entity
data class DatabaseUser constructor(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0, // default value so we don't have to pass it as a parameter when calling .asDatabaseModel() functions
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String
)

fun DatabaseUser.asDomainModel(): User {
    return User(this.firstName, this.lastName)
}

fun List<DatabaseUser>.asDomainModel(): List<User> {
    return map {
        User(it.firstName, it.lastName)
    }
}