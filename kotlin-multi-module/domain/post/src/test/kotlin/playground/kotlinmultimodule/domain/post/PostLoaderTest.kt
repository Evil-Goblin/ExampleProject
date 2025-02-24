package playground.kotlinmultimodule.domain.post

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.transaction.annotation.Transactional
import playground.kotlinmultimodule.domain.account.Account
import playground.kotlinmultimodule.domain.account.AccountWriter

@Transactional
class PostLoaderTest(
    private val postLoader: PostLoader,
    private val postRepository: PostRepository,
    private val accountWriter: AccountWriter
): PostContextTest() {

    @Test
    fun `load post by id if exists`() {
        // given
        val account = accountWriter.save(Account(email = "email", name = "name", password = "password"))
        val post = Post(author = account, title = "title", content = "content")
        val savedPost = postRepository.save(post)

        // when
        val loadedPost = postLoader.loadById(savedPost.id)

        // then
        assertEquals(loadedPost.id, savedPost.id)
        assertEquals(loadedPost.author.id, savedPost.author.id)
        assertEquals(loadedPost.title, savedPost.title)
        assertEquals(loadedPost.content, savedPost.content)
    }
}
