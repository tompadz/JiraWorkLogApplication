package data.models

data class SprintModel(
    val id:Int,
    val self:String,
    val state:String,
    val name:String,
    val startDate:String,
    val endDate:String,
)
