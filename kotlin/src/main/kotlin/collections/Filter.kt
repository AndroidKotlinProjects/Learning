package collections

fun main() {

    // THE TESTING PREDICATE FUNCTIONS "ANY", "NONE",  AND "ALL" ARE
    // REALLY GOOD FOR USING WITHIN THE FILTER LAMBDA WHEN THE THING
    // YOU ARE FILTERING ON IS A LIST (SEE BELOW EXAMPLES).

    Library.books
        .filter {
            it.authors.size > 1
        }
        .map {
            "\"${it.title}\" written by ${it.authors.map { it.name }.joinToString(", ")}"
        }
        .forEach {
            //println(it)
        }

    // the above map can be shortened by using the transformation function on joinToString
    println("Books with more than 1 author;")
    Library.books
        .filter {
            it.authors.size > 1
        }
        .map {
            "\"${it.title}\" written by ${it.authors.joinToString(", ") { it.name } }"
        }
        .forEach {
            println(it)
        }

    println("\nNon-fiction books;")
    val nonFictionBooks = Library.books
        .filter {
            it.genres.all { it is Genre.NonFiction }
        }
        .map {
            "\"${it.title}\" has genre: ${it.genres}"
        }
        .also { // must call also otherwise return type would be Unit
            it.forEach {
                println(it)
            }
        }

    println("\nFiction books;")
    val fictionBooks = Library.books
        .filter {
            it.genres.none {
                it is Genre.NonFiction
            }
        }
        .map { "\"${it.title}\" has genre: ${it.genres}" }
        .also { it.forEach { println(it) } }

    println("\nBoth fiction and non-fiction;")
    val factAndFictionBooks = Library.books
        .filter { it.genres.any { it is Genre.NonFiction } }
        .filter { it.genres.any { it is Genre.Fiction } }
        .map { "\"${it.title}\" has genre: ${it.genres}" }
        .also { it.forEach { println(it) } }

    // YOU CAN ALSO USE YOUR OWN PREDICATE INSTEAD OF THE
    // NONE, ALL, ANY FUNCTIONS, ESPECIALLY WHEN THE ITEMS
    // YOU ARE TESTING ON AREN'T A CONTAINER
    println("\nSome words;")
    val words = "Hello my name is Nicholas and I like to program".split(" ")
    words.filter { it.length < 5 }.forEach { println(it) }
}
