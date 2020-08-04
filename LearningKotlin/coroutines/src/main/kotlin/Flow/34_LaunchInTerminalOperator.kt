package Flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

// Imitate a flow of events
private fun events(): Flow<Int> = (1..3).asFlow().onEach { delay(1000) }

fun main() = runBlocking<Unit> {
    events()
        .onEach { event -> println("Event: $event") }
        .launchIn(this) // <--- Launching the flow in a separate coroutine
    println("Done")
}

/* main is a coroutine because we use the runBlocking coroutine builder. Using the launchIn operator allows use to
* launch a separate coroutine. We pass this (the coroutine scope of the main coro) as the context to launch in. and
* thus main will not terminate until the inner coro is completed. The done message however will print immediately as
* the flow collection is occurring in a different coro and so main is free to run still.
*
* Note that launchIn also returns a Job, which can be used to cancel the corresponding flow collection coroutine only
* without cancelling the whole scope or having to join it.*/