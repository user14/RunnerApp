import com.android.build.api.dsl.LibraryExtension
import com.android.convention.ExtensionType
import com.android.convention.addUiLayerDependencies
import com.android.convention.configureAndroidCompose
import com.android.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin

class AndroidFeatureUiConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("runner.android.library.compose")
            }
            dependencies{
                addUiLayerDependencies(target)
            }
        }
    }
}