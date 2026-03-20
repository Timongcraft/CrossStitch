plugins {
    id("maven-publish")
    alias(libs.plugins.fabric.loom)
}

group = "com.velocitypowered"
version = libs.versions.project.get()

fabricApi {
    configureTests {
        createSourceSet = true
        modId = "crossstitch-test"
        enableClientGameTests = false
        eula = true
    }
}

dependencies {
    minecraft(libs.minecraft)
    implementation(libs.fabric.loader)

    "gametestImplementation"(libs.fabric.api)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(libs.versions.java.get()))
    }
}

tasks.named<Jar>("jar") {
    from("LICENSE")
}

tasks.named<ProcessResources>("processResources") {
    filteringCharset = "UTF-8"

    val props = mapOf(
        "version" to libs.versions.project.get(),
        "java" to libs.versions.java.get(),
        "minecraft" to libs.versions.minecraft.get(), //todo: re-add placeholder
        "fabric_loader" to libs.versions.fabric.loader.get()
    )

    props.forEach { (key, value) ->
        inputs.property(key, value)
    }

    filesMatching("fabric.mod.json") {
        expand(props)
    }
}