package post.kt

import io.javalin.http.Context
import io.javalin.http.HttpCode
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import post.kt.handlers.PostHandler
import post.kt.models.Post
import post.kt.models.PostRequest
import post.kt.services.PostDatabaseService

class UnitTests {

    private val ctx = mockk<Context>(relaxed = true)

    @Test
    fun `POST to create post gives OK`() {
        every { ctx.bodyAsClass<PostRequest>() } returns PostRequest("this is some content")
        PostHandler.addPost(ctx, PostDatabaseService())
        verify { ctx.status(HttpCode.OK) }
    }

    @Test
    fun `POST to create post with empty body gives NOT_FOUND`() {
        every { ctx.bodyAsClass<PostRequest>() } returns PostRequest("")
        PostHandler.addPost(ctx, PostDatabaseService())
        verify { ctx.status(HttpCode.NOT_FOUND) }
    }

    @Test
    fun `GET post by id returns post with valid id`() {
        every { ctx.bodyAsClass<PostRequest>() } returns PostRequest("this is some content")
        every { ctx.pathParam("postId") } returns "1"
        val postDatabaseService = PostDatabaseService()

        PostHandler.getPostById(ctx, postDatabaseService)
        verify { ctx.json(Post(1, "this is some content")) }
    }

    @Test
    fun `GET post by id with bad id returns empty string`() {
        every { ctx.bodyAsClass<PostRequest>() } returns PostRequest("this is some content")
        every { ctx.pathParam("postId") } returns "2"
        val postDatabaseService = PostDatabaseService()

        PostHandler.getPostById(ctx, postDatabaseService)
        verify { ctx.json("") }
    }
}