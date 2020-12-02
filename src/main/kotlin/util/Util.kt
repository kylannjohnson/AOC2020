package util

fun getResourceAsText(path: String): List<String> {
    return try {
        object {}.javaClass.classLoader.getResourceAsStream(path)!!.bufferedReader().readLines()
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    }
}
