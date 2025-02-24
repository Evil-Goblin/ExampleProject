package playground.kotlinmultimodule.security.common

import org.springframework.security.config.annotation.web.builders.HttpSecurity

interface SecurityApplier {
    fun apply(httpSecurity: HttpSecurity)
}
