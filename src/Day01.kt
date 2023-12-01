fun main() {

    fun extractCalbrationValue(input: String): Int {
        val digits = input.filter { it.isDigit() }
        return "${digits.first()}${digits.last()}".toInt()
    }

    /*
    --- Part One ----
     Process input files and calculate the calibration value.
     As an input use first and last digit in each line combined as two digit number.
     Final result is sum of all calibration values.
     */

    fun part1(input: List<String>): Int {
        return input.sumOf { extractCalbrationValue(it) }
    }

    /*
    --- Part Two ---
        Your calculation isn't quite right. It looks like some of the digits are actually spelled out with letters:
         one, two, three, four, five, six, seven, eight, and nine also count as valid "digits".
        Equipped with this new information, you now need to find the real first and last digit on each line. For example:
     */

    fun part2(input: List<String>): Int {
        var result = 0
        val digitMap = mapOf(
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9"
        )

        val digitsRegex = "(${digitMap.keys.joinToString(separator = "|")}|\\d)".toRegex(setOf(RegexOption.IGNORE_CASE))

        val replaceLambda: ((MatchResult) -> String) = {
            if(digitMap[it.value] != null) {
                digitMap[it.value] + it.value.last()
            } else {
                it.value
            }
        }

        for (line in input) {
            val fixedInput = digitsRegex.replace(line, transform = replaceLambda)

            // Fix stuff like eightree or sevenine
            val fixedInput2 = digitsRegex.replace(fixedInput, transform = replaceLambda)
            val calibration = extractCalbrationValue(fixedInput2)

            result += calibration
        }

        return result
    }

    val input = readInput("Day01")
    //val input2 = readInput("Day01_test")
    part1(input).println()
    part2(input).println()
}
