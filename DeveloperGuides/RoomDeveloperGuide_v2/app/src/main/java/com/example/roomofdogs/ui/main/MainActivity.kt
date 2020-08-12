package com.example.roomofdogs.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.State
import androidx.compose.collectAsState
import androidx.lifecycle.ViewModelProvider
import androidx.ui.core.setContent
import androidx.ui.foundation.AdapterList
import androidx.ui.foundation.Text
import androidx.ui.layout.Column
import androidx.ui.material.Divider
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.tooling.preview.Preview
import androidx.ui.tooling.preview.PreviewParameter
import androidx.ui.unit.dp
import com.example.roomofdogs.model.DatabaseOwner
import com.example.roomofdogs.model.OwnerWithDogs
import com.example.roomofdogs.model.getDatabase
import com.example.roomofdogs.repository.MainRepository
import com.example.roomofdogs.ui.compose.RoomOfDogsTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomOfDogsTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    OwnershipList(ownersWithDogs = viewModel.ownersWithDogs)
                }
            }
        }
        val database = getDatabase(this)
        val repository: MainRepository = MainRepository(database.ownerDao)
        viewModel = MainViewModel(repository = repository)
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RoomOfDogsTheme {
        Greeting("Android")
    }
}

@Composable
fun OwnerWithDogsItem(ownerWithDogs: OwnerWithDogs) {
    Column {
        Text(text = ownerWithDogs.owner.name)
        ownerWithDogs.dogs.forEach {
            Text(text = "    ${it.name}, who is a ${it.breed}, and is ${it.age} years old.")
        }
        Divider(thickness = 2.dp)
    }
}

@Composable
fun OwnershipList(ownersWithDogs: Flow<List<OwnerWithDogs>>) {
    val relationships = ownersWithDogs.collectAsState(listOf())
    AdapterList(data = relationships.value) { ownerWithDogs ->
        OwnerWithDogsItem(ownerWithDogs)
    }
}