fun main() {

    fun part1(input: List<String>): Int {
        val parts = mutableListOf<Int>()

        // Create a regular expression for finding numbers in the line
        val numberRegex = Regex("\\d+")

        // Iterate over all lines in the input
        for (lineIndex in 0..<input.size) {
            // Read all numbers from the line using regular expression and store them in a list
            val line = input[lineIndex]
            val numbers = numberRegex.findAll(line).map { it.value }.toList()

            // Iterate over all numbers in the line
            for (number in numbers) {
                val adjacentChars = mutableListOf<Char>()

                // Get a position of number substring in the line
                var leftIndex = line.indexOf(number)
                var rightIndex = leftIndex + number.length - 1

                // Get adjacent chars in the same line
                if (leftIndex > 0) {
                    leftIndex--
                    adjacentChars.add(line[leftIndex])
                }

                if (rightIndex < line.length - 1) {
                    rightIndex++
                    adjacentChars.add(line[rightIndex])
                }

                // Get adjacent symbols from the previous line
                if (lineIndex > 0) {
                    val previousLine = input[lineIndex - 1]
                    val adjacentTopSubstring = previousLine.substring(leftIndex..rightIndex)
                    for(adjacentTopChar in adjacentTopSubstring) {
                        adjacentChars.add(adjacentTopChar)
                    }
                }

                // Get adjacent symbols from the next line
                if (lineIndex < input.size - 1) {
                    val nextLine = input[lineIndex + 1]
                    val adjacentBottomSubstring = nextLine.substring(leftIndex..rightIndex)
                    for(adjacentBottomChar in adjacentBottomSubstring) {
                        adjacentChars.add(adjacentBottomChar)
                    }
                }

                // Check if there exactly one adjacent symbols which is not digit and not period
                val hasAdjacentSymbols = adjacentChars.count { !it.isDigit() && it.toString() != "." } == 1

                // If so, then it is valid engine part number
                if (hasAdjacentSymbols) {
                    parts.add(number.toInt())
                }

                println("${number}: ${adjacentChars.joinToString(separator = "")} -> ${hasAdjacentSymbols}")
            }
        }

        println(parts.joinToString(separator = ", "))

        return parts.sum()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInput("Day03")
    part1(input).println()
    //part2(input).println()
}