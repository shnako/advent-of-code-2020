package solutions.day24

import org.apache.commons.lang3.tuple.ImmutableTriple
import org.apache.commons.lang3.tuple.MutableTriple
import solutions.GenericSolution
import util.readLines
import java.io.File

class Solution : GenericSolution {
    private val coordinates = mapOf(
        Pair("w", ImmutableTriple(-1, +1, +0)),
        Pair("e", ImmutableTriple(+1, -1, +0)),
        Pair("sw", ImmutableTriple(-1, +0, +1)),
        Pair("se", ImmutableTriple(+0, -1, +1)),
        Pair("nw", ImmutableTriple(+0, +1, -1)),
        Pair("ne", ImmutableTriple(+1, +0, -1)),
    )

    private fun move(direction: String, currentCoordinates: MutableTriple<Int, Int, Int>) {
        val directionCoordinates = coordinates[direction]!!

        currentCoordinates.left += directionCoordinates.left
        currentCoordinates.middle += directionCoordinates.middle
        currentCoordinates.right += directionCoordinates.right
    }

    private fun populateBlackTiles(tileCoordinates: Collection<String>): MutableSet<MutableTriple<Int, Int, Int>> {
        val blackTiles = mutableSetOf<MutableTriple<Int, Int, Int>>()
        for (tileCoordinate in tileCoordinates) {
            val tile = MutableTriple(0, 0, 0)
            var i = 0
            while (i < tileCoordinate.length) {
                val direction: String
                if (tileCoordinate[i] == 'w' || tileCoordinate[i] == 'e') {
                    direction = tileCoordinate[i].toString()
                    i++
                } else {
                    direction = tileCoordinate.substring(i, i + 2)
                    i += 2
                }
                move(direction, tile)
            }

            if (blackTiles.contains(tile)) {
                blackTiles.remove(tile)
            } else {
                blackTiles.add(tile)
            }
        }
        return blackTiles
    }

    override fun runPart1(inputFile: File): String {
        val tileCoordinates = readLines(inputFile)

        val blackTiles = populateBlackTiles(tileCoordinates)

        return blackTiles.size.toString()
    }

    override fun runPart2(inputFile: File): String {
        TODO("Not yet implemented")
    }
}
