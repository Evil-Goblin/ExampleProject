package playground.kotlinmultimodule.domain.post

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import playground.kotlinmultimodule.domain.account.AccountDomainConfig

@Import(AccountDomainConfig::class)
@ConfigurationPropertiesScan
@SpringBootApplication
class PostTestApplication

fun main(args: Array<String>) {
    runApplication<PostTestApplication>(*args)
}
