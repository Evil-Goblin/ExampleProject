package playground.kotlinmultimodule.domain.post

import org.springframework.stereotype.Component

@Component
class PostWriter(
    private val postRepository: PostRepository
) {
}
