plugins {
    alias(libs.plugins.kotlin.jvm)
}

kotlin {
    jvmToolchain(1_8)
}

dependencies {
    api(projects.trekkieTestCore)
    api(libs.kotlinx.coroutines.core)

    api(platform(libs.kotest.bom))
    api(libs.kotest.assertions)
    api(libs.kotest.junit5)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
