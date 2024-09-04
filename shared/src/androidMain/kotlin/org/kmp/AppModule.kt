import android.content.Context
import android.content.SharedPreferences
import org.kmp.Repositories.BusScheduleRepository
import org.kmp.ktor.KtorClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val networkModule = module {
    singleOf (::KtorClient )
}

val cacheModule = module {
    singleOf(::Cache)
}
val storageModule = module {
    single { provideSharedPrefs(androidContext()) }
}

val repositoryModule = module {
    singleOf(::BusScheduleRepository)
}

fun provideSharedPrefs(context: Context): SharedPreferences {
    return context.getSharedPreferences("pref", Context.MODE_PRIVATE)
}

fun appModule() = listOf(networkModule, cacheModule, storageModule, repositoryModule)