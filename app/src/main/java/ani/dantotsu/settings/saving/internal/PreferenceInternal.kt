package ani.shiroin.settings.saving.internal

import kotlin.reflect.KClass


data class Pref(
    val prefLocation: Location,
    val type: KClass<*>,
    val default: Any
)

enum class Location(val location: String, val exportable: Boolean) {
    General("ani.shiroin.general", true),
    UI("ani.shiroin.ui", true),
    Player("ani.shiroin.player", true),
    Reader("ani.shiroin.reader", true),
    NovelReader("ani.shiroin.novelReader", true),
    Irrelevant("ani.shiroin.irrelevant", false),
    AnimeDownloads("animeDownloads", false),  //different for legacy reasons
    Protected("ani.shiroin.protected", true),
}
