package post.kt

import kong.unirest.HttpResponse
import kong.unirest.Unirest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import post.kt.server.PostServer

class IntegrationTests {

    private val app = PostServer()


    @Test
    fun `GET to fetch posts returns a list of posts`() {
        val port = 1234
        Unirest.config().defaultBaseUrl("http://localhost:$port")
        app.create().start(port)

        val getAllPostsResponseEmpty: HttpResponse<String> = Unirest.get("/posts").asString()
        assertThat(getAllPostsResponseEmpty.status).isEqualTo(200)
        assertThat(getAllPostsResponseEmpty.body).isEqualTo("[]")

        val addPostResponse: HttpResponse<String> = Unirest.post("/addPost")
            .header("accept", "application/json")
            .body("{ \"content\": \"this is some content\"}")
            .asString()
        assertThat(addPostResponse.status).isEqualTo(200)

        val getAllPostsResponseNotEmpty: HttpResponse<String> = Unirest.get("/posts").asString()
        assertThat(getAllPostsResponseNotEmpty.status).isEqualTo(200)
        assertThat(getAllPostsResponseNotEmpty.body).isEqualTo("[{\"id\":1,\"content\":\"this is some content\"}]")
    }

    @Test
    fun `UPDATE changes post content`() {
        val port = 1235
        Unirest.config().defaultBaseUrl("http://localhost:$port")
        app.create().start(port)
        val createPostResponse: HttpResponse<String> = Unirest.post("/addPost")
            .header("accept", "application/json")
            .body("{ \"content\": \"this is some content\"}")
            .asString()
        assertThat(createPostResponse.status).isEqualTo(200)

        val getPostByIdResponseBefore: HttpResponse<String> = Unirest.get("/posts/1").asString()
        assertThat(getPostByIdResponseBefore.status).isEqualTo(200)
        assertThat(getPostByIdResponseBefore.body).isEqualTo("{\"id\":1,\"content\":\"this is some content\"}")

        val updatePostResponse: HttpResponse<String> = Unirest.patch("/updatePost/1")
            .header("accept", "application/json")
            .body("{ \"content\": \"updated content\"}")
            .asString()
        assertThat(updatePostResponse.status).isEqualTo(200)

        val getPostByIdResponseAfter: HttpResponse<String> = Unirest.get("/posts/1").asString()
        assertThat(getPostByIdResponseAfter.status).isEqualTo(200)
        assertThat(getPostByIdResponseAfter.body).isEqualTo("{\"id\":1,\"content\":\"updated content\"}")
    }

    @Test
    fun `DELETE removes post`() {
        val port = 1236
        Unirest.config().defaultBaseUrl("http://localhost:$port")
        app.create().start(port)

        val addPostResponse: HttpResponse<String> = Unirest.post("/addPost")
            .header("accept", "application/json")
            .body("{ \"content\": \"this is some content\"}")
            .asString()
        assertThat(addPostResponse.status).isEqualTo(200)

        val getPostResponseBefore: HttpResponse<String> = Unirest.get("/posts/1").asString()
        assertThat(getPostResponseBefore.status).isEqualTo(200)
        assertThat(getPostResponseBefore.body).isEqualTo("{\"id\":1,\"content\":\"this is some content\"}")

        val deletePostResponse: HttpResponse<String> = Unirest.delete("/deletePost/1").asString()
        assertThat(deletePostResponse.status).isEqualTo(200)

        val getPostResponseAfter: HttpResponse<String> = Unirest.get("/posts/1").asString()
        assertThat(getPostResponseAfter.status).isEqualTo(404)
    }
}