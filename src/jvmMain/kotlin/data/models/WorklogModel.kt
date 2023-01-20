package data.models


data class WorklogModel(
    val self:String,
    val author:UserModel,
    val timeSpent:String,
    val created:String,
)
