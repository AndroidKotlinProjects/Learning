package ContextAndDispatchers

import kotlinx.coroutines.*

/* All coroutine builders like launch and async accept an optional CoroutineContext parameter that can be used to
* explicitly specify the dispatcher for the new coroutine and other context elements. The dispatcher is what decides
* which thread the coroutine will run on. Some dispatchers have access to just one thread, others have access to a
* pool of shared threads (i.e. default dispatcher has worker-1, worker-2 etc.) */

fun main() = runBlocking<Unit> {
    /* launching without providing a specific context will mean you inherit the context (and thus dispatcher) of the
    * CoroutineScope you are launching from. In this case, we inherit the dispatcher that the main runBlocking
    * coroutine is using, which is the main dispatcher/main thread. */
    launch {
        println("main runBlocking      : I'm working in thread ${Thread.currentThread().name}")
    }
    /* Unconfined behaves like above, inheriting the dispatcher of the scope it was called in. For this reason, it
    * also starts on the main thread. The difference is that it is not confined to the main thread. After the first
    * suspension it resumes the coroutine in the thread that the suspending function was running in. */
    launch(Dispatchers.Unconfined) { // not confined -- will work with main thread (but then maybe not later on)
        println("Unconfined            : I'm working in thread ${Thread.currentThread().name}")
    }
    /* This is the default dispatcher, which means that it is the dispatcher used when you do GlobalScope.launch {} and
    * don't supply a dispatcher argument to launch. The default dispatcher has a pool of threads that it can dispatch
    * the coroutine to. */
    launch(Dispatchers.Default) { // will get dispatched to DefaultDispatcher
        println("Default               : I'm working in thread ${Thread.currentThread().name}")
    }
    /* This creates a whole new thread for the coroutine to run in. Creating a new thread is expensive. The thread
    * must be released when it is no longer needed, or stored in a variable for access later on. */
    launch(newSingleThreadContext("MyOwnThread")) { // will get its own new thread, EXPENSIVE
        println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
    }
}