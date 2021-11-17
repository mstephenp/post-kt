package post.kt.services

import post.kt.database.PostDatabase
import post.kt.models.Post
import post.kt.models.PostRequest
import java.util.*

class PostDatabaseService {

    private val postDatabase = PostDatabase()

    fun getPosts(): Collection<Post> {
        return postDatabase.getAllPosts()
    }

    fun addPost(request: PostRequest) {
        postDatabase.addPost(request.content)
    }

    fun getPost(id: Int): Optional<Post> {
        val post = postDatabase.getPost(id)
        return if (post != null) {
            Optional.of(post)
        } else {
            Optional.empty()
        }
    }

    fun updatePost(id: Int, request: PostRequest) {
        postDatabase.updatePost(id, request.content)
    }

    fun deletePost(id: Int) {
        postDatabase.deletePost(id)
    }
}