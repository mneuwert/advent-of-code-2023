data class RangeMapping(val sourceRange:LongRange, val destinationRange:LongRange) {
    fun find(value: Long): Long? {
        return if (value in sourceRange) {
            val index = value - sourceRange.first
            destinationRange.first + index
        } else {
            null
        }
    }
}

fun List<RangeMapping>.find(value: Long): Long? {
    for (range in this) {
        range.find(value)?.let {
            return it
        }
    }
    return null
}

data class GardenPlan(val seeds:List<Long>, val mappings:Map<String, List<RangeMapping>>)

fun main() {

    fun parse(input: List<String>): GardenPlan {
        val mappings = mutableMapOf<String, List<RangeMapping>>()
        val inputCopy = input.toMutableList()

        // Read seed values from the first line formatted as: seeds: 1 2 3 5
        val seeds = inputCopy[0].substringAfter("seeds: ").split(" ").map { it.toLong() }

        val mapRegex = Regex("([^\\d]+):\n((\\d)+ (\\d)+ (\\d)+\n)+(\n)*")
        val mapMatches = mapRegex.findAll(inputCopy.joinToString("\n"))
        mapMatches.map { it.value }.forEach {
            val (name, numbers) = it.split(":")
            val rangeMappingList = mutableListOf<RangeMapping>()

            val ranges = numbers.trim().split("\n")
            ranges.forEach {
                val range = it.split(" ").map { it.toLong() }
                if (range.size == 3) {
                    val destinationRange = LongRange(range[0], range[0] + range[2])
                    val sourceRange = LongRange(range[1], range[1] + range[2])
                    rangeMappingList.add(RangeMapping(sourceRange, destinationRange))
                }
            }
            mappings[name] = rangeMappingList
        }
        return GardenPlan(seeds, mappings)
    }

    fun part1(input: List<String>): Long {
        val plan = parse(input)
        val locations = mutableListOf<Long>()

        for (seed in plan.seeds) {
            var location: Long = seed
            plan.mappings.forEach {
                it.value.find(location)?.let {
                    location = it
                }
            }
            locations.add(location)
        }

        return locations.min()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInput("Day05")
    part1(input).println()
    //part2(input).println()
}