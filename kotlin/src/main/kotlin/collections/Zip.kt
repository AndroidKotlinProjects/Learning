package collections

fun main() {

    // PICTURE THE ZIP FUNCTION LIKE A ZIPPER ON A JACKET. THE ELEMENTS
    // ARE THE TEETH ON THE ZIP AND THEY ARE PAIRED UP WHEN YOU PULL
    // THE ZIPPER (CALL THE ZIP FUNCTION). IF YOU DON'T PROVIDE A
    // TRANSFORMATION LAMBDA THEN THE TWO COLLECTIONS ARE TURNED INTO
    // A LIST OF PAIRS BY DEFAULT

    val numericNumbers = listOf(1, 2, 3, 4, 5)
    val wordNumbers = "one two three four five".split(" ")

    val zippedNumbers = numericNumbers.zip(wordNumbers)
        .forEach {
            println("${it.first} is spelt ${it.second}")
        }

    // you can also use infix function call notation
    val infixZip = (wordNumbers zip numericNumbers)
        .map {
            var res = "${it.first} tally looks like this: "
            res += "|".repeat(it.second)
            res
        }
        .forEach {
            println(it)
        }

    // and you can use a transformation function to store something
    // other than just pairs in the resulting list
    val transformedZip = numericNumbers
        .zip(wordNumbers) { first, second ->
            var cap = second.toUpperCase()
            "$first - $cap"
        }
        .forEach {
            println(it)
        }

}