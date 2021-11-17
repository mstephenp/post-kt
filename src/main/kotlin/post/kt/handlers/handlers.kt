package post.kt.handlers

import io.javalin.http.Context
import io.javalin.http.HttpCode
import post.kt.models.PostRequest
import post.kt.services.PostDatabaseService

fun getPosts(ctx: Context, databaseService: PostDatabaseService) {
    val posts = databaseService.getPosts()
    ctx.json(posts)
}

fun getPostById(ctx: Context, databaseService: PostDatabaseService) {
    val id = ctx.pathParam("postId").toInt()
    val post = databaseService.getPost(id)
    ctx.json(post)
}

fun addPost(ctx: Context, databaseService: PostDatabaseService) {
    val request = ctx.bodyAsClass<PostRequest>()
    databaseService.addPost(request)
    ctx.status(HttpCode.OK)
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