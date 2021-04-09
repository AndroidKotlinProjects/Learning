package com.example.jetpackcomposebasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsStory()
        }
    }
}

@Composable
fun NewsStory() {
    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(R.drawable.header),
                contentDescription = null,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Another rainy day in Sydney. It seems to be raining " +
                    "lots these days. When will it stop? I don't know. It sucks " +
                    "going to work when it is raining because I have to ride my " +
                    "motorbike",
                style = typography.h6,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis)
            Text(
                text = "A house floats down the river in Taree",
                style = typography.body2
            )
            Text(
                text = "Man attempts to make an app in 7 days",
                style = typography.body2
            )
        }
    }
}

@Preview
@Composable
fun PreviewNewsStory() {
    NewsStory()
}

@Composable
fun MessageList(messages: List<String>) {
    Column() {
        messages.forEach {
            Text(it)
        }
    }
}

@Preview
@Composable
fun PreviewMessageList() {
    MessageList(messages = listOf(
        "This is finally fucking working",
        "Why does it have to be so dogshite",
        "Why are there no official posts about this stupid shit"
    ))
}

@Composable
fun ClickCounter(clicks: Int, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("I've been clicked $clicks times!")
    }
}

@Preview
@Composable
fun PreviewClickCounter() {
    ClickCounter(0) {
        println("on click ran")
    }
}