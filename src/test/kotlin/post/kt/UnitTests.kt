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
    fun `POST to create post with empty body gives BAD_REQUEST`() {
        every { ctx.bodyAsClass<PostRequest>() } returns PostRequest("")
        PostHandler.addPost(ctx, PostDatabaseService())
        verify { ctx.status(HttpCode.BAD_REQUEST) }
    }

    @Test
    fun `GET post by id returns post with valid id`() {
        every { ctx.bodyAsClass<PostRequest>() } returns PostRequest("this is some content")
        every { ctx.pathParam("postId") } returns "1"

        val postDatabaseService = PostDatabaseService()

        PostHandler.addPost(ctx, postDatabaseService)
        verify { ctx.status(HttpCode.OK) }

        PostHandler.getPostById(ctx, postDatabaseService)
        verify { ctx.status(HttpCode.OK) }
        verify { ctx.json(Post(1, "this is some content")) }
    }

    @Test
    fun `GET post by id with bad id returns empty string`() {
        every { ctx.bodyAsClass<PostRequest>() } returns PostRequest("this is some content")
        every { ctx.pathParam("postId") } returns "2"
        val postDatabaseService = PostDatabaseService()

        PostHandler.getPostById(ctx, postDatabaseService)
        verify { ctx.status(HttpCode.NOT_FOUND) }
        verify { ctx.json("") }
    }

    @Test
    fun `PATCH post by id updates post content`() {
        val postDatabaseService = PostDatabaseService()
        `setup database`(ctx, postDatabaseService)

        every { ctx.pathParam("postId") } returns "1"
        every { ctx.bodyAsClass<PostRequest>() } returns PostRequest("updated content")
        PostHandler.updatePost(ctx, postDatabaseService)
        verify { ctx.status(HttpCode.OK) }

        PostHandler.getPostById(ctx, postDatabaseService)
        verify { ctx.json(Post(1, "updated content")) }
    }

    @Test
    fun `DELETE post by id removes post`() {
        val postDatabaseService = PostDatabaseService()
        `setup database`(ctx, postDatabaseService)

        every { ctx.pathParam("postId") } returns "1"
        PostHandler.getPostById(ctx, postDatabaseService)
        verify { ctx.status(HttpCode.OK) }

        PostHandler.deletePost(ctx, postDatabaseService)
        verify { ctx.status(HttpCode.OK) }

        PostHandler.getPostById(ctx, postDatabaseService)
        verify { ctx.status(HttpCode.NOT_FOUND) }

        every { ctx.pathParam("postId") } returns "2"
        PostHandler.getPostById(ctx, postDatabaseService)
        verify { ctx.status(HttpCode.OK) }
    }

    private fun `setup database`(ctx: Context, postDatabaseService: PostDatabaseService, size: Int = 10) {
        (0..size).forEach {
            every { ctx.bodyAsClass<PostRequest>() } returns PostRequest("this is content number $it")
            PostHandler.addPost(ctx, postDatabaseService)
            verify { ctx.status(HttpCode.OK) }
        }
    }
}