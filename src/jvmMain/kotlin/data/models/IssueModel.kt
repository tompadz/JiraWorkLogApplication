package data.models

data class IssueModel(
    val id:String,
    val self:String,
    val key:String,
    val fields : IssueFields
)

data class IssueFields(
    val summary:String,
    val timetracking:IssueTimeTracking,
    val issuetype: IssueTypeModel?,
    val progress: IssueProgressModel,
    val worklog: PagingWorkLogModel,
    val status : IssueStatus,
)

data class IssueStatus(
    val name : String,
)

data class PagingWorkLogModel(
    val worklogs : List<WorklogModel>,
    val maxResults:Int,
    val total:Int,
)

data class IssueTypeModel(
    val iconUrl:String,
)

data class IssueProgressModel(
    val percent:Int
)

data class IssueTimeTracking(
    val originalEstimate:String,
    val remainingEstimate:String,
    val timeSpent:String?,
    val originalEstimateSeconds:Long,
    val remainingEstimateSeconds:Long,
    val timeSpentSeconds:Long?
)
