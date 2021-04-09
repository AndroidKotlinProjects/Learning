package ContextAndDispatchers

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    /* Job's are stored inside the coroutine Context, alongside other things like the dispatcher. You can access it
    * like this if you didn't store a reference to it when launching. */
    println("My job is ${coroutineContext[Job]}")

    /* isActive is just shorthand for coroutineContext[Job]?.isActive */
    println(isActive)
    println(coroutineContext[Job]?.isActive)
    cancel()
    println(isActive)
    println(coroutineContext[Job]?.isActive)
}

/* Note that the exception that gets thrown by this program is completely normal. Coroutines are supposed to throw
* a JobCancellationException when they are cancelled. */