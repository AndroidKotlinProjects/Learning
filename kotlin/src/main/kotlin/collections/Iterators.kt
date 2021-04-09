package collections

fun main() {
    // let scopes the captured variable to that block. Anyone reading this code
    // knows that it is only used in this part of the code, and don't have to
    // look out for usages of it elsewhere. Scoping is good!

    // Worst way to iterate. If you forget to call it.next() then you
    // will be stuck in an infinite loop.
    Library.books.iterator().let {
        while (it.hasNext()) {
            val book = it.next()
            //println(book)
        }
    }

    // Medium way, it's little better. Now the iterator is implicitly created
    // for you. This is identical to the above in terms of implementation
    Library.books.let { books ->
        for (book in books) {
            //println(book)
        }
    }

    // The best way (without index). Ctrl-click forEach to see the implementation
    // (it's a high-order extension function)
    Library.books.forEach {
        //println(it)
    }

    // Best way (with index)
    Library.books.forEachIndexed { index, book ->
        println("${index+1} - $book")
    }
}