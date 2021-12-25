plugins {
    id("java")
    id("fabric-loom").version("0.10-SNAPSHOT") apply(false)
}

group = "com.dfsek"
version = "0.1.0"

repositories {
    mavenCentral()
}


val fabricLoader = "0.12.12"
val minecraft = "1.18.1"
val yarn = "$minecraft+build.7"


allprojects {
    apply(plugin = "java")
    apply(plugin = "fabric-loom")

    group = "com.dfsek"
    version = "0.1.0"

    dependencies {
        "minecraft"("com.mojang:minecraft:$minecraft")
        "mappings"("net.fabricmc:yarn:$yarn:v2")
        "modImplementation"("net.fabricmc:fabric-loader:$fabricLoader")

        testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    }

    configure<net.fabricmc.loom.api.LoomGradleExtensionAPI> {
        shareRemapCaches.set(true)
    }


    tasks.getByName<Test>("test") {
        useJUnitPlatform()
    }

    tasks.withType<JavaCompile>().configureEach {
        options.release.set(17)
    }
}

dependencies {
    afterEvaluate {
        subprojects.forEach {
            println("Including ${it.name}")
            "include"(project(path = ":${it.name}"))
            "implementation"(project(path = ":${it.name}", configuration = "namedElements"))
        }
    }
}

tasks.named("assemble").configure {
    dependsOn("remapJar")
}