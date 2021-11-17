package post.kt.database

import post.kt.models.Post

class PostDatabase {
    private val posts: HashMap<Int, Post>

    init {
        posts = initDatabase()
    }

    private fun initDatabase(): HashMap<Int, Post> {
        return hashMapOf(
            1 to Post(1, "content for post 1"),
            2 to Post(2, "content for post 2"),
            3 to Post(3, "content for post 3")
        )
    }

    private fun createPostId(): Int {
        return getNextPostId(posts.size)
    }

    private fun getNextPostId(id: Int): Int = when {
        posts.filterKeys { it == id }.isNotEmpty() -> getNextPostId(id + 1)
        else -> id
    }

    fun getAllPosts(): MutableCollection<Post> = posts.values

    fun getPost(id: Int): Post? {
        return posts[id]
    }

    fun addPost(content: String) {
        val id = createPostId()
        posts[id] = Post(id, content)
    }

    fun updatePost(id: Int, content: String) {
        posts[id] = Post(id, content)
    }

    fun deletePost(id: Int) {
        posts.remove(id)
    }
}