package solutions.day17

import util.are3DCoordinatesValid
import util.neighbours3D

// Arrangement: grid [ z ] [ x ] [ y ]
fun initialize3DGrid(cycles: Int, input: List<List<Boolean>>): Array<Array<BooleanArray>> {
    val gridSize = input.size + cycles * 2
    val grid = Array(gridSize) {
        Array(gridSize) {
            BooleanArray(gridSize) {
                false
            }
        }
    }

    // Place the input at the very middle of the 3D array
    for (x in input.indices) {
        for (y in input[x].indices) {
            grid[cycles + 1][cycles + x][cycles + y] = input[x][y]
        }
    }

    return grid
}

fun print3DGrid(grid: Array<Array<BooleanArray>>) {
    for (z in grid.indices) {
        println("z = $z")
        for (x in grid[z].indices) {
            for (y in grid[z][x].indices) {
                print((if (grid[z][x][y]) '#' else '.') + " ")
            }
            println()
        }
        println()
    }
}

fun clone3DGrid(grid: Array<Array<BooleanArray>>): Array<Array<BooleanArray>> {
    return Array(grid.size) { z ->
        Array(grid[z].size) { x ->
            BooleanArray(grid[z][x].size) { y ->
                grid[z][x][y]
            }
        }
    }
}

fun countActive3DNeighbours(z: Int, x: Int, y: Int, grid: Array<Array<BooleanArray>>): Int {
    return neighbours3D
        .filter { are3DCoordinatesValid(z + it[0], x + it[1], y + it[2], grid) }
        .map { grid[z + it[0]][x + it[1]][y + it[2]] }
        .filter { it }
        .count()
}

fun simulate3DCycle(grid: Array<Array<BooleanArray>>): Array<Array<BooleanArray>> {
    val gridSize = grid.size
    val result = clone3DGrid(grid)

    for (z in 0 until gridSize) {
        for (x in 0 until gridSize) {
            for (y in 0 until gridSize) {
                val activeNeighbours = countActive3DNeighbours(z, x, y, grid)
                result[z][x][y] = when (grid[z][x][y]) {
                    true -> activeNeighbours == 2 || activeNeighbours == 3
                    false -> activeNeighbours == 3
                }
            }
        }
    }

    return result
}

fun count3DActiveCubes(grid: Array<Array<BooleanArray>>): Int {
    return grid
        .map { z ->
            z.map { x ->
                x.filter { y ->
                    y
                }.count()
            }.sum()
        }.sum()
}
