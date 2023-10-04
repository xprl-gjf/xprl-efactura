package uk.co.xprl.efactura.builders

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
fun <T: Builder<*>> requires(
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
fun <T: Builder<*>> requiresNotEmpty(
    propertyName: String,
    propertyGetter: (T) -> Any?
) = BuilderValidationRule(
    propertyGetter,
    "$propertyName is empty"
) {
    it != null &&
    (it as? Collection<*>)?.isNotEmpty() ?: (it as? Map<*, *>)?.isNotEmpty() ?: true
}


/**
 * Create a [Builder] validation rule to check that a builder property (that is a collection)
 * contains fewer than the specified number of items.
 */
fun <T: Builder<*>> requiresNotMoreThan(
    propertyName: String,
    maxItems: Int,
    propertyGetter: (T) -> Any?
) = BuilderValidationRule(
    propertyGetter,
    "$propertyName contains more than $maxItems items"
) {
    val count = (it as? Collection<*>)?.size
        ?: (it as? Map<*, *>)?.size
        ?: 0
    (count <= maxItems)
}

/**
 * Create a [Builder] validation rule to conditionally check that a given builder property is not null.
 */
fun <T: Builder<*>> requiresIf(
    predicate: (T) -> Boolean,
    propertyName: String,
    propertyGetter: (T) -> Any?
) = BuilderValidationRule<T>(
    { Pair(predicate(it), propertyGetter(it)) },
    "$propertyName is null"
) {
    (it as? Pair<*, *>)?.let { (predicate, value) ->
        !(predicate as? Boolean ?: true) || value != null
    } ?: false
}

/**
 * Create a [Builder] validation rule to conditionally check that a given builder property
 * is not null and that if the property is a collection it is not empty.
 */
fun <T: Builder<*>> requiresNotEmptyIf(
    predicate: (T) -> Boolean,
    propertyName: String,
    propertyGetter: (T) -> Any?
) = BuilderValidationRule<T>(
    { Pair(predicate(it), propertyGetter(it)) },
    "$propertyName is empty"
) {
    (it as? Pair<*, *>)?.let { (predicate, value) ->
        !(predicate as? Boolean ?: true) ||
            (value != null &&
                (value as? Collection<*>)?.isNotEmpty() ?: (value as? Map<*, *>)?.isNotEmpty() ?: true
            )
    } ?: false
}
