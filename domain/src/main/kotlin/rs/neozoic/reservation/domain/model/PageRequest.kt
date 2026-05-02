package rs.neozoic.reservation.domain.model

/**
 * Pagination parameters passed to repository ports and use cases.
 *
 * Use [Offset] for traditional page-number/size pagination; use [Cursor] for keyset
 * or cursor-based pagination where clients pass an opaque token between requests.
 */
sealed class PageRequest {

    /**
     * Offset-based (page-number) pagination parameters.
     *
     * @property page zero-based page index; 0 returns the first page
     * @property size maximum number of items to return per page; must be positive
     */
    data class Offset(val page: Int, val size: Int) : PageRequest()

    /**
     * Cursor-based (keyset) pagination parameters.
     *
     * @property cursor opaque cursor string returned by the previous page response; null requests the first page
     * @property size maximum number of items to return per page; must be positive
     */
    data class Cursor(val cursor: String?, val size: Int) : PageRequest()
}
