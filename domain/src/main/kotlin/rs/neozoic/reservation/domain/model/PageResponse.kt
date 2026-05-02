package rs.neozoic.reservation.domain.model

/**
 * Generic paginated result container for both offset and cursor pagination strategies.
 *
 * Fields relevant only to offset pagination are null when produced by a cursor-based query,
 * and vice versa. Callers should check which [PageRequest] subtype was used to determine
 * which fields are populated.
 *
 * @property content the items on the current page; never null, may be empty
 * @property size the maximum page size that was requested
 * @property page zero-based page index; null for cursor-based responses
 * @property totalElements total number of matching items across all pages; null for cursor-based responses
 * @property totalPages total number of pages given the requested size; null for cursor-based responses
 * @property nextCursor opaque cursor pointing to the next page; null if there is no next page or for offset responses
 * @property prevCursor opaque cursor pointing to the previous page; null if there is no previous page or for offset responses
 * @property hasNext whether a next page exists; null for offset-based responses
 * @property hasPrevious whether a previous page exists; null for offset-based responses
 */
data class PageResponse<T>(
    val content: List<T>,
    val size: Int,
    val page: Int? = null,
    val totalElements: Long? = null,
    val totalPages: Int? = null,
    val nextCursor: String? = null,
    val prevCursor: String? = null,
    val hasNext: Boolean? = null,
    val hasPrevious: Boolean? = null
)
