package post.kt.server

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import post.kt.handlers.PostHandler
import post.kt.services.PostDatabaseService

class PostServer {

    fun create(): Javalin {
        val app = Javalin.create() { it.enableDevLogging() }
        val databaseService = PostDatabaseService()

        app.routes {
            get("/posts") {
                PostHandler.getPosts(it, databaseService)
            }
            get("/posts/{postId}") {
                PostHandler.getPostById(it, databaseService)
            }
            post("/addPost") {
                PostHandler.addPost(it, databaseService)
            }
            patch("/updatePost/{postId}") {
                PostHandler.updatePost(it, databaseService)
            }
            delete("/deletePost/{postId}") {
                PostHandler.deletePost(it, databaseService)
            }
        }
        return app
    }
}