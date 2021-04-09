package collections

fun main() {

    // USE WHEN YOU WANT TO MAKE A MAP FROM ANY TYPE OF COLLECTION
    // NAMED ASSOCIATE BECAUSE A MAP IS AN ASSOCIATION

    /* Association functions are for building maps from the items in a
    * collection and some lambda function that operates on the items.
    * For example, you can create a map that associates strings with their
    * lengths */
    val greetings = listOf("Hallo", "Servus", "Moin moin")
    val assoc = greetings.associate {
        it to it.length
    }
    println(assoc)

    /* associate creates a map, and so if there are key conflicts then
    * it's last write wins. That's why there is only one book per genre,
    * which happens to be the last book of that genre type in the list */
    Library.books
        .associate {
            //Pair(it.genres.first(), it) // Pair(Genre, Book)
            // you can use the "to" notation instead
            it.genres.first() to it
        }
        .forEach { key, value ->
            //println("$key : $value")
        }

    // use the by variant to define the key
    // here we define the key to be the first genre that the book has
    Library.books
        .associateBy {
            it.genres.first()
        }
        .forEach { key, value ->
            //println("$key : $value")
        }
    greetings
        .associateBy {
            it.length
        }
        .forEach {  key, value ->
            //println("$key : $value")
        }


    // use the with variant to define the value, the element item as
    // a whole will be used as the key
    Library.books
        .associateWith {
            it.genres.first()
        }
        .forEach { key, value ->
            println("$key : $value")
        }
    greetings
        .associateWith {
            it.first().toUpperCase()
        }
        .forEach { key, value ->
            println("$key : $value")
        }
}