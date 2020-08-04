package CancellationAndTimeouts

import kotlinx.coroutines.*

/* In a long-running application you might need fine-grained control on your background coroutines. For example, a user
 might have closed the page that launched a coroutine and now its result is no longer needed and its operation can be
 cancelled. The launch function returns a Job that can be used to cancel the running coroutine: */

fun main() = runBlocking<Unit> {

    // the user clicks the link to visit the web-page
    clickLink()

    // the data for the webpage must be fetched
    val job = GlobalScope.launch(Dispatchers.IO) {
        repeat(1_000) { i ->
            delay(1000L)
            println("Request for webpage data has been running for ${i+1} seconds...")
        }
    }

    // user waits for webpage but becomes impatient and closes it
    delay(5000L)
    println("User has been waiting for 5 seconds and is fed-up.")
    closePage()

    // we may as well cancel the data request now because the page is closed so we don't need the data
    job.cancel() // cancel it (or more technically tell it to cancel itself - cooperative cancelling)
    job.join() // now join it to wait until it really is cancelled
    //job.cancelAndJoin() // you can also use this one liner, which combines the above two function calls

    println("data request was cancelled. Browsing elsewhere.")
}

fun clickLink() {
    println("The user has clicked the webpage link.")
}

fun closePage() {
    println("The user has closed the page.")
}