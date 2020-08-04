package Flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

private suspend fun performRequest(request: Int): String {
    println("async request is being performed...")
    delay(1000) // imitate long-running asynchronous work
    return "response $request"
}

fun main() = runBlocking<Unit> {
    (1..3).asFlow() // a flow of requests
        .map { request -> performRequest(request) }
        .collect { response -> println(response) }

    println("starting second flow")
    /* We firstly take the range of ints 4..6 and turn it into a flow of Ints with the asFlow() builder */
    println("creating original flow")
    val originalFlow: Flow<Int> = (4..6).asFlow()
    /* Then we use map to map each Int in the original flow to a function call that returns a string, thus the newly
    * transformed flow is a flow of strings. Notice that this returns very fast (the performRequest function is not
    * actually called here, you can think of it like the map has tacked peformedRequest(it) onto the end of the
    * flow {} definition before it returns. This is also why the type is also now String */
    println("applying map operator")
    val transformedFlow: Flow<String> = originalFlow.map { performRequest(it) }
    println("collecting transformed flow")
    transformedFlow.collect { println(it) }
}

/* An operator (e.g. map) is not a suspending function itself. It works quickly, returning the definition of a new
* transformed flow. You can think of the operator as being tacked onto the end of the flow { } definition. For this
* reason, operators are also cold i.e. you take a flow, transform it with some operator functions, then you call
* collect on it to actually start the flow.
*
* We stated that operators themselves are not suspending, but they can call suspending functions. Notice above that
* map very quickly returns the transformed flow. The performRequest function is actually only called when we collect
* the flow. */