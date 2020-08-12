package com.littleboysoftware.runaways.ui

import android.app.Application
import android.provider.ContactsContract
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.littleboysoftware.runaways.model.DatabaseDog
import com.littleboysoftware.runaways.model.DatabaseOwner
import com.littleboysoftware.runaways.model.OwnerWithDogs
import com.littleboysoftware.runaways.model.Qualities
import com.littleboysoftware.runaways.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.random.Random

val dogNames = listOf("boof", "zero", "buddy", "boxer", "abby", "stanley", "oscar", "borris", "shaggy", "scooby", "thunderbolt", "stomper", "chumly", "benny", "chelsea")
val breeds = listOf("poodle", "mastiff", "terrier", "wolfhound", "boxer", "shitsu", "terrier", "great dane", "rotweiler", "dalmation")
val cutenesses = listOf("adorable", "very", "not very", "ugly", "hideous")
val ownerNames = listOf("The Pound", "Nicholas", "Jared", "Brett", "Luke", "Shelley", "Jennifer", "Paul", "Chloe", "Zac", "James", "Angela", "Eli", "Leon", "Jaime", "Archet")
val addresses = listOf("1 Madeup Rd, Fakeville", "3 Intangible Ln, Fakeville", "2 Madeup Rd, Fakeville", "64 Nonexistant St, Fakeville")


class MainViewModel(val repository: MainRepository): ViewModel() {
    val ownersWithDogs: Flow<List<OwnerWithDogs>> = repository.ownersWithDogs

    private var ownerIds: MutableList<Int> = mutableListOf<Int>()

    fun addDog() = viewModelScope.launch {
        val dog = newDog()
        repository.insertDog(dog)
    }

    private fun newDog(): DatabaseDog {
        return DatabaseDog(
            dogNames[Random.nextInt(dogNames.size)],
            breeds[Random.nextInt(breeds.size)],
            Random.nextInt(16),
            Qualities(cutenesses[Random.nextInt(cutenesses.size)], Random.nextInt(11)),
            ownerIds[Random.nextInt(ownerIds.size)]
        )
    }

    fun addOwner() = viewModelScope.launch {
        val owner = newOwner()
        repository.insertOwner(owner)
        ownerIds.add(ownerIds.size+1)
    }

    private fun newOwner(): DatabaseOwner {
        return DatabaseOwner(name = ownerNames[Random.nextInt(ownerNames.size)], address = addresses[Random.nextInt(
            addresses.size)])
    }
}

class MainViewModelFactory(val repository: MainRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T;
    }
}