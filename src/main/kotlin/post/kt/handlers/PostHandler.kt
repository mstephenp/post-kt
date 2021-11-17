package post.kt.handlers

import io.javalin.http.Context
import io.javalin.http.HttpCode
import post.kt.models.PostRequest
import post.kt.services.PostDatabaseService

class PostHandler() {
    companion object {

        fun getPosts(ctx: Context, databaseService: PostDatabaseService) {
            val posts = databaseService.getPosts()
            ctx.json(posts)
        }

        fun getPostById(ctx: Context, databaseService: PostDatabaseService) {
            val id = ctx.pathParam("postId").toInt()
            val post = databaseService.getPost(id)
            if (post.isPresent) {
                ctx.json(post.get())
            } else {
                ctx.json("")
            }
        }

        fun addPost(ctx: Context, databaseService: PostDatabaseService) {
            val request = ctx.bodyAsClass<PostRequest>()
            if (request.content.isEmpty()) {
                ctx.status(HttpCode.NOT_FOUND)
            } else {
                databaseService.addPost(request)
                ctx.status(HttpCode.OK)
            }
        }

        fun updatePost(ctx: Context, databaseService: PostDatabaseService) {
            val id = ctx.pathParam("postId").toInt()
            val request = ctx.bodyAsClass<PostRequest>()
            databaseService.updatePost(id, request)
            ctx.status(HttpCode.OK)
        }

        fun deletePost(ctx: Context, databaseService: PostDatabaseService) {
            val id = ctx.pathParam("postId").toInt()
            databaseService.deletePost(id)
            ctx.status(HttpCode.OK)
        }
    }
}