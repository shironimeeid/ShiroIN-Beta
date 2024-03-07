package ani.shiroin.media.manga

import ani.shiroin.media.MediaDetailsViewModel
import ani.shiroin.media.SourceAdapter
import ani.shiroin.media.SourceSearchDialogFragment
import ani.shiroin.parsers.ShowResponse
import kotlinx.coroutines.CoroutineScope

class MangaSourceAdapter(
    sources: List<ShowResponse>,
    val model: MediaDetailsViewModel,
    val i: Int,
    val id: Int,
    fragment: SourceSearchDialogFragment,
    scope: CoroutineScope
) : SourceAdapter(sources, fragment, scope) {
    override suspend fun onItemClick(source: ShowResponse) {
        model.overrideMangaChapters(i, source, id)
    }
}