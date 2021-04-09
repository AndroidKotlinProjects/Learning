package AndroidDevsYoutube

import kotlinx.coroutines.*
import kotlin.coroutines.EmptyCoroutineContext

fun main() {
    /* Starts a coroutine on the main thread. Useful for setting UI (UI can only be set from main thread). Note that
     * this will error if you try and run it because Main is an android specific thing. */
    GlobalScope.launch(Dispatchers.Main) {

    }

    // Starts a coroutine on a background thread. Useful for long running or intensive calculations
    GlobalScope.launch(Dispatchers.Default) {

    }

    // Starts a coroutine on a background thread. Useful for IO operations e.g. reading files, network, database
    GlobalScope.launch(Dispatchers.IO) {

    }

    // Starts the coroutine on the current thread, but then suspends it and it will later resume from any thread.
    GlobalScope.launch(Dispatchers.Unconfined) {

    }

    // Starts a coroutine on a new custom thread, MyThread.
    GlobalScope.launch(newSingleThreadContext("MyThread")) {

    }

    /* This is the default parmater that gets used when you don't pass anything. This defers the choosing of context
     * to the coroutine builder i.e. in this case, the launch coroutine builder will specify the context (thread) to
     * run in, which is almost always default-dispatcher-worker#x. */
    GlobalScope.launch(EmptyCoroutineContext) {

    }
}

/* Main: this is the main thread on android that is used for setting the UI. This can only be used in android projects
 * and will crash otherwise. */

/* Unconfined: executes coroutine immediately on the current thread and later resumes it in whatever thread called
 * resume. It is usually a good fit for things like intercepting regular non-suspending API or invoking
 * coroutine-related code from blocking world callbacks. */