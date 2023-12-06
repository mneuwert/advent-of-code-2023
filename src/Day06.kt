import java.lang.Math.*
import kotlin.system.measureTimeMillis

data class Race(val time:Long, val recordDistance:Long) {
    // TODO: this is not used in the solution, but it's a nice formula to calculate the number of ways to win
    // Credits: https://github.com/ephemient
    fun winCount(): Long {
        // x * (time - x) > distance
        // x^2 - time*x < -distance
        // x^2 - time*x + (time/2)^2 < (time/2)^2 - distance
        // abs(x - time/2) < sqrt((time/2)^2 - distance)
        val b = time / 2.0
        val d = sqrt(b * b - recordDistance.toDouble())
        return (ceil(b + d - 1) - floor(b - d + 1) + 1).toLong()
    }
}
fun main() {

    fun distance(totalTime: Long, chargeTime: Long): Long {
        val remainingTime = totalTime - chargeTime
        // speed is equal charge time
        return remainingTime * chargeTime
    }

    fun parse(input:List<String>): List<Race> {
        val races = mutableListOf<Race>()
        val times = input[0].substringAfter("Time:").trim().split(regex = Regex("\\s+")).map { it.toLong() }
        val distances = input[1].substringAfter("Distance:").trim().split(regex = Regex("\\s+")).map { it.toLong() }

        times.zip(distances).forEach { races.add(Race(it.first, it.second)) }

        return races
    }

    fun parse2(input:List<String>): Race {
        val time = input[0].substringAfter(":").trim().filter { it.isDigit() }.toLong()
        val distance = input[1].substringAfter(":").trim().filter { it.isDigit() }.toLong()
        return Race(time, distance)
    }

    fun part1(input: List<String>): Int {
        val races = parse(input)
        var result = 1
        races.forEach {
            var waysToWin = 0
            // Try to hold charge button each time 1ms longer, to overcome the record distance
            // Count the ways to win
            for (x in 1..it.time) {
                if (distance(it.time, x) > it.recordDistance) {
                    waysToWin++
                }
            }
            result *= waysToWin
        }

        return result
    }

    fun part2(input: List<String>): Int {
        val race = parse2(input)
        var waysToWin = 0
        // Try to hold charge button each time 1ms longer, to overcome the record distance
        // Count the ways to win.. Probably you could come up with somewhat more efficient solution though
        val timeInMillis = measureTimeMillis {
            for (x in 1..race.time) {
                val distance = distance(race.time, x)
                if (distance > race.recordDistance) {
                    waysToWin++
                }
            }
        }
        println("Time: $timeInMillis")

        return waysToWin
    }

    fun part2_2(input: List<String>): Int {
        val race = parse2(input)
        var waysToWin = 0
        var startedWinning = false
        // Try to hold charge button each time 1ms longer, to overcome the record distance
        // Count the ways to win..
        val timeInMillis = measureTimeMillis {
            for (x in race.time downTo 1) {
                val distance = distance(race.time, x)
                if (distance > race.recordDistance) {
                    waysToWin++
                    startedWinning = true
                } else if (startedWinning) {
                    // try to stop counting when charging time is not sufficient to win
                    break
                }
            }
        }
        println("Time: $timeInMillis")
        return waysToWin
    }

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
    part2_2(input).println()

}