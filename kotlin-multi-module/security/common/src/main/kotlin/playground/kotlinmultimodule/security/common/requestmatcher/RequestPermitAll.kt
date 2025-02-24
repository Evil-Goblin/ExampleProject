package playground.kotlinmultimodule.security.common.requestmatcher

import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer

internal class RequestPermitAll(
    httpMethod: HttpMethod?,
    patterns: List<String>
): RequestMatcher(httpMethod, patterns) {
    override fun build(registry: AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizedUrl): AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry {
        return registry.permitAll()
    }
}
