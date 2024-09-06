import platform.Foundation.*
import platform.darwin.NSInteger

actual class Cache {
    val userDefaults: NSUserDefaults = NSUserDefaults.standardUserDefaults

    actual inline fun <reified T> save(key: String, value: T) {
        when (T::class) {
            String::class -> userDefaults.setObject(value as String, forKey = key)

            Int::class, NSInteger::class -> userDefaults.setInteger(value as NSInteger, forKey = key)

            Float::class -> userDefaults.setFloat(value as Float, forKey = key)

            Boolean::class -> userDefaults.setBool(value as Boolean, forKey = key)

            Long::class -> userDefaults.setInteger(value as Long, forKey = key)

            else -> throw IllegalArgumentException("Unsupported type")
        }
        userDefaults.synchronize()
    }

    actual inline fun <reified T> load(key: String): T? {
        return when (T::class) {
            String::class -> userDefaults.stringForKey(key) as T?

            Int::class, NSInteger::class -> userDefaults.integerForKey(key) as T?

            Float::class -> userDefaults.floatForKey(key) as T?

            Boolean::class -> userDefaults.boolForKey(key) as T?

            Long::class -> userDefaults.integerForKey(key) as T?

            else -> null
        }
    }

    actual fun clear(key: String) {
        userDefaults.removeObjectForKey(key)
        userDefaults.synchronize()
    }
}
