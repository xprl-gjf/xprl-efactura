package ec.com.xprl.efactura

internal const val DOCUMENT_ID_MAX_LENGTH = 15 + 2   // XXX-XXX-XXXXXXXXX

/**
 * Immutable representation of a 'document id' value,
 * e.g. for [DocModificado.id]
 */
class DocumentId private constructor (val value: String) {

    override fun toString() = value

    companion object {
        /**
         * Maximum length of a [DocumentId] value.
         */
        val MAX_LENGTH: Int
            @JvmStatic get() = DOCUMENT_ID_MAX_LENGTH

        /**
         * Factory function to create a [DocumentId] value from an arbitrary string.
         * @exception IllegalArgumentException the string is not a valid [DocumentId] value.
         */
        fun from(value: CharSequence) = DocumentId(validate(value))

        private fun validate(value: CharSequence) = validateCharSequence<DocumentId>(
            value,
            "numeric with hyphens",
            DOCUMENT_ID_MAX_LENGTH,
            allowBlank = false
        ) {
            it.isValidNumeric(allowExtendedChars = true)
        }.toString()
    }
}