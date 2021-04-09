package collections

fun main() {

    // USE FLATTEN TO TURN A COLLECTION OF COLLECTIONS WHICH HAVE ELEMENTS OF
    // TYPE T, INTO A SINGLE LIST<T>

    /* To get a list of authors, rather than a list of a list of authors, you
    * use the flatten operator. This takes a collection of a collection and just
    * returns it as one list i.e. [[1,2], [3,4], [5,6]] -> [1,2,3,4,5,6] */
    val authors = Library.books
        .map {
            it.authors
        }
        // the first map return List<List<Author>>
        // flatten makes it a List<Author>
        .flatten()
        .map {
            it.name
        }

    /* In the previous sample, we had to perform a map after doing the flatten
    * to just get the author name, rather than the whole object. This is quite
    * a common pattern (flatten then map), so much so that flatMap exists, which
    * does this very thing in one go i.e. you take the map you would have done
    * after calling flatten and put it inside flatMap */
    val authorNames = Library.books
        .map { it.authors }
        .flatMap { authors ->
            authors.map { it.name }
        }

    println(authorNames)

}