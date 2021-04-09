package functional.intro_article

fun drop(string: String, char: Char): String? = if (string[0] == char) string.drop(1) else null

fun zero(string: String): String? = drop(string, '0')
fun one(string: String): String? = drop(string, '1')

fun runSequence(string: String?, funcs: List<(String) -> String?>): String? {
    return string?.let {
        if (funcs.isEmpty()) string
        else runSequence(funcs[0](string), funcs.drop(1))
    } ?: null
}

fun main() {
    runSequence("10010011", listOf(::one, ::zero, ::zero, ::one, ::zero, ::zero, ::one))
        .also { println(it) }
    runSequence("1011", listOf(::one, ::one, ::one))
        .also { println(it) }
}