package collections

fun main() {

    // PARTITION IS A FILTER THAT RETURNS BOTH A LIST OF ALL
    // ELEMENTS THAT PASSED THE PREDICATE, AND ANOTHER LIST
    // OF THOSE THAT DIDN'T. IT RETURNS PAIR<LIST, LIST>

    val (fiction, nonFiction) = Library.books
        .partition { it.genres.all { it is Genre.Fiction } }

    println("Fiction books;\n$fiction")
    println("Non-fiction books;\n$nonFiction")

    val words = "The quick brown fox jumped over the lazy dog".split(" ")
    val (small, large) = words.partition { it.length <= 3 }
    println(small.joinToString())
    println(large.joinToString())

}