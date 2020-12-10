package solutions.day10

import solutions.GenericSolution
import util.readIntegers
import java.io.File

/**
 * Knowing that the adapters can all link together makes everything simple.
 * Just sort them by joltage, measure the difference between consecutive ones and that's part 1 done.
 *
 * For part 2 I've used a recursive function that goes through all valid arrangements but that's too slow
 * as there are hundreds of trillions of valid arrangements. I ran it just for fun for 90 min and it didn't complete.
 *
 * The optimal solution is the same as the slow one,
 * but uses memoization so we don't recalculate results for subsets we've already calculated.
 */
class Solution : GenericSolution {
    override fun runPart1(inputFile: File): String {
        val input = readIntegers(inputFile)
            .toMutableList()
        input.add(0)
        val adapters = input.sorted()

        var ones = 0
        var threes = 1 // One for 3 higher than the highest rated
        var i = 0
        while (++i < adapters.size) {
            when (adapters[i] - adapters[i - 1]) {
                1 -> ones++
                3 -> threes++
            }
        }
        return (ones * threes).toString()
    }

    @Suppress("unused") // Keeping as a suboptimal solution
    private fun getArrangementsNaive(adapters: List<Int>, index: Int): Long {
        if (index + 1 == adapters.size) {
            return 1
        }

        var sum = 0L
        for (offset in 1..3) {
            if (index + offset >= adapters.size) {
                break
            }

            if (adapters[index] + 3 >= adapters[index + offset]) {
                sum += getArrangementsNaive(adapters, index + offset)
            }
        }

        return sum
    }

    private fun getArrangementsMemoized(adapters: List<Int>, index: Int, memoizedResults: LongArray): Long {
        if (memoizedResults[index] != 0L) {
            return memoizedResults[index]
        }

        if (index + 1 == adapters.size) {
            return 1
        }

        var sum = 0L
        for (offset in 1..3) {
            if (index + offset >= adapters.size) {
                break
            }

            if (adapters[index] + 3 >= adapters[index + offset]) {
                sum += getArrangementsMemoized(adapters, index + offset, memoizedResults)
            }
        }

        memoizedResults[index] = sum
        return sum
    }

    override fun runPart2(inputFile: File): String {
        val input = readIntegers(inputFile)
            .toMutableList()
        input.add(0)
        val adapters = input.sorted()

        val memoizedResults = LongArray(input.size)
        return getArrangementsMemoized(adapters, 0, memoizedResults).toString()
    }
}
