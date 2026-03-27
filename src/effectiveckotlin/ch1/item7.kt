package effectiveckotlin.ch1

inline fun <reified T>String.readObjectOrNull(): T? {
    if (false) {
        return null
    }

    return null
}

inline fun <reified T>String.readObject(): Result<T> {
    if (false) {
        return Result.failure(JsonParsingException())
    }
    var result: T = null as T
    return Result.success(result)
}

class JsonParsingException : Exception()

fun useReadObject(showPersonAge: (Person) -> Unit, showError: (Throwable) -> Unit) {
    val userText: String = "test"
    userText.readObject<Person>()
        .onSuccess { showPersonAge(it) }
        .onFailure { showError(it) }


    val age = userText.readObjectOrNull<Person>()?.age ?: -1
//    val printer: Printer? = getFirstAvailablePrinter()
//    printer?.print()
//    if (printer != null) printer.print()
}
