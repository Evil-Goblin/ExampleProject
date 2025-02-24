package playground.kotlinmultimodule.security.common.requestmatcher

import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer

abstract class RequestMatcher(
    private val httpMethod: HttpMethod? = null,
    private val patterns: List<String> = emptyList()
) {
    open fun build(registry: AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry): AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry {
        return build(registry.requestMatchers(httpMethod, *patterns.toTypedArray()))
    }

    abstract fun build(registry: AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizedUrl): AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry

    companion object {
        fun withPermitAll(
            httpMethod: HttpMethod? = null,
            patterns: List<String>
        ): RequestMatcher {
            return RequestPermitAll(
                httpMethod = httpMethod,
                patterns = patterns
            )
        }

        fun withPermitAll(patterns: List<String>): RequestMatcher {
            return withPermitAll(null, patterns)
        }

        fun withAnonymous(
            httpMethod: HttpMethod? = null,
            patterns: List<String>
        ): RequestMatcher {
            return RequestAnonymous(
                httpMethod = httpMethod,
                patterns = patterns
            )
        }

        fun withAnonymous(patterns: List<String>): RequestMatcher {
            return RequestAnonymous(null, patterns)
        }
    }
}
