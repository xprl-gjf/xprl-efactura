
plugins {
    id("xprl-efactura.kotlin-jvm-conventions")
}

repositories {
    // TODO: remove this if/when sri-efactura-core is published to MavenCentral.
    mavenLocal {
        content {
            includeGroup("ec.com.xprl.efactura")
        }
    }
}

dependencies {
    api(project(":xprl-efactura-model"))
    implementation("ec.com.xprl.efactura:sri-efactura-core:0.1.0-SNAPSHOT")
    testRuntimeOnly("com.sun.xml.bind:jaxb-impl:4.0.0", ) {
        because("Runtime implementation of jaxb-api")
    }
    testRuntimeOnly("org.hibernate.validator:hibernate-validator:7.0.5.Final") {
        because("Runtime provider of jakarta.validation.Validator.")
    }
    testRuntimeOnly("org.glassfish:jakarta.el:4.0.2") {
        because("Runtime Expression Language implementation needed by jakarta.validation.Validator.")
    }
}
