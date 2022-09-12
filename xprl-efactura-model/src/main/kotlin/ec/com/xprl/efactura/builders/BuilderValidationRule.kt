package ec.com.xprl.efactura.builders

/**
 * A validation rule that may be performed for some [Builder].
 */
class BuilderValidationRule<T: Builder<*>>(
    val propertyGetter: (T) -> Any?,
    val validationErrorMessage: String,
    val predicate: (Any?) -> Boolean,
)

/**
 * Create a [Builder] validation rule to check that a given builder property is not null.
 */
internal fun <T: Builder<*>> requires(
    propertyName: String,
    propertyGetter: (T) -> Any?
) = BuilderValidationRule(
    propertyGetter,
    "$propertyName is null"
) {
    it != null
}

/**
 * Create a [Builder] validation rule to check that a builder property is
 * not null and that if the property is a collection it is not empty.
 */
internal fun <T: Builder<*>> requiresNotEmpty(
    propertyName: String,
    propertyGetter: (T) -> Any?
) = BuilderValidationRule(
    propertyGetter,
    "$propertyName is empty"
) {
    it != null &&
    (it as? Collection<*>)?.isNotEmpty() ?: (it as? Map<*, *>)?.isNotEmpty() ?: true
}