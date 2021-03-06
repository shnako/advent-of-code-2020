package solutions.day23

data class Node(
    val value: Int,
    var next: Node? = null,
)

/**
 * Beware - there are bugs in this code.
 * This class is only intended to work for the specified input set.
 * It also contains methods that are no longer used.
 */
class MyLinkedList(items: Collection<Int>, maxSize: Int) {
    private var first: Node
    private var last: Node
    private var size: Int
    private var valueMap: Array<Node?>

    init {
        val iterator = items.iterator()

        first = Node(iterator.next())
        last = first
        size = 1
        valueMap = Array(maxSize + 1) { null }
        valueMap[first.value] = first

        while (iterator.hasNext()) {
            add(iterator.next())
        }

        this.size = items.size
    }

    fun peek(): Int {
        if (size == 0) {
            return -1
        }
        return first.value
    }

    fun pop(): Int {
        if (size == 0) {
            return -1
        }
        val oldFirst = first
        valueMap[oldFirst.value] = null
        size--
        if (size > 0 && first.next != null) {
            first = first.next!!
        }
        return oldFirst.value
    }

    fun moveFirstXAfterValue(elements: Int, afterValue: Int) {
        val afterNode = valueMap[afterValue]

        val segmentFirst = first
        var segmentLast = segmentFirst
        for (i in 1 until elements) {
            segmentLast = segmentLast.next!!
        }

        first = segmentLast.next!!
        segmentLast.next = afterNode!!.next
        afterNode.next = segmentFirst

        if (afterNode == last) {
            last = segmentLast
        }
    }

    fun add(value: Int) {
        val newNode = Node(value)
        valueMap[value] = newNode
        if (size > 0) {
            last.next = newNode
        }
        last = newNode
        size++
    }

    fun insertAfterValue(afterValue: Int, newValue: Int) {
        val newNode = Node(newValue)
        valueMap[newValue] = newNode
        val afterNode = valueMap[afterValue]
        if (afterNode == last) {
            afterNode.next = newNode
            last = newNode
        } else {
            newNode.next = afterNode?.next
            afterNode!!.next = newNode
        }
        size++
    }

    fun indexOf(value: Int): Int {
        var node: Node? = first
        var index = 0
        while (node != null) {
            if (node.value == value) {
                return index
            }
            node = node.next
            index++
        }

        return -1
    }

    fun get(index: Int): Int {
        if (index < 0 || index >= size) {
            return -1
        }

        var node = first
        for (i in 0 until index) {
            node = node.next!!
        }

        return node.value
    }

    fun size(): Int {
        return size
    }

    fun isNotEmpty(): Boolean {
        return size != 0
    }

    fun max(): Int {
        var max = -1
        var node: Node? = first
        while (node != null) {
            if (node.value > max) {
                max = node.value
            }
            node = node.next
        }
        return max
    }

    fun contains(value: Int): Boolean {
        if (value < 0 || value > valueMap.size) {
            return false
        }
        return valueMap[value] != null
    }

    override fun toString(): String {
        val result = StringBuilder()

        var node: Node? = first
        while (node != null) {
            result.append(node.value).append(", ")
            node = node.next
        }

        return result.toString()
    }
}
