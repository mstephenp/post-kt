package post.kt.database

import post.kt.models.Post

class PostDatabase {
    private val posts: HashMap<Int, Post>

    init {
        posts = initDatabase()
    }

    private fun initDatabase(): HashMap<Int, Post> {
        // load data into table
        return HashMap<Int, Post>()
    }

    private fun createPostId(): Int {
        return getNextPostId(posts.size + 1)
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