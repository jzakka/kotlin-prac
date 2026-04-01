package effectiveckotlin.ch1


inline fun <T> Iterable<T>.filter(
    predicate: (T) -> Boolean
): List<T> {
    val destination = ArrayList<T>()
    for (element in this) {
        if (predicate(element)) {
            destination.add(element)
        }
    }
    return destination
}

inline fun <T, R: Any> Iterable<T>.mapNotNull(
    transform: (T) -> R?
): List<R> {
    return mapNotNullTo(ArrayList<R>(), transform)
}
