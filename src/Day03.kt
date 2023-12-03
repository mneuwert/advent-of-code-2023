data class PartNumber(val value: Int, val range: IntRange, val lineIndex: Int)

data class Symbol(val value:Char, val range: IntRange, val lineIndex: Int)

fun main() {

    fun processInput(input: List<String>): Pair<List<PartNumber>, List<Symbol>> {
        val parts = mutableListOf<PartNumber>()
        val symbols = mutableListOf<Symbol>()
        val numberRegex = Regex("\\d+")
        val symbolRegex = Regex("[^\\d.]")

        for (lineIndex in 0..<input.size) {
            val line = input[lineIndex]
            val numbers = numberRegex.findAll(line).map { PartNumber(it.value.toInt(), range = it.range, lineIndex = lineIndex) }.toList()
            val symbolsInLine = symbolRegex.findAll(line).map { Symbol(it.value.first(), range = it.range, lineIndex = lineIndex) }.toList()

            for (number in numbers) {
                parts.add(number)
            }

            for (symbol in symbolsInLine) {
                symbols.add(symbol)
            }

        }
        return Pair(parts, symbols)
    }

    fun part1(input: List<String>): Int {

        val processedInput = processInput(input)
        val parts = processedInput.first
        val symbols = processedInput.second

        val validParts = mutableListOf<PartNumber>()

        for (part in parts) {

            // Get a position of number substring in the line
            var leftIndex = part.range.first
            var rightIndex = part.range.last

            // Get adjacent chars in the same line
            val lineRange = IntRange(part.lineIndex-1, part.lineIndex+1)
            val inLineRange = IntRange(leftIndex-1, rightIndex+1)
            symbols.count { it.lineIndex in lineRange && it.range.first in inLineRange }.takeIf { it == 1}?.let {
                validParts.add(part)
            }
        }

        return validParts.sumOf { it.value }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInput("Day03")
    val input2 = readInput("Day03_test2")
    part1(input).println()
    part2(input2).println()
}