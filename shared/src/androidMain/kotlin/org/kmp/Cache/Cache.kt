import android.content.SharedPreferences
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

actual class Cache : KoinComponent {
    val sharedPrefs: SharedPreferences by inject()
    actual inline fun <reified T> save(key: String, value: T) {
        when (T::class) {
            String::class -> sharedPrefs.edit().putString(key, value as String).apply()

            Int::class -> sharedPrefs.edit().putInt(key, value as Int).apply()

            Float::class -> sharedPrefs.edit().putFloat(key, value as Float).apply()

            Boolean::class -> sharedPrefs.edit().putBoolean(key, value as Boolean).apply()

            Long::class -> sharedPrefs.edit().putLong(key, value as Long).apply()

            Set::class -> try {
                @Suppress("UNCHECKED_CAST")
                sharedPrefs.edit().putStringSet("list", (value as Set<String>))
            } finally {}
            else -> {}
        }
    }

    actual inline fun <reified T> load(key: String): T? {
        when (T::class) {
            String::class -> {
                return sharedPrefs.getString(key, "") as T
            }

            Int::class -> {
                return sharedPrefs.getInt(key, -1) as T
            }

            Float::class -> {
                return sharedPrefs.getFloat(key, -1f) as T
            }

            Boolean::class -> {
                return sharedPrefs.getBoolean(key, false) as T
            }

            Long::class -> {
                return sharedPrefs.getLong(key, -1L) as T
            }

            Set::class -> {
                return sharedPrefs.getStringSet(key, emptySet()) as T
            }

            else -> {
                return null
            }
        }
    }

    actual fun clear(key: String) {
        sharedPrefs.edit().remove(key).apply()
    }
}