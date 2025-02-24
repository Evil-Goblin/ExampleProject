package playground.kotlinmultimodule.security.common.requestmatcher

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import playground.kotlinmultimodule.security.common.SecurityApplier

class RequestMatchers(
    private val requestMatcherList: List<RequestMatcher> = mutableListOf()
): SecurityApplier {
    override fun apply(httpSecurity: HttpSecurity) {
        httpSecurity.authorizeHttpRequests { request ->
            requestMatcherList.forEach { matchers -> matchers.build(request) }
        }
    }

    fun addMatcher(matcher: RequestMatcher): RequestMatchers {
        if (requestMatcherList is MutableList) {
            requestMatcherList.add(matcher)
        }

        return this
    }
}
