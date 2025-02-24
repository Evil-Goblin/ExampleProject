package playground.kotlinmultimodule.domain.account

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class AccountTestApplication

fun main(args: Array<String>) {
    runApplication<AccountTestApplication>(*args)
}
