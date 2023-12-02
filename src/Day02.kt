data class CubeSet(val red: Int, val green: Int, val blue: Int)

data class Game(val id: Int, val sets: Set<CubeSet>) {

    fun fullfills(maxRed: Int, maxGreen: Int, maxBlue: Int): Boolean {
        var allSetsFullfill = true

        for (set in sets) {
            if (set.red > maxRed || set.green > maxGreen || set.blue > maxBlue) {
                allSetsFullfill = false
                break
            }
        }
        return allSetsFullfill
    }

    fun power(): Int {
        val maxRed = sets.maxOf { it.red }
        val maxGreen = sets.maxOf { it.green }
        val maxBlue = sets.maxOf { it.blue }
        return maxRed * maxGreen * maxBlue
    }

    companion object {
        fun fromString(input: String): Game {
            val numberRegex = "\\d+".toRegex()
            val parts = input.split(":")
            var gameId = 0
            parts[0].let {
                gameId = numberRegex.find(it)!!.value.toInt()
            }
            val subparts = parts[1].split(";")

            val setsOfCubes = mutableSetOf<CubeSet>()
            for (subpart in subparts) {
                var redCount = 0
                var greenCount = 0
                var blueCount = 0

                val cubesStrings = subpart.split(",")
                for (cube in cubesStrings) {
                    if(cube.contains("red")) {
                        redCount = numberRegex.find(cube)!!.value.toInt()
                    }
                    if(cube.contains("green")) {
                        greenCount = numberRegex.find(cube)!!.value.toInt()
                    }
                    if(cube.contains("blue")) {
                        blueCount = numberRegex.find(cube)!!.value.toInt()
                    }
                }

                setsOfCubes.add(CubeSet(red = redCount, green = greenCount, blue = blueCount))
            }
            return Game(gameId, setsOfCubes)
        }
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val games = mutableListOf<Game>()
        val possibleGames = mutableListOf<Game>()

        // Parse input
        for (line in input) {
            games.add(Game.fromString(line))
        }

        for (game in games) {
            if(game.fullfills(12, 13, 14)) {
                possibleGames.add(game)
            }
        }

        val sumOfGameIds = possibleGames.sumOf { it.id }

        return sumOfGameIds
    }

    fun part2(input: List<String>): Int {
        val games = mutableListOf<Game>()
        // Parse input
        for (line in input) {
            games.add(Game.fromString(line))
        }
        val totalPower = games.sumOf { it.power() }
        return totalPower
    }

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}