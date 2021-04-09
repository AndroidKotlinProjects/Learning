package scopeFunctions

import kotlin.random.Random

data class FictionalPerson(
    var firstName: String,
    var lastName: String,
    var age: Int,
    var address: Address,
    var gender: String? = null,
    var hasFacebook: Boolean = false,
    var hasTwitter: Boolean = false,
    var occupation: String? = null
)
data class Address(
    var number: Int,
    var street: String,
    var city: String,
    var state: String,
    var zipCode: Int
)

fun main() {
    val homer = FictionalPerson(
        firstName = "Homer",
        lastName = "Simpson",
        age = 39,
        address = Address(
            number = 742,
            street = "Evergreen Terrace",
            city = "Springfield",
            state = "Oregon",
            zipCode = 97475
        )
    )

    // let prevent you having to use homer.address... all the time
    // and you can return the new address (last line is returned)
    val homersNewAddress = homer.address.let {
        it.number = 20
        it.street = "Madeup Lane"
        it.city = "Sydney"
        it.state = "NSW"
        it.zipCode = 2032
        it
    }
    println("Homer's new address is: $homersNewAddress")
    println(homer)

    // you can also use it instead of doing if (x != null) {...}
    var marge: FictionalPerson? = FictionalPerson(
        firstName = "Marge",
        lastName = "Simpson",
        age = 38,
        address = Address(
            number = 742,
            street = "Evergreen Terrace",
            city = "Springfield",
            state = "Oregon",
            zipCode = 97475
        )
    )
    // 50% chance that marge ends up null here
    if (Random.nextInt() % 2 == 0) marge = null
    // the following is equivalent to an if not null check
    // and note that the captured parameter isn't of type?
    // because it knows that if it makes it passed the ? and
    // into the let then it cannot be null.
    // furthermore, you can use the elvis operator to provide
    // an alternative action when it is null
    val hasTwitter = marge?.let {
        it.hasTwitter = true
        println("marge is $marge")
        it.hasTwitter
    } ?: {
        println("marge is null")
        println("that's not good")
        println("we'll assume she doesn't have twitter because she doesn't exist!")
        false
    }.invoke() // use invoke for a multi line elvis block
    println("does marge have twitter? $hasTwitter")

}