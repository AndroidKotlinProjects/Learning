import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking {

    // PART 1

    exampleOf("Sequence (which blocks the main thread)")
    fun prequels(): Sequence<String> = sequence {
        for (movie in listOf(episodeI, episodeII, episodeIII)) {
            Thread.sleep(DELAY)
            yield(movie)
        }
    }
    prequels().forEach { movie -> log(movie) }
    log("Some more work to be done here.")
    /* Observations: the "Some more work..." message prints last because the main thread is blocked whilst the sequence
    * is being consumed. Here we are calling the prequels() function which returns a sequence of strings. */

    exampleOf("Suspend functions (asynchronous which won't block the main thread)")
    suspend fun originals(): List<String> {
        delay(DELAY)
        return listOf(episodeIV, episodeV, episodeVI)
    }
    launch {
        originals().forEach { movie -> log(movie) }
    }
    log("Some more work to be done here.")
    /* Observations: Now the "Some more work..." message won't print last because now we are using a suspend function
    * and so the main thread is free to keep working.
    * Mistakes: I didn't put the call to the suspend function inside a launch {} coroutine builder. Remember that
    * suspend functions are no different to regular functions when called from the same coroutine i.e. synchronous by
    * default. */

    delay(DELAY*2)
    exampleOf("Flow")
    fun sequels(): Flow<String> = flow {
        for (movie in listOf(episodeVII, episodeVIII, episodeIX)) {
            delay(DELAY)
            emit(movie)
        }
    }
    launch {
        sequels().collect { log(it) }
    }
    launch {
        repeat(3) {
            delay(DELAY)
            log("Some other work")
        }
    }
    log("Some more work to be done here.")
    /* Observations: The coroutine collect, "some more work..." message and the "some other work" message are all being
    * printed on the main thread but none of it is blocking. They all run concurrently.
    * Mistakes: again, forgot to run the sequels().collect inside of a launched coroutine. */

    delay(4*DELAY)
    exampleOf("Cold stream")
    val sequelsFlow = sequels()
    sequelsFlow.collect { log(it) }
    /* Observations: flows don't actually start until you call a terminal operator like collect */

    delay(DELAY*4)
    exampleOf("Collecting again")
    sequelsFlow.collect { log(it) }
    /* Observations: flows can be collected more than once */

    exampleOf("flowOf")
    val favourites = flowOf(episodeV, episodeIX, episodeI)
    favourites.collect { log(it) }
    /* Observations: flows can be built with the flow {} builder, but they can also be built in the same way that
    * list can be i.e. instead of listOf(x,y,z), we have flowOf(x,y,z) */

    exampleOf("asFlow")
    val favouritesList = listOf(episodeV, episodeIX, episodeI)
    favouritesList.asFlow().collect { log(it) }
    /* Observations: flows can also be generated from collections e.g. lists, with the asFlow() extension function */

    exampleOf("cancellation via withTimeoutOrNull")
    fun starWarsSounds() = flow {
        listOf(blasterSound, chewieSound, saberSound, r2d2Sound).forEach {
            delay(DELAY)
            log("emitting $it")
            emit(it)
        }
    }
    withTimeoutOrNull((3.1*DELAY).toLong()) {
        starWarsSounds().collect {
            log(it)
        }
    }
    log("Done emitting sounds!")

    exampleOf("filter operator")
    forceUsers.asFlow()
        .filter { it is Jedi }
        .collect { log(it.name) }

    exampleOf("map operator")
    suspend fun bestowSithTitle(forceUser: ForceUser): String {
        delay(DELAY)
        return "Darth ${forceUser.name}"
    }
    val sith = forceUsers.asFlow()
        .filter { it is Sith }
    sith.map { bestowSithTitle(it) } // map means we now have a flow of strings because we mapped a forceUser to string
        .collect { log(it) }
    /* Observations: when you use a map, the map possibly changes the type of your flow. That is indeed the case here,
    * because we pass each sith user to the bestowSithTitle function which returns a string of their sith name, the
    * resultant newly mapped to flow is therefore a flow of strings. */

    exampleOf("transform operator (more general map)")
    forceUsers.asFlow()
        .transform {
            if (it is Sith) {
                emit("I am turning ${it.name} to the dark side...")
                emit(bestowSithTitle(it))
            }
        }
        .collect { log(it) }

    exampleOf("size-limiting operator")
    sith.take(2).collect { log(it.name) }

    exampleOf("reduction terminal operator")
    val jediLineage = forceUsers.asFlow()
        .filter { it is Jedi }
        .map { it.name }
        .reduce { a, b -> "$a trained by $b" }
    log(jediLineage)
    /* Observations: in reduce, we firstly get a = Luke Skywalker and b = Obi-Wan Kenobi. This results in "Luke
    * Skywalker was trained by Obi-Wan Kenobi". Secondly, we get a = "Luke Skywalker was trained by Obi-Wan Kenobi",
    * i.e. the result of the last reduce, and b = Yoda. This results in "Luke Skywalker was trained by Obi-Wan Kenobi
    * was trained by Yoda", which is the whole reduction. */

    fun turnToDarkSide(): Flow<ForceUser> = forceUsers.asFlow()
        .transform { forceUser ->
            if (forceUser is Jedi) {
                emit(Sith(forceUser.name))
            }
        }
    exampleOf("imperative completion")
    try {
        turnToDarkSide().collect { log("${it.name}, your transformation to the Dark Side is complete! Rise!") }
    } finally {
        log("We have turned them all!")
    }

    exampleOf("declarative completion")
    turnToDarkSide()
        .onCompletion { log("We have turned them all!") }
        .collect { log("${it.name}, your transformation to the Dark Side is complete! Rise!") }

    // PART 2

    exampleOf("flowOn to run the flow in another context")
    fun duelOfTheFates(): Flow<ForceUser> = flow {
        forceUsers.forEach {
            delay(DELAY)
            log("Battling with ${it.name}")
            emit(it)
        }
    }.transform { forceUser ->
        if (forceUser is Sith) {
            forceUser.name = "Darth ${forceUser.name}"
        }
        emit(forceUser)
    }.flowOn(Dispatchers.Default)
    duelOfTheFates().collect { log("Battled ${it.name}") }

    exampleOf("no buffering")
    fun jediTrainees(): Flow<ForceUser> = forceUsers.asFlow()
        .transform { forceUser ->
            if (forceUser is Padawan) {
                delay(DELAY) // train from Padawan to Jedi
                emit(forceUser)
            }
        }

    val time = measureTimeMillis {
        jediTrainees().collect {
            delay(DELAY * 3) // train from Jedi to Jedi Master
            log("Jedi ${it.name} is now a Jedi Master!")
        }
    }
    log("total training time from Padawan -> Jedi -> Jedi Master was $time")

    exampleOf("buffering")
    val time2 = measureTimeMillis {
        jediTrainees()
            .buffer()
            .collect {
            delay(DELAY * 3) // train from Jedi to Jedi Master
            log("Jedi ${it.name} is now a Jedi Master!")
        }
    }
    log("total training time from Padawan -> Jedi -> Jedi Master was $time2")

    exampleOf("conflating")
    val time3 = measureTimeMillis {
        jediTrainees()
            .conflate()
            .collect {
                delay(DELAY * 3) // train from Jedi to Jedi Master
                log("Jedi ${it.name} is now a Jedi Master!")
            }
    }
    log("total training time from Padawan -> Jedi -> Jedi Master was $time3")

    exampleOf("conflating")
    val time4 = measureTimeMillis {
        jediTrainees()
            .collectLatest {
                log("starting training from Jedi to Jedi Master for ${it.name}")
                delay(DELAY * 3) // train from Jedi to Jedi Master
                log("Jedi ${it.name} is now a Jedi Master!")
            }
    }
    log("total training time from Padawan -> Jedi -> Jedi Master was $time4")

    exampleOf("zip operator")
    var characters = characterNames.asFlow()
    var weapons = weaponNames.asFlow()
    characters.zip(weapons) { characterName, weaponName -> "$characterName wields $weaponName" }
        .collect { log(it) }

    exampleOf("zip with onEach and delays")
    characters = characterNames.asFlow().onEach { delay(DELAY/2) }
    weapons = weaponNames.asFlow().onEach { delay(DELAY/2) }
    var startTime = System.currentTimeMillis()
    characters.zip(weapons) { characterName, weaponName -> "$characterName wields $weaponName" }
        .collect { log("$it, with delta ${System.currentTimeMillis() - startTime}") }

    exampleOf("combine (like zip but without exclusive pairs, pairs on update of either flow)")
    characters = characterNames.asFlow().onEach { delay(DELAY/2) }
    weapons = weaponNames.asFlow().onEach { delay(DELAY/2) }
    startTime = System.currentTimeMillis()
    characters.combine(weapons) { characterName, weaponName -> "$characterName wields $weaponName" }
        .collect { log("$it, with delta ${System.currentTimeMillis() - startTime}") }

    fun suitUp(name: String): Flow<String> = flow {
        emit("$name gets dressed for battle")
        delay(DELAY)
        emit("$name dons their helmet")
    }

    exampleOf("flow of flows (what happens if you use map instead of flatMap)")
    characterNames.asFlow()
        .map { suitUp(it) }
        .collect { println(it) }

    exampleOf("flatMapConcat")
    startTime = System.currentTimeMillis()
    characterNames.asFlow().onEach { delay(DELAY/2) }
        .flatMapConcat { suitUp(it) }
        .collect { log("$it at delta ${System.currentTimeMillis() - startTime}") }

    exampleOf("flatMapMerge")
    startTime = System.currentTimeMillis()
    characterNames.asFlow().onEach { delay(DELAY/2) }
        .flatMapMerge { suitUp(it) }
        .collect { log("$it at delta ${System.currentTimeMillis() - startTime}") }

    exampleOf("flatMapLatest")
    startTime = System.currentTimeMillis()
    characterNames.asFlow().onEach { delay(DELAY/2) }
        .flatMapLatest { suitUp(it) }
        .collect { log("$it at delta ${System.currentTimeMillis() - startTime}") }

    fun midichlorianCountTest(): Flow<Int> = flow {
        midichlorianCounts.keys.forEach {
            log("Testing $it")
            delay(DELAY)
            emit(midichlorianCounts[it] ?: 0) // not allowed to emit null ?
        }
    }

    exampleOf("imperative catching of an exception in collect with try {} catch {}")
    try {
        midichlorianCountTest().collect {
            log(it.toString())
            check(it <= CHOSEN_ONE_THRESHOLD) { "check failed on: $it" }
        }
    } catch (e: Throwable) {
        log("omg we may have found the chosen one! $e")
    }

    exampleOf("imperative catching of an exception in the flow with try {} catch {}")
    fun midichlorianCountTestString(): Flow<String> = flow<Int> {
        midichlorianCounts.keys.forEach {
            log("Testing $it")
            delay(DELAY)
            emit(midichlorianCounts[it] ?: 0) // not allowed to emit null ?
        }
    }.map {
        check(it <= CHOSEN_ONE_THRESHOLD) { "check failed on: $it" }
        "$it"
    }

    try {
        midichlorianCountTest().collect {
            log(it.toString())
            check(it <= CHOSEN_ONE_THRESHOLD) { "check failed on: $it" }
        }
    } catch (e: Throwable) {
        log("omg we may have found the chosen one! $e")
    }

    exampleOf("exception transparency: using the catch operator to only catch upstream exceptions")

    midichlorianCountTest()
        .onEach {
            check(it <= CHOSEN_ONE_THRESHOLD) { "Test result: $it" }
            log(it.toString())
        }
        .catch { e -> log("exception caught: $e") }
        .collect()

    midichlorianCountTest()
        .catch { e -> log("exception caught: $e") }
        .collect {
            check(it <= CHOSEN_ONE_THRESHOLD) { "Test result: $it" }
            log(it.toString())
        }


    exampleOf("trying to emit null")
    val hasNulls: List<Int?> = listOf(1, 2, null, 4)
    val flowWithNull = flow {
        hasNulls.forEach {
            emit(it)
        }
    }
    flowWithNull.collect { log(it.toString()) }
}