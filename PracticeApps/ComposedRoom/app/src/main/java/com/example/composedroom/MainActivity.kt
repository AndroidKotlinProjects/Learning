package com.example.composedroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.example.composedroom.ui.theme.ComposedRoomTheme


class MainActivity : ComponentActivity() {

    private val wordViewModel: WordsViewModel by viewModels {
        WordViewModelFactory((application as WordsApplication).wordsDatabase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposedRoomTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    App(wordViewModel)
                }
            }
        }
    }
}

@Composable
fun App(
    vm: WordsViewModel
) {
    var text by remember { mutableStateOf("") }
    val words: List<Word> by vm.allWords.observeAsState(listOf())
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Row() {
            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it
                },
                label = { Text("test label") }
            )
            Button(
                onClick = {
                    if (text.isNotBlank()) {
                        vm.insertWord(Word(text))
                        text = ""
                    }
                }
            ) {
                Text("Add")
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
        )
        LazyColumn() {
            items(words) { WordDbItem ->
                Text(WordDbItem.word)
            }
        }
    }
}
