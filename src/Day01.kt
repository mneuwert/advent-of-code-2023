fun main() {

    fun extractCalbrationValue(input: String): Int {
        val digits = input.filter { it.isDigit() }
        return "${digits.first()}${digits.last()}".toInt()
    }

    fun part1(input: List<String>): Int {
        var result = 0
        for (line in input) {
            val calibrationValue = extractCalbrationValue(line)
            result += calibrationValue
        }

        return result
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
            "zero" to "0",
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

        val digitsPattern = "(${digitMap.keys.joinToString(separator = "|")})"
        val digitsRegex = Regex(digitsPattern)

        for (line in input) {
            val fixedInput = digitsRegex.replace(line, transform = { digitMap[it.value] ?: "" })
            val calibration = extractCalbrationValue(fixedInput)
            result += calibration
        }

        return result
    }

    val input = readInput("Day01")
    val input2 = readInput("Day01_test")
    part1(input).println()
    part2(input).println()
}
