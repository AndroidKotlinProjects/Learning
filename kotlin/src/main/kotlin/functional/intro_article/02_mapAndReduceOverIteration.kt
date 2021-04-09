package functional.intro_article

fun main() {

    val names = "Mary Isla Sam".split(" ")

    // non-functional iterations
    val nameLengths = mutableListOf<Int>()
    names.forEach {
        nameLengths.add(it.length)
    }
    for (nameLength in nameLengths) {
        println(nameLength)
    }

    // functional (no variables, and no mind-compiling)
    "Mary Isla Sam"
        .split(" ")
        .map { it.length }
        .forEach { println(it) }

}