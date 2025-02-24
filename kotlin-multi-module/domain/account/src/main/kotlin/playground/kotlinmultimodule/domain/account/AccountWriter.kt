package playground.kotlinmultimodule.domain.account

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional
@Component
class AccountWriter(
    private val accountRepository: AccountRepository
) {
    fun save(account: Account): Account {
        return accountRepository.save(account)
    }
}
