
plugins {
    id("xprl-efactura.kotlin-jvm-conventions")
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.github.com/xprl-gjf/sri-efactura-core")
        credentials {
            username = (project.findProperty("gpr.user") ?: System.getenv("USERNAME")).toString()
            password = (project.findProperty("gpr.key") ?: System.getenv("TOKEN")).toString()
        }
    }
    mavenLocal {
        content {
            includeGroup("ec.com.xprl.efactura")
        }
    }
}

dependencies {
    api(project(":xprl-efactura-model"))
    implementation("ec.com.xprl.efactura:sri-efactura-core:0.1.0")
    testRuntimeOnly("com.sun.xml.bind:jaxb-impl:4.0.1", ) {
        because("Runtime implementation of jaxb-api")
    }
    testRuntimeOnly("org.hibernate.validator:hibernate-validator:7.0.5.Final") {
        because("Runtime provider of jakarta.validation.Validator.")
    }
    testRuntimeOnly("org.glassfish:jakarta.el:4.0.2") {
        because("Runtime Expression Language implementation needed by jakarta.validation.Validator.")
    }
}
