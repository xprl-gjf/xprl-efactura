package ec.com.xprl.efactura.builders

/**
 * Interface for a mutable builder for some class T.
 */
interface Builder<out T: Any> {
    /**
     * Boolean value to indicate whether the current state of the builder is valid.
     *
     * If the current state of the builder is not valid, then a call to [build]
     * will throw an [InvalidBuilderOperation] exception.
     */
    val isValid: Boolean

    /**
     * If the current state of the builder is invalid, the list of properties
     * that are missing or are invalid.
     *
     * Returns an empty list if the current state of the builder is valid.
     */
    val invalidProperties: List<String>

    /**
     * Create a value of type [T] from the current state of this mutable builder.
     *
     * @throws InvalidBuilderOperation the current state of the builder is invalid
     * for a value of type [T].
     * @return a newly created object of type [T].
     */
    fun build(): T
}

/**
 * Abstract base class for a Builder of some type [T].
 *
 * @constructor
 * @param clazz the Class of object built by this builder. (The reified Class for type [T].)
 * @param validationRules a collection of validation rules for determining
 *                        whether the current state of the builder is valid.
 */
abstract class AbstractBuilder<TBuilder: AbstractBuilder<TBuilder, T>, out T: Any>(
    private val clazz: Class<T>,
    vararg validationRules: BuilderValidationRule<TBuilder>
) : Builder<T> {
    private val _validationRules = validationRules

    override val isValid: Boolean
        get() = invalidProperties.isEmpty()

    override val invalidProperties: List<String>
        get() = validate()

    override fun build(): T {
        val errors = validate()
        if (errors.isNotEmpty()) {
            throw InvalidBuilderOperation(clazz.name ?: "<UNKNOWN>", errors)
        }

        return validatedBuild()
    }

    /**
     * Apply the validation rules for this builder to get the list of invalid
     * properties, if any.
     *
     * @return the invalid property names as a list of strings, or an empty list.
     */
    protected open fun validate() = _validationRules.map {
        it.predicate(it.propertyGetter(this as TBuilder)) to it.validationErrorMessage
    }.filter { !it.first }.map { it.second }

    /**
     * Create a value of type [T] from the current state of this mutable builder,
     * when the current state of this builder has already been validated.
     *
     * Concrete implementations of this method do not need to test for the validity
     * of builder properties and should never need to throw an [InvalidBuilderOperation].
     *
     * @return a newly created object of type [T]
     */
    protected abstract fun validatedBuild(): T
}


/**
 * Abstract base class for a Builder of some type [T], where some properties
 * of this builder are themselves Builder objects.
 *
 * @constructor
 * @param clazz the Class of object built by this builder. (The reified Class for type [T].)
 * @param innerBuilderProperties the list of properties for this builder that
 *                        are themselves Builder objects. Supplied as a list of
 *                        getter methods which, given a Builder object, returns
 *                        the corresponding properties.
 * @param validationRules a collection of validation rules for determining
 *                        whether the current state of the builder is valid.
 */
abstract class CompositeBuilder<TBuilder: CompositeBuilder<TBuilder, T>, out T: Any>(
    clazz: Class<T>,
    private val innerBuilderProperties: (TBuilder) -> List<Builder<*>?>,
    vararg validationRules: BuilderValidationRule<TBuilder>
) : AbstractBuilder<TBuilder, T>(clazz, *validationRules) {


    constructor(
        clazz: Class<T>,
        innerBuilderProperties: List<(TBuilder) -> Builder<*>?>,
        vararg validationRules: BuilderValidationRule<TBuilder>
    ) : this (
        clazz,
        { builder: TBuilder -> innerBuilderProperties.map { it(builder) } },
        *validationRules)

    override fun validate()
        // invoke validate for this builder
        = super.validate() +
        // invoke validate for each inner builder
        innerBuilderProperties(this as TBuilder).flatMap {
            it?.invalidProperties ?: emptyList()
        }
}

/**
 * Exception thrown if [Builder.build] is invoked for a builder whose current
 * state is invalid.
 */
@Suppress("MemberVisibilityCanBePrivate", "CanBeParameter")
class InvalidBuilderOperation(
    val typename: String,
    val errors: List<String>
) : Exception(message(typename, errors)) {
    companion object {
        private fun message(typename: String, errors: List<String>) = errors.joinToString(
            "\n",
            prefix = "Cannot build $typename:\n"
        )
    }
}
