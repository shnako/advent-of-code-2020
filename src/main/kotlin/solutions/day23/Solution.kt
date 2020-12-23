package solutions.day23

import solutions.GenericSolution
import util.readFileAsString
import java.io.File
import kotlin.math.max

class Solution : GenericSolution {
    private fun readCups(inputFile: File, maxSize: Int): MyLinkedList {
        val cups = readFileAsString(inputFile)
            .toCharArray()
            .dropLast(1)
            .map { it.toInt() - 48 }

        return MyLinkedList(cups, if (maxSize == -1) cups.size else maxSize)
    }

    private fun findValidLabel(current: Int, max: Int, nextCup1: Int, nextCup2: Int, nextCup3: Int): Int {
        var nextLabel = current

        while (true) {
            nextLabel--
            if (nextLabel == 0) {
                nextLabel = max + 1
                continue
            }
            if (nextLabel != nextCup1 && nextLabel != nextCup2 && nextLabel != nextCup3) {
                return nextLabel
            }
        }
    }

    private fun playMove(cups: MyLinkedList, max: Int) {
        val current = cups.pop()
        val nextCup1 = cups.pop()
        val nextCup2 = cups.pop()
        val nextCup3 = cups.pop()

        val destinationCupLabel = findValidLabel(current, max, nextCup1, nextCup2, nextCup3)

        cups.insertAfterValue(destinationCupLabel, nextCup3)
        cups.insertAfterValue(destinationCupLabel, nextCup2)
        cups.insertAfterValue(destinationCupLabel, nextCup1)

        cups.add(current)
    }

    private fun getResult(cups: MyLinkedList): Long {
        while (cups.peek() != 1) {
            cups.add(cups.pop())
        }

        cups.pop()

        var result = 0L
        while (cups.isNotEmpty()) {
            result = result * 10 + cups.pop()
        }

        return result
    }

    override fun runPart1(inputFile: File): String {
        val cups = readCups(inputFile, -1)
        val max = cups.max()
        for (i in 1..100) {
            playMove(cups, max)
        }
        return getResult(cups).toString()
    }

    override fun runPart2(inputFile: File): String {
        val maxCup = 1000000
        val cups = readCups(inputFile, maxCup)
        for (i in (cups.max() + 1)..maxCup) {
            cups.add(i)
        }

        for (i in 1..10000000) {
            playMove(cups, maxCup)
            if (i % 1000000 == 0) {
                println(i)
            }
        }

        val index1 = cups.indexOf(1)
        return (cups.get((index1 + 1) % maxCup).toLong()
                * cups.get((index1 + 2) % maxCup).toLong())
            .toString()
    }
}
