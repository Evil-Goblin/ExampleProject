import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.allopen.gradle.AllOpenExtension

class JpaConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.plugins.apply("org.jetbrains.kotlin.plugin.allopen")
        target.extensions.configure(AllOpenExtension::class.java) {
            annotation("jakarta.persistence.Entity")
            annotation("jakarta.persistence.Embeddable")
            annotation("jakarta.persistence.MappedSuperclass")
        }
    }
}
