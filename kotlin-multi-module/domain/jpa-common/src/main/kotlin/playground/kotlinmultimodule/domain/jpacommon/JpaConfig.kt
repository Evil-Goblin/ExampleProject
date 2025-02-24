package playground.kotlinmultimodule.domain.jpacommon

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.transaction.annotation.EnableTransactionManagement

@EnableTransactionManagement
@EnableJpaAuditing
@Configuration
internal class JpaConfig
