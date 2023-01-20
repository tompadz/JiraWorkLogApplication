package data.models

data class BoardModel(
    val id: Int,
    val self: String,
    val name: String,
    val location: BoardLocation
)

data class BoardLocation(
    val projectId: Int,
    val displayName: String,
    val projectName: String,
    val projectKey: String,
    val projectTypeKey: String,
    val avatarURI: String,
)
