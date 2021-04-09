package Flow

/* Long-running CPU-consuming code might need to be executed in the context of Dispatchers.Default and UI-updating code
* might need to be executed in the context of Dispatchers.Main. Usually, withContext() is used to change the context in
* the code using Kotlin coroutines, but code in the flow { ... } builder has to honor the context preservation property
* and is not allowed to emit from a different context. We saw this in the previous sample. So how do we solve it, see
* below; */

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

private fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

private fun simple(): Flow<Int> = flow {
    for (i in 1..3) {
        Thread.sleep(100) // pretend we are computing it in CPU-consuming way
        log("Emitting $i")
        emit(i) // emit next value
    }
}.flowOn(Dispatchers.Default) // RIGHT way to change context for CPU-consuming code in flow builder

fun main() = runBlocking<Unit> {
    simple().collect { value ->
        log("Collected $value")
    }
}

/* OUTPUT;
[DefaultDispatcher-worker-1] Emitting 1
[main] Collected 1
[DefaultDispatcher-worker-1] Emitting 2
[main] Collected 2
[DefaultDispatcher-worker-1] Emitting 3
[main] Collected 3 */

/* As you can see, the flowOn(Dispatchers.x) function should be used instead of withContext(Dispatchers.x) */