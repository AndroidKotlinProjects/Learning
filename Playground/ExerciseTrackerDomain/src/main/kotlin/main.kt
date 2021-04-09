import Components.Active.TimedSets
import Components.Active.WeightedSets
import Components.Component
import Components.Rest.CountdownTimer

fun main() {

    var weightedSet: Component = WeightedSets(5, 70.toDouble(), Metrics.kilograms)
    var countdownTimer: Component = CountdownTimer(60)
    var benchComponents: MutableList<Component> = mutableListOf(weightedSet, countdownTimer, weightedSet, countdownTimer, weightedSet, countdownTimer)
    var benchPress: Exercise = Exercise("Bench press", WorkoutTypes.strength, benchComponents)

    var timedSet: Component = TimedSets(numReps = 2, seconds = 120)
    var quadStretchComponents: MutableList<Component> = mutableListOf(timedSet, countdownTimer, timedSet, countdownTimer)
    var quadStretch: Exercise = Exercise("Quad stretch", WorkoutTypes.mobility, quadStretchComponents)

    val routine: Routine = Routine("Strength routine", exercises = mutableListOf(quadStretch, benchPress))
    routine.start()

}