package Flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/* Notice that simple() is not a suspend function but we are calling delay() inside the flow {}. Therefore the flow{}
* must be a coroutine or suspend function. */
private fun simple(): Flow<Int> = flow {
    (1..3).forEach {
        delay(1000) // replace me with Thread.sleep() and it will become blocking!!!
        emit(it)
    }
}

fun main() = runBlocking<Unit> {
    launch {
        (1..3).forEach {
            println("launched coro not blocked if this prints")
            delay(1000)
        }
    }
    simple().collect() {
        println("Just collected value $it from the flow")
    }
}

/* OUTPUT:
launched coro not blocked if this prints
Just collected value 1 from the flow
launched coro not blocked if this prints
Just collected value 2 from the flow
launched coro not blocked if this prints
Just collected value 3 from the flow
*/

/* As you can see from the output, a new value is retrieved from the flow every second, but at the same time a
* coroutine is running on the same Scope/Context as main (proving main thread is not blocked whilst the flow computes
* the next value in the sequence). */

/* IMPORTANT NOTES:
- A builder function for Flow type is called flow.
- Code inside the flow { ... } builder block can suspend BUT simple() is not marked with suspend modifier.
- Values are emitted from the flow using emit function.
- Values are collected from the flow using collect function. */