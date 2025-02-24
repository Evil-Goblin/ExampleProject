package playground.kotlinmultimodule.domain.account

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import playground.kotlinmultimodule.domain.account.dto.AccountInfo
import playground.kotlinmultimodule.domain.account.dto.AccountSpec

@Transactional
@Component
class AccountService(
    private val accountWriter: AccountWriter
) {
    fun createAccount(accountSpec: AccountSpec): AccountInfo {
        val account = Account(
            email = accountSpec.email,
            name = accountSpec.name,
            password = accountSpec.password
        )

        val savedAccount = accountWriter.save(account)

        return AccountInfo(
            id = savedAccount.id,
            email = savedAccount.email,
            name = savedAccount.name
        )
    }
}
