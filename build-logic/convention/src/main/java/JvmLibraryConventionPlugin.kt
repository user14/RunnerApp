import com.android.build.api.dsl.LibraryExtension
import com.android.convention.ExtensionType
import com.android.convention.configureKotlinAndroid
import com.android.convention.configureKotlinJvm
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class JvmLibraryConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.apply("org.jetbrains.kotlin.jvm")
            configureKotlinJvm()
        }

    }
}