plugins {
    alias(libs.plugins.runner.android.library)
    alias(libs.plugins.runner.jvm.ktor)
}

android {
    namespace = "com.android.run.network"
}

dependencies {

    implementation(libs.androidx.core.ktx)

    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(projects.core.domain)
    implementation(projects.core.data)
}