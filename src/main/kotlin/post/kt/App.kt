package post.kt

import post.kt.server.PostServer

fun main() {
    PostServer()
        .create()
        .start()
}



