package com.example.jetpackcomposebasics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Text
import androidx.ui.graphics.Color
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxHeight
import androidx.ui.layout.padding
import androidx.ui.material.Button
import androidx.ui.material.Divider
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.tooling.preview.Preview
import androidx.ui.tooling.preview.PreviewParameter
import androidx.ui.unit.dp
import com.example.jetpackcomposebasics.ui.JetpackComposeBasicsTheme
import com.example.jetpackcomposebasics.ui.MyApplicationTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                GreetingList()
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable() () -> Unit) {
    MyApplicationTheme() {
        Surface(color = Color.DarkGray) {
            content()
        }
    }

}

@Preview(name = "all")
@Composable
fun previewAll() {
    MyApp {
        GreetingList()
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!",
        modifier = Modifier.padding(24.dp),
        style = MaterialTheme.typography.body1.copy(color = Color.Yellow))
}


@Composable
fun DoubleGreeting() {
    Column {
        Greeting("This is the first greeting!")
        Divider(color = Color.Black)
        Greeting(name = "This is the second greeting!")
    }
}

@Composable
fun GreetingList(names: List<String> = listOf("Arnold", "Brent", "Charlie")) {
    Column(Modifier.fillMaxHeight()) {
        Column(Modifier.weight(1f)) {
            names.forEach {
                Greeting(name = it)
                Divider(color = Color.Black)
            }
        }
        val counter = state {0}
        CounterButton(counter.value) { newValue ->
            counter.value = newValue
        }
    }

}

/* This Composable uses state hoisting i.e. it has the state passed in from the caller
* so that the caller has access to it as well. */
@Composable
fun CounterButton(count: Int, updateCount: (Int) -> Unit) {
    Button(onClick = { updateCount(count+1) },
            backgroundColor = if (count % 2 == 0) Color.Red else Color.Green) {
        Text("$count clicks")
    }
}