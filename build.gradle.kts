plugins {
    id("maven-publish")
    alias(libs.plugins.fabric.loom)
}

group = "com.velocitypowered"
version = libs.versions.project.get()

dependencies {
    minecraft(libs.minecraft)
    mappings(loom.officialMojangMappings())
    modImplementation(libs.fabric.loader)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(rootProject.libs.versions.java.get()))
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
        "minecraft" to libs.versions.minecraft.get(),
        "fabric_loader" to libs.versions.fabric.loader.get()
    )

    props.forEach { (key, value) ->
        inputs.property(key, value)
    }

    filesMatching("fabric.mod.json") {
        expand(props)
    }
}