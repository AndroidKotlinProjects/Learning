package com.raywenderlich.android.currency

sealed class AcceptedCurrency {

    abstract val valueInDollars: Double
    var amount: Double = 0.0

    val name: String
        get() = when(this) {
            is Dollar -> "Dollars"
            is Euro -> "Euro"
            is Crypto -> "Crypto"
        }

    fun totalValueInDollars(): Double {
        return amount * valueInDollars
    }

    class Dollar: AcceptedCurrency() {
        override val valueInDollars: Double = 1.0
    }

    class Euro: AcceptedCurrency() {
        override val valueInDollars: Double = 1.25
    }

    class Crypto: AcceptedCurrency() {
        override val valueInDollars: Double = 2534.92
    }

}