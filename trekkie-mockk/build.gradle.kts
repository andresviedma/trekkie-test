dependencies {
    api(projects.trekkieTestCore)
    api(libs.kotlinx.coroutines.core)

    api(libs.mockk)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
