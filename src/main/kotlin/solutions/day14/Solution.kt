package solutions.day14

import solutions.GenericSolution
import util.readLines
import java.io.File

class Solution : GenericSolution {
    fun binaryToLong(binary: List<Int>): Long {
        var multiplier = 1L
        var number = 0L
        for (i in binary.count() - 1 downTo 0) {
            if (binary[i] == 1) {
                number += multiplier
            }
            multiplier *= 2
        }
        return number
    }

    fun applyMask(number: Long, mask: String): Long {
        val bits = mask.length

        val numberInBinary = number.toString(2).toCharArray().map { it.toInt() - 48 }

        val updatedNumberInBinary = mutableListOf<Int>()
        for (i in numberInBinary.size until bits) {
            updatedNumberInBinary.add(0)
        }
        updatedNumberInBinary.addAll(numberInBinary)

        for (i in 0 until bits) {
            if (mask[i] != 'X') {
                updatedNumberInBinary[i] = mask[i].toInt() - 48
            }
        }

        return binaryToLong(updatedNumberInBinary)
    }

    override fun runPart1(inputFile: File): String {
        val instructions = readLines(inputFile)

        var mask = ""
        val memory = HashMap<Long, Long>()
        for (instruction in instructions) {
            val components = instruction.split("=").map { it.trim() }
            if (components[0] == "mask") {
                mask = components[1]
                continue
            }

            val address = components[0].removePrefix("mem[").removeSuffix("]").toLong()
            val number = components[1].toLong()

            memory[address] = applyMask(number, mask)
        }

        return memory.values.sum().toString()
    }

    override fun runPart2(inputFile: File): String {
        TODO("Not yet implemented")
    }
}
