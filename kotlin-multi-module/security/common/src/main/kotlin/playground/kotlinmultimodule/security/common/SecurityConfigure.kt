package playground.kotlinmultimodule.security.common

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfigure(
    private val securityAppliers: List<SecurityApplier>
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        securityAppliers.forEach { applier -> applier.apply(http) }

        return http.build()
    }
}
