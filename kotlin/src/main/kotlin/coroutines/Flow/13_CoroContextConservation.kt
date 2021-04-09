package Flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

private fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

private fun simple(): Flow<Int> = flow {
    log("Started simple flow")
    for (i in 1..3) {
        emit(i)
    }
}

fun main() = runBlocking<Unit> {
    /* This is called from the main coro which runs on the main thread, and so the collection will happen on main too */
    simple().collect { value -> log("Collected $value") }

    /* Now we change context to the IO dispatcher which uses the DefaultDispatcher-worker-x thread pool. Thus the
    * collection will happen on one of these threads. */
    withContext(Dispatchers.IO) {
        simple().collect { value -> log("Collected $value") }
    }
}

/* Collection of a flow always happens in the context of the coroutine that called the terminal operator. The code in
* the flow { ... } builder also runs in the same context. */