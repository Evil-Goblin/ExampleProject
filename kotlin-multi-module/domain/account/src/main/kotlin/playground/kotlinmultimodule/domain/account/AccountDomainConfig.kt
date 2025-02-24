package playground.kotlinmultimodule.domain.account

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@ComponentScan//("playground.kotlinmultimodule.domain.account")
@Configuration
@EntityScan//("playground.kotlinmultimodule.domain.account")
@EnableJpaRepositories//("playground.kotlinmultimodule.domain.account")
class AccountDomainConfig
