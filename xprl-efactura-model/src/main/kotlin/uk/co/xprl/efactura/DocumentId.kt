package uk.co.xprl.efactura

internal const val DOCUMENT_ID_MAX_LENGTH = 15 + 2   // XXX-XXX-XXXXXXXXX

/**
 * Immutable representation of a 'document id' value,
 * e.g. for [DocModificado.id]
 */
class DocumentId private constructor (val value: String) {

    override fun toString() = value

    /**
     * Value equality comparison
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as DocumentId
        return other.value == this.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

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