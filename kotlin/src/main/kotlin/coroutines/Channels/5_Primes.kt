package Channels

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

fun main() = runBlocking {
    var cur = numbersFrom(2) // this launches a new coroutine!
    repeat(10) {
        val prime = cur.receive()
        /* we know 2 is prime so we cant print it before filtering */
        println(prime)
        /* Now filter all multiples of the current prime out of the list (because they aren't prime!) */
        cur = filter(cur, prime) // this launches a new coroutine every time it is called!
    }
    coroutineContext.cancelChildren() // cancel all children to let main finish, didn't need to keep references to them
}

fun CoroutineScope.numbersFrom(start: Int) = produce<Int> {
    var x = start
    while (true) send(x++) // infinite stream of integers from start
}

fun CoroutineScope.filter(numbers: ReceiveChannel<Int>, prime: Int) = produce<Int> {
    for (x in numbers) if (x % prime != 0) send(x)
}

/* Note that the important thing in this program is not the prime sieve logic, instead it is the fact that we can
* launch many coroutines and not have to keep references to them and just call this.coroutineContext.cancelChildren()
* to cancel them all because we launched them all in the calling scope (by making the functions extension functions
* on CoroutineScope). Every time we call numbersFrom() or filter(), which in turn call the produce {} coro builder and
* hence launch a new coro, they are actually doing this.produce {} where this is the coroutine scope from main.  */