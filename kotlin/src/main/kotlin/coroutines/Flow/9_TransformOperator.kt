package Flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

private suspend fun performRequest(request: Int): String {
    delay(1000) // imitate long-running asynchronous work
    return "response $request"
}

fun main() = runBlocking<Unit> {
    (1..3).asFlow() // a flow of requests
        /* remember that this transform operator is not suspending and will return very fast. The actual emits and
        * calling of the request function will only happen when the returned transformed flow is collected below. */
        .transform { request ->
            emit("Making request $request") // emit this message to the flow to alert that the request is going
            emit(performRequest(request)) // do the request and emit its result when it is done
        }
        .collect { response -> println(response) }
}

/* Among the flow transformation operators, the most general one is called transform. It can be used to imitate simple
* transformations like map and filter, as well as implement more complex transformations. Using the transform operator,
* we can emit arbitrary values an arbitrary number of times. */