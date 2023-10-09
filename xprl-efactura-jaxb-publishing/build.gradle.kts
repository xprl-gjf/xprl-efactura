
plugins {
    id("xprl-efactura.kotlin-jvm-conventions")
}

repositories {
    mavenCentral()
}

dependencies {
    api(project(":xprl-efactura-model"))
    implementation("uk.co.xprl.efactura:sri-efactura-core:0.1.2")
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
