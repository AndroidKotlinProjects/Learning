package Components.Active

import Components.Component
import kotlin.math.max

/*
* default repLabel of "rep" because most sets are of reps, not laps etc.
* default numReps of 1 because each set should contain at least 1 rep otherwise you didn't really do a set.
* default metric of kg because most sets will be used for lifting weights and i like kg more than lbs */
abstract class Sets(var repLabel: String, var numReps: Int): Component {
}

class WeightedSets(numReps: Int, var weight: Double, var metric: String): Sets("reps", numReps) {
    override fun start() {
        println("$numReps $repLabel of $weight $metric completed!")
    }
}

class TimedSets(numReps: Int, var seconds: Int): Sets("reps", numReps) {
    override fun start() {
        for (i in (0..numReps-1)) {
            println("timed set timer of $seconds seconds done, rep ${i+1} completed")
        }
    }

}

sealed class Metric {

    abstract val label: String

    sealed class Weight(): Metric() {

        class Kilogram: Weight() {
            override val label: String = "kg"
        }
        class Pound: Weight() {
            override val label: String = "lbs"
        }
    }

    sealed class Time(): Metric() {
        class Second: Time() {
            override val label: String = "secs"
        }
        class Minute: Time() {
            override val label: String = "mins"
        }
    }

    sealed class Distance: Metric() {
        class Meter: Distance() {
            override val label: String = "m"
        }
        class Kilometer: Distance() {
            override val label: String = "km"
        }
    }

}

/* Contains all possible activity components */
sealed class ActivityComponent {

    /* Set components are designed for activities of a repeated nature, e.g. bench press, squat, toe touches, quad
    * stretches etc. */
    sealed class Set(): ActivityComponent() {
        var metric: Metric = Metric.Weight.Kilogram()

        // looks like: 5 reps of 70kg, or 2 reps of 130lbs etc.
        /* Need to be able to change the metric (kg/lb) */
        class WeightedSet(): Set()

        // looks like: 4 reps, or 3 reps etc.
        /* Need to be able to change the rep label i.e. 4 reps vs 4 laps */
        class PlainSet(): Set()

        // Looks like: 3 reps of 60 seconds, 2 reps of 2 minutes
        /* Need to be able to change the metric (sec/min) */
        class TimedSet(): Set()

        /*
        sealed class MultiSet(): Set() {
            abstract var numExercises: Int
            class SuperSet: MultiSet() {
                override var numExercises = 2
            }
            class TripleSet: MultiSet() {
                override var numExercises = 3
            }
            class GiantSet: MultiSet() {
                override var numExercises = 4
                fun setNumExercises() = max(numExercises, readLine()?.toInt()!!)
            }
        }
        */
    }

    /* A component for all GPS tracking based activities such as running, bike riding, hiking, walking etc. */
    class DistanceActivity(): ActivityComponent() {

    }
}

/* Contains all possible rest components */
sealed class RestComponent {

}




