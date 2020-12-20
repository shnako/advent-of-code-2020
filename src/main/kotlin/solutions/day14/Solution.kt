package solutions.day14

import solutions.GenericSolution
import util.binaryToLong
import util.longToBinary
import util.readLines
import java.io.File

@Suppress("DuplicatedCode") // Keeping some small duplicates for clarity.
class Solution : GenericSolution {

    private fun applyMask(number: Long, mask: String): Long {
        val bits = mask.length

        val numberInBinary = longToBinary(number, mask.length)

        for (i in 0 until bits) {
            if (mask[i] != 'X') {
                numberInBinary[i] = mask[i].toInt() - 48
            }
        }

        return binaryToLong(numberInBinary)
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

    private fun buildAddress(bit: Int, numberInBinary: MutableList<Int>, mask: String, result: MutableList<Long>) {
        if (bit == mask.length) {
            result.add(binaryToLong(numberInBinary))
            return
        }

        if (mask[bit] == '0') {
            buildAddress(bit + 1, numberInBinary, mask, result)
        }

        if (mask[bit] == '1' || mask[bit] == 'X') {
            numberInBinary[bit] = 1
            buildAddress(bit + 1, numberInBinary, mask, result)
        }

        if (mask[bit] == 'X') {
            numberInBinary[bit] = 0
            buildAddress(bit + 1, numberInBinary, mask, result)
        }
    }

    private fun getAddresses(address: Long, mask: String): List<Long> {
        val result = mutableListOf<Long>()
        val numberInBinary = longToBinary(address, mask.length)

        buildAddress(0, numberInBinary, mask, result)

        return result
    }

    override fun runPart2(inputFile: File): String {
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

            val addressesToWriteTo = getAddresses(address, mask)
            for (addressToWriteTo in addressesToWriteTo) {
                memory[addressToWriteTo] = number
            }
        }

        return memory.values.sum().toString()
    }
}
