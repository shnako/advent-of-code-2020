package solutions.day12

import solutions.GenericSolution
import util.readLines
import java.io.File
import java.security.InvalidParameterException
import kotlin.math.abs

private const val NORTH = 'N'
private const val EAST = 'E'
private const val SOUTH = 'S'
private const val WEST = 'W'
private const val LEFT = 'L'
private const val RIGHT = 'R'
private const val FORWARD = 'F'

private data class ShipPosition(
    var x: Int,
    var y: Int,
    var bearing: Char
)

class Solution : GenericSolution {
    private fun turn(shipPosition: ShipPosition, action: Char, value: Int) {
        for (turn in 0 until value / 90) {
            when (action) {
                LEFT -> {
                    shipPosition.bearing = when (shipPosition.bearing) {
                        NORTH -> WEST
                        WEST -> SOUTH
                        SOUTH -> EAST
                        EAST -> NORTH
                        else -> throw InvalidParameterException()
                    }
                }
                RIGHT -> {
                    shipPosition.bearing = when (shipPosition.bearing) {
                        NORTH -> EAST
                        EAST -> SOUTH
                        SOUTH -> WEST
                        WEST -> NORTH
                        else -> throw InvalidParameterException()
                    }
                }
            }
        }
    }

    // North & East positive, South & West negative
    private fun navigate(shipPosition: ShipPosition, action: Char, value: Int) {
        when (action) {
            NORTH -> shipPosition.x += value
            EAST -> shipPosition.y += value
            SOUTH -> shipPosition.x -= value
            WEST -> shipPosition.y -= value
            LEFT -> turn(shipPosition, action, value)
            RIGHT -> turn(shipPosition, action, value)
            FORWARD -> navigate(shipPosition, shipPosition.bearing, value)
        }
    }

    private fun navigate(shipPosition: ShipPosition, instructions: List<String>) {
        instructions.forEach {
            val action = it[0]
            val value = it.substring(1).toInt()
            navigate(shipPosition, action, value)
        }
    }

    override fun runPart1(inputFile: File): String {
        val instructions = readLines(inputFile)

        val shipPosition = ShipPosition(0, 0, EAST)
        navigate(shipPosition, instructions)

        return (abs(shipPosition.x) + abs(shipPosition.y)).toString()
    }

    override fun runPart2(inputFile: File): String {
        TODO("Not yet implemented")
    }
}
