package post.kt.server

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder
import post.kt.handlers.PostHandler
import post.kt.services.PostDatabaseService

class PostServer {

    fun create(): Javalin {
        val app = Javalin.create()
        val databaseService = PostDatabaseService()

        app.routes {
            ApiBuilder.get("/posts") {
                PostHandler.getPosts(it, databaseService)
            }
            ApiBuilder.get("/posts/{postId}") {
                PostHandler.getPostById(it, databaseService)
            }
            ApiBuilder.post("/addPost") {
                PostHandler.addPost(it, databaseService)
            }
            ApiBuilder.patch("/updatePost/{postId}") {
                PostHandler.updatePost(it, databaseService)
            }
            ApiBuilder.delete("/deletePost/{postId}") {
                PostHandler.deletePost(it, databaseService)
            }
        }
        return app
    }
}