plugins {
  `kotlin-dsl`
}

group ="com.android.runner.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
dependencies{
        compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)

}
gradlePlugin{
    plugins{
        register("androidApplication"){
            id = "runner.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose"){
            id = "runner.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary"){
            id = "runner.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose"){
            id = "runner.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidFeatureUi"){
            id = "runner.android.feature.ui"
            implementationClass = "AndroidFeatureUiConventionPlugin"
        }
        register("androidRoom"){
            id = "runner.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("jvmLibrary"){
            id = "runner.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("jvmKtor"){
            id = "runner.jvm.ktor"
            implementationClass = "JvmKtorConventionPlugin"
        }
    }
}