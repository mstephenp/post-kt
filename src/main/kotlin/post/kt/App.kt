package post.kt

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import post.kt.handlers.*
import post.kt.services.PostDatabaseService

fun main() {
    val app = Javalin.create().start(8080)
    val databaseService = PostDatabaseService()

    app.routes {
        get("/posts") {
            getPosts(it, databaseService)
        }
        get("/posts/{postId}") {
            getPostById(it, databaseService)
        }
        post("/addPost") {
            addPost(it, databaseService)
        }
        patch("/updatePost/{postId}") {
            updatePost(it, databaseService)
        }
        delete("/deletePost/{postId}") {
            deletePost(it, databaseService)
        }
    }
}



