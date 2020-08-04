package basics

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

fun main() {

    // method 1
    GlobalScope.launch {
        this.doDelayExtension() // this is an extension function, API is a little unclear!
    }

    // method 2
    val hasAScope = HasAScope(CoroutineScope(Dispatchers.Default))
    runBlocking { hasAScope.doDelay() }

    // method 3
    val implementsCoroScope = ImplementsCoroScope(Dispatchers.Default)
    runBlocking { implementsCoroScope.doDelay() }

    // method 4
    runBlocking { doDelayDifferentScope() }

    readLine()
}

/* When you have a function that has a coroutine builder in it, you must have access to a scope in some way in that
* function. This is because all coroutine builders are extension functions of CoroutineScope and need to know which
* scope they should run in. */


/* The first method of getting a reference to the coroutine scope is to make it an extension function. That way, when
* the function is called above in main, it is actually called on the instance of the CoroutineScope that was created
* there, and thus the launch in doDelay knows which scope to run in. This is easy, but the API is unclear i.e. if you
* leave out the "this." then it is hard to see that it is an extension function */
suspend fun CoroutineScope.doDelayExtension() {
    launch {
        delay(1000L)
        println("method1 delay done")
    }
}

/* Class member way of solving the problem. Here we make the function a member of a class which has a reference to the
* coroutine scope for that class. We can then launch off of that reference. */
class HasAScope(val coroScope: CoroutineScope) {
    suspend fun doDelay() {
        coroScope.launch {
            delay(1000L)
            println("method2 delay done")
        }
    }
}

/* Class implementing CoroutineScope. This is the third way of solving the problem. Here our class implements
* CoroutineScope, and is thus a coroutine scope itself. Therefore we an IMPLICITLY launch in this scope. */
class ImplementsCoroScope(override val coroutineContext: CoroutineContext): CoroutineScope {
    suspend fun doDelay() {
        launch {
            delay(1000L)
            println("method3 delay done")
        }
    }
}

/* Different scope. This is the final and worst way of solving the problem. Here we make a completely separate
* CoroutineScope to launch in. Whilst this does let us launch, it means this functions coro is running in a different
* scope to whatever called it, which breaks the structured concurrency paradigm. This is a hacky solution but so long
* as you don't require that structure it is fine to do. */
suspend fun doDelayDifferentScope() {
    CoroutineScope(coroutineContext).launch {
        delay(1000L)
        println("method4 delay done")
    }
}