package solutions.day13

import solutions.GenericSolution
import util.readLines
import java.io.File

/**
 * The first part is very simple.
 * To find out how long we need to wait for for each bus, we can calculate (the earliest timestamp modulo bus number)
 * and subtract it from the bus number. The bus for which we need to wait the least is the answer.
 *
 * The second part has too many timestamps to go through and will take too long (see runPart2Naive).
 * My solution starts with the first 2 buses.
 * I find the firstTimestamp where both buses are valid.
 * I then find the secondTimestamp where both buses are valid.
 * The possible solutions show up every (secondTimestamp - firstTimestamp).
 * This allows us to incrementBy = (secondTimestamp - firstTimestamp) instead of by 1.
 * We then repeat the above for the next bus until we find the solution for all buses.
 * The incrementBy shoots up very quickly, which massively reduces the number of steps required, making this fast.
 */
class Solution : GenericSolution {
    override fun runPart1(inputFile: File): String {
        val input = readLines(inputFile)
        val earliestTimestamp = input[0].toLong()
        val buses = input[1].split(",").filter { it != "x" }.map { it.toLong() }

        var minWaitTime = Long.MAX_VALUE
        var bestBus = -1L

        for (bus in buses) {
            val waitTime = bus - earliestTimestamp % bus
            if (waitTime < minWaitTime) {
                minWaitTime = waitTime
                bestBus = bus
            }
        }

        return (minWaitTime * bestBus).toString()
    }

    private fun isValid(timestamp: Long, buses: List<Long>): Boolean {
        for (i in buses.indices) {
            if (buses[i] != -1L && (timestamp + i) % buses[i] != 0L) {
                return false
            }
        }

        return true
    }

    override fun runPart2(inputFile: File): String {
        val buses = readLines(inputFile)[1].split(",").map { if (it == "x") -1L else it.toLong() }

        var iterateBy = buses[0]
        var timestamp = buses[0]
        for (i in 1 until buses.size) {
            if (buses[i] == -1L) {
                continue
            }

            while (true) {
                timestamp += iterateBy
                if (isValid(timestamp, buses.subList(0, i + 1))) {
                    break
                }
            }
            val firstTimestamp = timestamp

            while (true) {
                timestamp += iterateBy
                if (isValid(timestamp, buses.subList(0, i + 1))) {
                    break
                }
            }
            val secondTimestamp = timestamp

            iterateBy = secondTimestamp - firstTimestamp
        }

        return (timestamp - iterateBy).toString()
    }

    @Suppress("unused") // Keeping as naive solution
    fun runPart2Naive(inputFile: File): String {
        val buses = readLines(inputFile)[1].split(",").map { if (it == "x") -1 else it.toLong() }

        val longestTrip = buses.maxOrNull()!!
        val longestTripOffset = buses.indexOf(longestTrip)

        var multiplier = 1L
        while (true) {
            val timestamp = longestTrip * multiplier - longestTripOffset
            if (isValid(timestamp, buses)) {
                return timestamp.toString()
            }

            multiplier++
            if (multiplier % 100000000 == 0L) {
                println("$timestamp is not valid")
            }
        }
    }
}
