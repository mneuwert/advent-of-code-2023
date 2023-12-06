data class Race(val time:Int, val recordDistance:Int)
fun main() {

    fun distance(totalTime: Int, chargeTime: Int): Int {
        val remainingTime = totalTime - chargeTime
        // speed is equal charge time
        return remainingTime * chargeTime
    }

    fun parse(input:List<String>): List<Race> {
        val races = mutableListOf<Race>()
        val times = input[0].substringAfter("Time:").trim().split(regex = Regex("\\s+")).map { it.toInt() }
        val distances = input[1].substringAfter("Distance:").trim().split(regex = Regex("\\s+")).map { it.toInt() }

        times.zip(distances).forEach { races.add(Race(it.first, it.second)) }

        return races
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
        return input.size
    }

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}