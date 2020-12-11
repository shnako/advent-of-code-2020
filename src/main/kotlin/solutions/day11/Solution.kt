package solutions.day11

import solutions.GenericSolution
import util.read2dCharArray
import java.io.File

class Solution : GenericSolution {
    private val floor = '.'
    private val empty = 'L'
    private val occupied = '#'

    private fun countOccupiedSeats(seatGrid: Array<CharArray>): Int {
        return seatGrid.map { seatLine -> seatLine.filter { it == occupied }.count() }.sum()
    }

    private fun areSeatCoordinatesValid(it: Array<Int>, seatGrid: Array<CharArray>): Boolean {
        if (it[0] < 0 || it[1] < 0) {
            return false
        }

        if (it[0] >= seatGrid.size) {
            return false
        }

        if (it[1] >= seatGrid[0].size) {
            return false
        }

        return true
    }

    private fun getAdjacentSeats(i: Int, j: Int, seatGrid: Array<CharArray>): List<Char> {
        val adjacentSeatCoordinateGrid = arrayOf(
            arrayOf(i - 1, j - 1),
            arrayOf(i - 1, j),
            arrayOf(i - 1, j + 1),
            arrayOf(i, j + 1),
            arrayOf(i + 1, j + 1),
            arrayOf(i + 1, j),
            arrayOf(i + 1, j - 1),
            arrayOf(i, j - 1)
        )

        return adjacentSeatCoordinateGrid
            .filter { areSeatCoordinatesValid(it, seatGrid) }
            .map { seatGrid[it[0]][it[1]] }
    }

    private fun getNewSeatState(i: Int, j: Int, seatGrid: Array<CharArray>): Char {
        val seat = seatGrid[i][j]
        val adjacentSeats = getAdjacentSeats(i, j, seatGrid)

        val occupiedSeats = adjacentSeats.filter { it == occupied }.count()

        if (seat == empty && occupiedSeats == 0) {
            return occupied
        }

        if (seat == occupied && occupiedSeats >= 4) {
            return empty
        }

        return seat
    }

    private fun Array<CharArray>.copy() = Array(size) { get(it).clone() }

    private fun getNewSeatGrid(seatGrid: Array<CharArray>): Array<CharArray> {
        val newSeatGrid = seatGrid.copy()

        for (i in seatGrid.indices) {
            for (j in seatGrid[0].indices) {
                val newSeatState = getNewSeatState(i, j, seatGrid)
                newSeatGrid[i][j] = newSeatState
            }
        }

        return newSeatGrid
    }

    override fun runPart1(inputFile: File): String {
        var seatGrid = read2dCharArray(inputFile)

        while (true) {
            val newSeatGrid = getNewSeatGrid(seatGrid)

            if (seatGrid.contentDeepEquals(newSeatGrid)) {
                break
            }

            seatGrid = newSeatGrid
        }

        return countOccupiedSeats(seatGrid).toString()
    }

    override fun runPart2(inputFile: File): String {
        TODO("Not yet implemented")
    }
}
