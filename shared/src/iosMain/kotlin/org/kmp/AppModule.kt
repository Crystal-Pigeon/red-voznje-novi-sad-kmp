import org.kmp.Cache.CacheManager
import org.kmp.Repositories.BusScheduleRepository
import org.kmp.ktor.KtorClient
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val networkModule = module {
    singleOf(::KtorClient)
}

val cacheModule = module {
    singleOf(::Cache)
    single { CacheManager() }
}

val repositoryModule = module {
    singleOf(::BusScheduleRepository)
}

fun appModule() = listOf(networkModule, cacheModule, repositoryModule)

fun initKoin() {
    startKoin { appModule() }
}