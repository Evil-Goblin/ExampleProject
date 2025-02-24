package playground.kotlinmultimodule.domain.account

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface AccountRepository: JpaRepository<Account, Long> {
    fun findByEmail(email: String): Account?
}
