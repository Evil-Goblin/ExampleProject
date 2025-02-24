package playground.kotlinmultimodule

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinMultiModuleApplication

fun main(args: Array<String>) {
    runApplication<KotlinMultiModuleApplication>(*args)
}
