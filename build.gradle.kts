plugins {
    // read extended project info from "projectInfo.conf"
    id("uk.co.xprl.project-info")
    id("uk.co.xprl.maven-artifact")
    id("xprl-efactura.kotlin-jvm-conventions")
    signing
}

evaluationDependsOnChildren()

val version: String by project
val jarProjects = arrayOf(
    ":xprl-efactura-model",
    ":xprl-efactura-jaxb-publishing",
)

val projectVersion = version
tasks.jar {
    destinationDirectory.set(layout.buildDirectory.dir("libs"))
    archiveBaseName.set("xprl-efactura")
    archiveVersion.set(projectVersion)
    from(
        jarProjects.map { projectName ->
            project(projectName).sourceSets.main.get().output
        }
    )
}

repositories {
    mavenCentral()
}

// dependencies to be listed in the published maven artifact
dependencies {
    api("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
    implementation("uk.co.xprl.efactura:sri-efactura-core:0.1.2")
    runtimeOnly("com.sun.xml.bind:jaxb-impl:4.0.4", ) {
        because("Runtime implementation of jaxb-api")
    }
    runtimeOnly("org.hibernate.validator:hibernate-validator:8.0.1.Final") {
        because("Runtime implementation of jakarta.validation.Validator used by JaxbComprobantePublisher.")
    }
    runtimeOnly("org.glassfish:jakarta.el:5.0.0-M1") {
        because("Expression Language implementation needed for jakarta.validation.Validator.")
    }
    runtimeOnly("org.bouncycastle:bcprov-jdk18on:1.77") {
        because("Runtime support for BouncyCastleProvider; a security provider with support for PKCS12, for xades-firma")
    }
    runtimeOnly("commons-logging:commons-logging:1.3.0") {
        because("For es.mityc.javasign classes bundled from xades-firma")
    }
}

mavenArtifact {
    artifactId = "xprl-efactura"
    artifactName = "Xprl efactura utils"
    artifactDescription = "Utility classes for constructing and publishing comprobantes electrónicos for SRI in Ecuador."
}

publishing {
    repositories {
        /*
        maven {
            // change URLs to point to your repos, e.g. http://my.org/repo
            val releasesRepoUrl = uri(layout.buildDirectory.dir("repos/releases"))
            val snapshotsRepoUrl = uri(layout.buildDirectory.dir("repos/snapshots"))
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
        }
         */
        maven {
            name = "OSSRH"      /* MavenCentral */
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = project.findProperty("ossrh.Username") as String? ?: System.getenv("MAVEN_USERNAME")
                password = project.findProperty("ossrh.Password") as String? ?: System.getenv("MAVEN_PASSWORD")
            }
        }
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/xprl-gjf/xprl-efactura")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }
}

signing {
    /*
    setRequired({
        (project.extra["isReleaseVersion"] as Boolean) && gradle.taskGraph.hasTask("publish")
    })
     */
    //val signingKey: String? by project
    //val signingPassword: String? by project
    //useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications)
}
