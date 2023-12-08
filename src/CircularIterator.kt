class CircularIterator<T>(val input: Iterable<T>): Iterator<T> {
    private var position = 0
    override fun hasNext(): Boolean {
        return true
    }

    override fun next(): T {
        if (position >= input.count()) {
            position = 0
        }
        val element: T = input.elementAt(position)
        position++
        return element
    }
}