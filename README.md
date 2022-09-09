# xprl-efactura :money_with_wings:

Clases útiles por la creación y publicación por XML de comprobantes electrónicos por los
servicios SRI en Ecuador. Implementado en kotlin y empaquetado en una libraría Jar por Java 11+.

Con apoyo por las siguientes esquemas:

* Factura v1.0.0
* Factura v1.1.0
* NotaCrédito v1.0.0
* NotaCrédito v1.1.0
* NotaDébito v1.0.0
* LiquidaciónCompra v1.0.0
* LiquidaciónCompra v1.1.0
* GuíaRemisión v1.0.0
* GuíaRemisión v1.1.0
* ComprobanteRetención v1.0.0

**TODO:**

comprobantes electrónicos por esquemas v2.0.0 o v2.1.0

## Construir la libraría xprl-efactura :hammer_and_wrench:

**Prerequisites:**
- JDK >= Java 11
- `ec.com.xprl.efactura:sri-efactura-core` v1.0-SNAPSHOT (con dependencia transitivo `ec.com.xprl:krasa-jaxb-tools` v2.3-SNAPSHOT) - ver lo siguiente.

Se puede instalar [sri-efactura-core](https://github.com/xprl-gjf/sri-efactura-core) y
[krasa-jaxb-tools](https://github.com/xprl-gjf/krasa-jaxb-tools) con Maven, por:

```console
$ git clone https://github.com/xprl-gjf/krasa-jaxb-tools.git \
    && cd krasa-jaxb-tools
$ mvn clean install

$ git clone https://github.com/xprl-gjf/sri-efactura-core.git \
    && cd sri-efactura-core
$ ./gradlew clean publishToMavenLocal
```

**Steps:**
1) Clone el repositorio y compílelo usando el script contenedor `gradlew`:
```console
$ git clone https://github.com/xprl-gjf/xprl-efactura.git \
    && cd xprl-efactura
$ ./gradlew clean build
```

2) Si lo desea, publique la biblioteca en un repositorio local de Maven:
```console
$ ./gradlew publishToMavenLocal
```

## Para usar la libraría xprl-efactura :jigsaw:

Inclúyalo en su proyecto por Gradle en la manera siguiente:

```kotlin
// build.gradle.kts:

repositories {
    mavenLocal {
        // if using mavenLocal, it is good practice to restrict it to only specific libs/groups
        content {
            includeGroup("ec.com.xprl.efactura")
        }
    }
}

dependencies {
    implementation("ec.com.xprl.efactura:sri-efactura-core:0.1.0-SNAPSHOT")
    // ...
}
```

Ejemplo de uso:

```kotlin
import ec.com.xprl.efactura.*
import ec.com.xprl.efactura.builders.InvalidBuilderOperation
import ec.com.xprl.efactura.jaxb.ComprobanteFirmaException
import ec.com.xprl.efactura.jaxb.ComprobanteFirmador
import ec.com.xprl.efactura.jaxb.JaxbComprobantePublisher
import ec.com.xprl.efactura.jaxb.JaxbValidationException
import java.io.File

fun main(args: Array<String>) {
    val firmaPath = args[0]
    val firmaContrasena = args[1]
    val firmador = ComprobanteFirmador(
        File(firmaPath).readBytes(), firmaContrasena
    )
    
    val factura: Factura = createFactura()
    val published: PublishedComprobante<Factura> = publishComprobante(
        factura, SchemaVersion.FacturaV110, firmador
    )
    println(published.xml)
}

@Throws(IllegalArgumentException::class, NumberFormatException::class, InvalidBuilderOperation::class)
private fun createFactura(): Factura {
    val emisorBuilder = EmisorBuilder()
        .setRUC(IdentityValue.RUC.from("9999999999001"))
        .setRazonSocial(TextValue.from("ACME Widgets Cia"))
        //...
    
    return FacturaBuilder()
        .setSecuencial(SecuencialValue.from(1))
        .setFechaEmision(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date)
        .setEmisor(emisorBuilder)
        //...
        .build()
}

@Throws(JaxbValidationException::class, ComprobranteFirmaException::class)
private fun <T: ComprobanteElectronico> publishComprobante(
    comprobante: T,
    schemaVersion: SchemaVersion<T>,
    firmador: ComprobanteFirmador? = null,
    ambiente: Ambiente = Ambiente.PRUEBAS
): PublishedComprobante<T> {
    val publisher = JaxbComprobantePublisher<T>(ambiente, validationEnabled=true)
    val published = publisher.publish(comprobante, schemaVersion)
    return firmador?.let { it.firmarComprobante(published) } ?: published
}

```