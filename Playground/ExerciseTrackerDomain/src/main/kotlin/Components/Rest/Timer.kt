package Components.Rest

import Components.Component

abstract class Timer(var initalValue: Int): Component {
}

class CountdownTimer(initialValue: Int): Timer(initialValue) {
    override fun start() {
        println("countdown timer counting down from $initalValue")
        println("tick tock tick tock")
        println("done")
    }

}