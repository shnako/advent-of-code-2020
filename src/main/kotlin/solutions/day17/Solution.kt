package solutions.day17

import solutions.GenericSolution
import util.are3DCoordinatesValid
import util.neighbours3D
import util.read2dCharArray
import java.io.File

class Solution : GenericSolution {
    private fun parseInput(inputFile: File): List<List<Boolean>> {
        return read2dCharArray(inputFile)
            .map { it.map { ch -> ch == '#' } }
    }

    // Arrangement: grid [ z ] [ x ] [ y ]
    private fun initializeGrid(cycles: Int, input: List<List<Boolean>>): Array<Array<BooleanArray>> {
        val gridSize = input.size + cycles * 2
        val grid = Array(gridSize) {
            Array(gridSize) {
                BooleanArray(gridSize) {
                    false
                }
            }
        }

        // Place the input at the very middle of the 3d array
        for (x in input.indices) {
            for (y in input[x].indices) {
                grid[cycles + 1][cycles + x][cycles + y] = input[x][y]
            }
        }

        return grid
    }

    private fun printGrid(grid: Array<Array<BooleanArray>>) {
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

    private fun cloneGrid(grid: Array<Array<BooleanArray>>): Array<Array<BooleanArray>> {
        return Array(grid.size) { z ->
            Array(grid[z].size) { x ->
                BooleanArray(grid[z][x].size) { y ->
                    //TODO Do I need copyOf?
                    grid[z][x][y]
                }
            }
        }
    }

    private fun countActiveNeighbours(z: Int, x: Int, y: Int, grid: Array<Array<BooleanArray>>): Int {
        return neighbours3D
            .filter { are3DCoordinatesValid(z + it[0], x + it[1], y + it[2], grid) }
            .map { grid[z + it[0]][x + it[1]][y + it[2]] }
            .filter { it }
            .count()
    }

    private fun simulateCycle(grid: Array<Array<BooleanArray>>): Array<Array<BooleanArray>> {
        val gridSize = grid.size
        val result = cloneGrid(grid)

        for (z in 0 until gridSize) {
            for (x in 0 until gridSize) {
                for (y in 0 until gridSize) {
                    val activeNeighbours = countActiveNeighbours(z, x, y, grid)
                    result[z][x][y] = when (grid[z][x][y]) {
                        true -> activeNeighbours == 2 || activeNeighbours == 3
                        false -> activeNeighbours == 3
                    }
                }
            }
        }

        return result
    }

    private fun countActiveCubes(grid: Array<Array<BooleanArray>>): Int {
        return grid
            .map { z ->
                z.map { x ->
                    x.filter { y ->
                        y
                    }.count()
                }.sum()
            }.sum()
    }

    override fun runPart1(inputFile: File): String {
        val cycles = 6
        val input = parseInput(inputFile)
        var grid = initializeGrid(cycles, input)
        println("Initial grid:")
        printGrid(grid)
        for (cycle in 1..cycles) {
            println()
            println("Cycle $cycle")
            grid = simulateCycle(grid)
            printGrid(grid)
        }

        return countActiveCubes(grid).toString()
    }

    override fun runPart2(inputFile: File): String {
        val input = parseInput(inputFile)
//        val grid = initializeGrid(inputFile)
        TODO("Not yet implemented")
    }
}
