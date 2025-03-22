plugins {
    alias(libs.plugins.runner.android.library)
    alias(libs.plugins.runner.android.room)
}

android {
    namespace = "com.android.core.database"
}

dependencies {
    implementation(libs.org.mongodb.bson)
    implementation(projects.core.domain)
}