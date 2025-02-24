package playground.kotlinmultimodule.domain.account

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
class AccountLoaderTest(
    private val accountRepository: AccountRepository,
    private val accountLoader: AccountLoader
) : AccountContextTest() {

    @Test
    fun `load account by id if exists`() {
        // given
        val account = Account(
            email = "email@email.com",
            name = "name",
            password = "password"
        )
        val savedAccount = accountRepository.save(account)

        // when
        val loadedAccount = accountLoader.loadById(savedAccount.id)

        // then
        assertThat(loadedAccount.id).isEqualTo(savedAccount.id)
        assertThat(loadedAccount.email).isEqualTo(savedAccount.email)
        assertThat(loadedAccount.name).isEqualTo(savedAccount.name)
        assertThat(loadedAccount.password).isEqualTo(savedAccount.password)
    }

    @Test
    fun `loadById raise not found exception if not exists`() {
        // given
        val account = Account(
            email = "email@email.com",
            name = "name",
            password = "password"
        )
        val savedAccount = accountRepository.save(account)

        // expect
        assertThatThrownBy { accountLoader.loadById(savedAccount.id + 1) }
            .isInstanceOf(RuntimeException::class.java)
            .hasMessage("Account[${savedAccount.id + 1}] not Found")
    }

    @Test
    fun `load account by email if exists`() {
        // given
        val account = Account(
            email = "email@email.com",
            name = "name",
            password = "password"
        )
        val savedAccount = accountRepository.save(account)

        // when
        val loadedAccount = accountLoader.loadByEmail("email@email.com")

        // then
        assertThat(loadedAccount.id).isEqualTo(savedAccount.id)
        assertThat(loadedAccount.email).isEqualTo(savedAccount.email)
        assertThat(loadedAccount.name).isEqualTo(savedAccount.name)
        assertThat(loadedAccount.password).isEqualTo(savedAccount.password)
    }

    @Test
    fun `loadByEmail raise not found exception if not exists`() {
        // given
        val account = Account(
            email = "email@email.com",
            name = "name",
            password = "password"
        )
        val savedAccount = accountRepository.save(account)

        // expect
        assertThatThrownBy { accountLoader.loadByEmail("invalid@email.com") }
            .isInstanceOf(RuntimeException::class.java)
    }
}
