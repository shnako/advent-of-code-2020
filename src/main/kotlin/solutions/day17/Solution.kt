package solutions.day17

import solutions.GenericSolution
import util.read2dCharArray
import java.io.File

@SuppressWarnings("kotlin:S125") // I want to keep the print statements commented out.
class Solution : GenericSolution {
    private val cycles = 6
    private fun parseInput(inputFile: File): List<List<Boolean>> {
        return read2dCharArray(inputFile)
            .map { it.map { ch -> ch == '#' } }
    }

    override fun runPart1(inputFile: File): String {
        val input = parseInput(inputFile)
        var grid = initialize3DGrid(cycles, input)
//        println("Initial grid:")
//        print3DGrid(grid)
        for (cycle in 1..cycles) {
//            println()
//            println("Cycle $cycle")
            grid = simulate3DCycle(grid)
//            print3DGrid(grid)
        }

        return count3DActiveCubes(grid).toString()
    }

    override fun runPart2(inputFile: File): String {
        val input = parseInput(inputFile)
        var grid = initialize4DGrid(cycles, input)
//        println("Initial grid:")
//        print4DGrid(grid)
        for (cycle in 1..cycles) {
//            println()
//            println("Cycle $cycle")
            grid = simulate4DCycle(grid)
//            print4DGrid(grid)
        }

        return count4DActiveCubes(grid).toString()
    }
}
