plugins {
    alias(libs.plugins.runner.android.feature.ui)

}

android {
    namespace = "com.android.auth.presentation"
}

dependencies {
    implementation(projects.auth.domain)
    implementation(projects.core.domain)

}