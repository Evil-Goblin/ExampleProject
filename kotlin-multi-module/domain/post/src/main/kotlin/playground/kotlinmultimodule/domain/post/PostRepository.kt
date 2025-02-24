package playground.kotlinmultimodule.domain.post

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface PostRepository: JpaRepository<Post, Long> {
    @EntityGraph(attributePaths = ["author"])
    fun findPostWithAuthorById(id: Long): Post?

    @Query("select p from Post p join fetch p.author")
    fun findAllWithAuthor(): List<Post>
}
