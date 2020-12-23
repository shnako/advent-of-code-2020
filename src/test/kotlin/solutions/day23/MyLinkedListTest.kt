package solutions.day23

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class MyLinkedListTest {
    private lateinit var testList: MyLinkedList

    @BeforeEach
    internal fun setUp() {
        testList = MyLinkedList(listOf(1, 2, 3), 100)
    }

    @Test
    fun peek() {
        assertEquals(1, testList.peek())
        assertEquals(1, testList.peek())

        assertEquals(1, testList.pop())
        assertEquals(2, testList.pop())
        assertEquals(3, testList.pop())

        assertEquals(-1, testList.peek())
    }

    @Test
    fun pop() {
        val popped = testList.pop()
        assertEquals(2, testList.size())
        assertFalse(testList.contains(1))

        assertEquals(1, popped)
        assertEquals(2, testList.peek())
        assertEquals(2, testList.pop())
        assertEquals(3, testList.pop())
        assertEquals(-1, testList.pop())

        assertFalse(testList.isNotEmpty())
    }

    @Test
    fun add() {
        testList.add(4)
        assertEquals(4, testList.size())
        assertTrue(testList.contains(4))

        assertEquals(1, testList.pop())
        assertEquals(2, testList.pop())
        assertEquals(3, testList.pop())
        assertEquals(4, testList.pop())

        assertFalse(testList.isNotEmpty())
    }

    @Test
    fun insertAfterValue() {
        testList.insertAfterValue(2, 4)
        assertTrue(testList.contains(4))

        assertEquals(4, testList.size())

        assertEquals(1, testList.pop())
        assertEquals(2, testList.pop())
        assertEquals(4, testList.pop())
        assertEquals(3, testList.pop())

        assertFalse(testList.isNotEmpty())
    }

    @Test
    fun insertAfterLastValue() {
        testList.insertAfterValue(3, 4)

        assertEquals(4, testList.size())
        assertTrue(testList.contains(4))

        assertEquals(1, testList.pop())
        assertEquals(2, testList.pop())
        assertEquals(3, testList.pop())
        assertEquals(4, testList.pop())

        assertFalse(testList.isNotEmpty())
    }

    @Test
    fun size() {
        assertEquals(3, testList.size())
    }

    @Test
    fun indexOf() {
        assertEquals(-1, testList.indexOf(4))
        assertEquals(0, testList.indexOf(1))
        assertEquals(1, testList.indexOf(2))
        assertEquals(2, testList.indexOf(3))
    }

    @Test
    fun get() {
        assertEquals(-1, testList.get(-1))
        assertEquals(1, testList.get(0))
        assertEquals(2, testList.get(1))
        assertEquals(3, testList.get(2))
        assertEquals(-1, testList.get(3))
    }

    @Test
    fun contains() {
        assertTrue(testList.contains(1))
        assertTrue(testList.contains(2))
        assertTrue(testList.contains(3))

        assertFalse(testList.contains(-1))
        assertFalse(testList.contains(4))
    }

    @Test
    fun isNotEmpty() {
        assertTrue(testList.isNotEmpty())
    }

    @Test
    fun max() {
        assertEquals(3, testList.max())
    }
}
