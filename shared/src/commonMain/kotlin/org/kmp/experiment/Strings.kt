import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc

expect class Strings {
    fun get(id: StringResource, args: List<Any>): String
}

fun getString(stringId: StringResource): StringDesc {
    return StringDesc.Resource(stringId)
}