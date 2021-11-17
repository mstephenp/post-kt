package post.kt.models

data class Post(val id: Int, val content: String)

data class PostRequest(val content: String)