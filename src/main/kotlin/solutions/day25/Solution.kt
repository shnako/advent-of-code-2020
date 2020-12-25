package solutions.day25

import solutions.GenericSolution
import util.readLines
import java.io.File

/**
 * Part 1:
 * Simply calculate according to the instructions.
 * Since the resulting encryption key is the same when calculating it from either side,
 * we only need to do it from one side, hence the commented code below.
 *
 * Part 2:
 * The challenge for part 2 requires all previous 49 stars in order to get the 50th.
 * There is no coding challenge.
 */
class Solution : GenericSolution {
    private val publicKeySubjectNumber = 7
    private val divisor = 20201227

    private fun calculateLoopSize(publicKey: Long): Int {
        var value = 1L
        var loopSize = 0
        while (value != publicKey) {
            value = (value * publicKeySubjectNumber) % divisor
            loopSize++
        }
        return loopSize
    }

    private fun calculateEncryptionKey(subjectNumber: Long, loopSize: Int): Long {
        var value = 1L
        repeat(loopSize) {
            value = (value * subjectNumber) % divisor
        }
        return value
    }

    @Suppress("kotlin:S125")
    override fun runPart1(inputFile: File): String {
        val input = readLines(inputFile)
        val publicKey1 = input[0].toLong()
        val publicKey2 = input[1].toLong()

        val loopSize1 = calculateLoopSize(publicKey1)
//        val loopSize2 = calculateLoopSize(publicKey2)

        val encryptionKey1 = calculateEncryptionKey(publicKey2, loopSize1)
//        val encryptionKey2 = calculateEncryptionKey(publicKey1, loopSize2)

        return encryptionKey1.toString()
    }

    override fun runPart2(inputFile: File): String {
        return "Merry Christmas!"
    }
}
