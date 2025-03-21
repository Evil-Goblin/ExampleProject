package playground.kotlinmultimodule.domain.post

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor

@ActiveProfiles("test")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest//(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PostContextTest
