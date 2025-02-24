package playground.kotlinmultimodule.domain.post

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@ComponentScan
@Configuration
@EntityScan//("playground.kotlinmultimodule.domain.post")
@EnableJpaRepositories//("playground.kotlinmultimodule.domain.post")
class PostDomainConfig
