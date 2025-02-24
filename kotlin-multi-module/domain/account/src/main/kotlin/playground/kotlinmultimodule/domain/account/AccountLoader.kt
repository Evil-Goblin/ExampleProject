package playground.kotlinmultimodule.domain.account

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Component
class AccountLoader(
    private val accountRepository: AccountRepository
) {
    fun loadById(id: Long): Account {
        return accountRepository.findByIdOrNull(id) ?: throw RuntimeException("Account[$id] not Found")
    }

    fun loadByEmail(email: String): Account {
        return accountRepository.findByEmail(email) ?: throw RuntimeException("Account[$email] not Found")
    }
}
