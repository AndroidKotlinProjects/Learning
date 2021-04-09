package collections

fun main() {

    // standard collections can be sorted by calling sorted() on them
    listOf(4, 6, 7, 8, -4, 66, -30, 0, 1, 2, 2, 1000).sorted()
        .also { println(it) }

    // but this cannot work for user defined types (such as books),
    // because they don't implement the comparable interface
    //Library.books.sorted() // does not compile

    // YOU CAN USE THE SORTEDBY FUNCTION TO SUPPLY A FIELD THAT
    // EXISTS ON THE OBJECT AND DOES IMPLEMENT COMPARABLE TO SORT BY
    Library.books.sortedBy { it.title }
        .also {
            println("Books in alphabetical order;")
            it.forEach { println(it.title) }
        }

    Library.books.sortedBy { it.price }
        .also {
            println("\nBooks sorted by price;")
            it.forEach { println("$${it.price/100}.${it.price%100} - ${it.title}") }
        }

    Library.books.sortedByDescending { it.price }
        .also {
            println("\nBooks sorted by price (descending);")
            it.forEach { println("$${it.price/100}.${it.price%100} - ${it.title}") }
        }

    // reversed and shuffled helper functions also exist
    listOf(1,2,3,4,5)
        .also { println("\noriginal list: $it") }
        .reversed()
        .also { println("reversed list: $it") }
        .shuffled()
        .also { println("shuffled list: $it") }
}