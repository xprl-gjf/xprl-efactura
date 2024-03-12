package uk.co.xprl.efactura.builders.retencion

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import uk.co.xprl.efactura.PaisCodeValue
import uk.co.xprl.efactura.TipoPagoRetencion
import uk.co.xprl.efactura.TipoRegimenFiscalDelExterior
import uk.co.xprl.efactura.builders.InvalidBuilderOperation
import kotlin.test.assertEquals

internal class PagoATSBuilderTest {

    /**
     * Verify that a [PagoRetencionATSBuilder.Residente]
     * successfully builds a [PagoRetencionATS] with tipoPago value of RESIDENTE.
     */
    @Test
    fun PagoRetencionATSBuilder_ResidenteCanBuild() {
        val pago = assertDoesNotThrow {
            PagoRetencionATSBuilder.Residente().build()
        }

        assertEquals(TipoPagoRetencion.RESIDENTE, pago.tipoPago)
    }

    /**
     * Verify that a [PagoRetencionATSBuilder.NoResidente]
     * successfully builds a [PagoRetencionATS] with tipoPago value of NO_RESIDENTE.
     */
    @Test
    fun PagoRetencionATSBuilder_NoResidenteCanBuild() {
        val pago = assertDoesNotThrow {
            PagoRetencionATSBuilder.NoResidente()
                .setTipoRegimen(TipoRegimenFiscalDelExterior.REGIMEN_GENERAL)
                .setPais(PaisCodeValue.from(123))
                .setAplicConvDobTrib(true)
                .build()
        }
        assertEquals(TipoPagoRetencion.NO_RESIDENTE, pago.tipoPago)
    }

    /**
     * Verify that an incomplete [PagoRetencionATSBuilder.NoResidente]
     * throws a validation exception.
     */
    @Test
    fun PagoRetencionATSBuilder_NoResidente_IncompleteThrowsException() {
        assertThrows<InvalidBuilderOperation> {
            PagoRetencionATSBuilder.NoResidente()
                .build()
        }
    }

    /**
     * Verify that an inconsistent [PagoRetencionATSBuilder.NoResidente]
     * throws a validation exception.
     */
    @Test
    fun PagoRetencionATSBuilder_NoResidente_InconsistentThrowsException() {
        assertThrows<InvalidBuilderOperation> {
            PagoRetencionATSBuilder.NoResidente()
                .setTipoRegimen(TipoRegimenFiscalDelExterior.REGIMEN_GENERAL)
                .setPais(PaisCodeValue.from(123))
                .setAplicConvDobTrib(false)
                // missing pagExtSujRetNorLeg
                .build()
        }
    }
}