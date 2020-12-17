package solutions.day17

import util.are4DCoordinatesValid
import util.neighbours4D

// Arrangement: grid [ w ][ z ] [ x ] [ y ]
fun initialize4DGrid(cycles: Int, input: List<List<Boolean>>): Array<Array<Array<BooleanArray>>> {
    val gridSize = input.size + cycles * 2
    val grid = Array(gridSize) {
        Array(gridSize) {
            Array(gridSize) {
                BooleanArray(gridSize) {
                    false
                }
            }
        }
    }

    // Place the input at the very middle of the 4D array
    for (x in input.indices) {
        for (y in input[x].indices) {
            grid[cycles + 1][cycles + 1][cycles + x][cycles + y] = input[x][y]
        }
    }

    return grid
}

fun print4DGrid(grid: Array<Array<Array<BooleanArray>>>) {
    for (w in grid.indices) {
        for (z in grid[w].indices) {
            println("w = $w, z = $z")
            for (x in grid[w][z].indices) {
                for (y in grid[w][z][x].indices) {
                    print((if (grid[w][z][x][y]) '#' else '.') + " ")
                }
                println()
            }
            println()
        }
    }
}

fun clone4DGrid(grid: Array<Array<Array<BooleanArray>>>): Array<Array<Array<BooleanArray>>> {
    return Array(grid.size) { w ->
        Array(grid[w].size) { z ->
            Array(grid[w][z].size) { x ->
                BooleanArray(grid[w][z][x].size) { y ->
                    grid[w][z][x][y]
                }
            }
        }
    }
}

fun countActive4DNeighbours(w: Int, z: Int, x: Int, y: Int, grid: Array<Array<Array<BooleanArray>>>): Int {
    return neighbours4D
        .filter { are4DCoordinatesValid(w + it[0], z + it[1], x + it[2], y + it[3], grid) }
        .map { grid[w + it[0]][z + it[1]][x + it[2]][y + it[3]] }
        .filter { it }
        .count()
}

@SuppressWarnings("kotlin:S3776")
fun simulate4DCycle(grid: Array<Array<Array<BooleanArray>>>): Array<Array<Array<BooleanArray>>> {
    val gridSize = grid.size
    val result = clone4DGrid(grid)

    for (w in 0 until gridSize) {
        for (z in 0 until gridSize) {
            for (x in 0 until gridSize) {
                for (y in 0 until gridSize) {
                    val activeNeighbours = countActive4DNeighbours(w, z, x, y, grid)
                    result[w][z][x][y] = when (grid[w][z][x][y]) {
                        true -> activeNeighbours == 2 || activeNeighbours == 3
                        false -> activeNeighbours == 3
                    }
                }
            }
        }
    }

    return result
}

fun count4DActiveCubes(grid4D: Array<Array<Array<BooleanArray>>>): Int {
    return grid4D
        .map { w ->
            w.map { z ->
                z.map { x ->
                    x.filter { y ->
                        y
                    }.count()
                }.sum()
            }.sum()
        }.sum()
}
