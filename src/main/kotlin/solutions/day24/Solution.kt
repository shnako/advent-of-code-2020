package solutions.day24

import org.apache.commons.lang3.tuple.MutableTriple
import solutions.GenericSolution
import util.readLines
import java.io.File

class Solution : GenericSolution {
    override fun runPart1(inputFile: File): String {
        val tileCoordinates = readLines(inputFile)

        val blackTiles = mutableSetOf<MutableTriple<Int, Int, Int>>()
        for (tileCoordinate in tileCoordinates) {
            val tile = MutableTriple(0, 0, 0)
            var i = 0
            while (i < tileCoordinate.length) {
                when (tileCoordinate[i]) {
                    'w' -> {
                        tile.left--
                        tile.middle++
                    }
                    'e' -> {
                        tile.left++
                        tile.middle--
                    }
                    's' -> {
                        when (tileCoordinate[++i]) {
                            'w' -> {
                                tile.left--
                                tile.right++
                            }
                            'e' -> {
                                tile.middle--
                                tile.right++
                            }
                        }
                    }
                    'n' -> {
                        when (tileCoordinate[++i]) {
                            'w' -> {
                                tile.middle++
                                tile.right--
                            }
                            'e' -> {
                                tile.left++
                                tile.right--
                            }
                        }
                    }
                }
                i++
            }

            if (blackTiles.contains(tile)) {
                blackTiles.remove(tile)
            } else {
                blackTiles.add(tile)
            }
        }

        return blackTiles.size.toString()
    }

    override fun runPart2(inputFile: File): String {
        TODO("Not yet implemented")
    }
}
