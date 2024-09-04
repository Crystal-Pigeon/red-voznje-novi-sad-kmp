expect class Cache {
    inline fun <reified T> save(key: String, value: T)
    inline fun <reified T> load(key: String): T?
    fun clear(key: String)
}
