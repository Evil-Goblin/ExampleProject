package playground.kotlinmultimodule.domain.post

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Component
class PostLoader(
    private val postRepository: PostRepository
) {
    fun loadById(id: Long): Post {
        return postRepository.findByIdOrNull(id) ?: throw RuntimeException("Post[$id] not Found")
    }
}
