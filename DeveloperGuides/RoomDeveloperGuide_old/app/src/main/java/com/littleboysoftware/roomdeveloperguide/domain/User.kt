package com.littleboysoftware.roomdeveloperguide.domain

import android.provider.ContactsContract
import androidx.room.Database
import com.littleboysoftware.roomdeveloperguide.database.DatabaseUser
import com.littleboysoftware.roomdeveloperguide.database.getDatabase
import kotlin.random.Random

val firstNames = listOf("Nicholas", "Beth", "Lucy", "Shelley", "Paul", "Jared", "Anna", "Brent", "Logan", "Kennith")
val lastNames = listOf("Quinn", "Wentworth", "Diamond", "Fletcher", "Himmel", "Tabbel", "Sopa")

class User(
    var firstName: String,
    var lastName: String
) {
    fun changeFirstName(newName: String) { firstName = newName }
    fun changeLastName(newName: String) { lastName = newName }
    fun generateProfile(): String {
        return "Hello, my name is $firstName $lastName."
    }
}

fun User.asDatabaseModel(): DatabaseUser {
    println("debug: transforming user to db model!")
    val dbUser = DatabaseUser(firstName = firstName, lastName = lastName)
    println("debug: transformed to db model!")
    return dbUser
}

fun createRandomUser(): User {
    println("debug: creating the random user!")
    val user = User(
        firstName = firstNames[Random.nextInt(firstNames.size)],
        lastName = lastNames[Random.nextInt(lastNames.size)]
    )
    println("debug: random user ${user.firstName} ${user.lastName} created!")
    return user
}