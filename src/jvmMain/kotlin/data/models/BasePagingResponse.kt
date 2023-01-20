package data.models

data class BasePagingResponse<T>(
    val startAt: Int,
    val maxResults: Int,
    val total: Int,
    val values: List<T>,
    val issues: List<IssueModel>?
)
