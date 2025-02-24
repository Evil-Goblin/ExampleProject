package playground.kotlinmultimodule

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import playground.kotlinmultimodule.domain.account.AccountService
import playground.kotlinmultimodule.domain.account.dto.AccountSpec
import playground.kotlinmultimodule.dto.RequestCreateUser
import playground.kotlinmultimodule.dto.ResponseCreatedUser

@RestController
class CoreController(
    private val accountService: AccountService
) {

    @PostMapping("/users")
    fun createUser(@RequestBody requestCreateUser: RequestCreateUser): ResponseEntity<ResponseCreatedUser> {
        val createAccount = accountService.createAccount(
            AccountSpec(
                email = requestCreateUser.email,
                name = requestCreateUser.name,
                password = requestCreateUser.password
            )
        )

        return ResponseEntity.ok(
            ResponseCreatedUser(
                id = createAccount.id,
                email = createAccount.email,
                name = createAccount.name
            )
        )
    }
}
