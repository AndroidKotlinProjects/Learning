package Flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

// Imitate a flow of events
private fun events(): Flow<Int> = (1..3).asFlow().onEach { delay(1000) }

fun main() = runBlocking<Unit> {
    events()
        .onEach { event -> println("Event: $event") }
        .collect() // <--- Collecting the flow waits
    println("Done, moving on!")
}

/* Notice that the Done message wont print until all 3 numbers are collected from the flow. */