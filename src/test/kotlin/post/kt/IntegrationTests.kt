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
        app.create().start(1234)
        val r1: HttpResponse<String> = Unirest.get("http://localhost:1234/posts").asString()
        assertThat(r1.status).isEqualTo(200)
        assertThat(r1.body).isEqualTo("[]")

        val r2: HttpResponse<String> = Unirest.post("http://localhost:1234/addPost")
            .header("accept", "application/json")
            .body("{ \"content\": \"this is some content\"}")
            .asString()
        assertThat(r2.status).isEqualTo(200)

        val r3: HttpResponse<String> = Unirest.get("http://localhost:1234/posts").asString()
        assertThat(r3.status).isEqualTo(200)
        assertThat(r3.body).isEqualTo("[{\"id\":1,\"content\":\"this is some content\"}]")
    }
}