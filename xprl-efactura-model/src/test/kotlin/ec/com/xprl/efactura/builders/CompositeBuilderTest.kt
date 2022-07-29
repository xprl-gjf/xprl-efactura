package ec.com.xprl.efactura.builders

import org.junit.jupiter.api.Named.named
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.*

/**
 * Verify the correct application of verification rules for an
 * implementation of [CompositeBuilder].
 */
internal class CompositeBuilderTest {

    /**
     * Verify that, when all builder validation rules for a [CompositeBuilder]
     * are satisfied, a call to [CompositeBuilder.build] may succeed.
     */
    @Test
    fun validCompositeBuilderCanBuild() {
        val builder = CompositeFooBuilder()
        minimumBuilderConfig(builder)
        assertEquals(builder.expectedResult, builder.build())

        completeBuilderConfig(builder)
        assertEquals(builder.expectedResult, builder.build())

        val barBuilder = CompositeBarBuilder()
        minimumBuilderConfig(barBuilder)
        assertEquals(barBuilder.expectedResult, barBuilder.build())

        completeBuilderConfig(barBuilder)
        assertEquals(barBuilder.expectedResult, barBuilder.build())
    }

    /**
     * Verify that the [CompositeBuilder.isValid] and [CompositeBuilder.invalidProperties]
     * properties return the expected results for permutations of invalid builder.
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("getInvalidFooBuilderConfigActions")
    fun validateInvalidCompositeBuilder_listsInvalidProperties(
        configAction: (CompositeFooBuilder) -> Unit,
        expectedErrors: Iterable<String>
    ) {
        val builder = CompositeFooBuilder()
        configAction.invoke(builder)

        assertFalse { builder.isValid }
        assertEquals(expectedErrors, builder.invalidProperties)
    }

    /**
     * Verify that the [CompositeBuilder.isValid] and [CompositeBuilder.invalidProperties]
     * properties return the expected results for permutations of invalid builder.
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("getInvalidBarBuilderConfigActions")
    fun validateAltInvalidCompositeBuilder_listsInvalidProperties(
        configAction: (CompositeBarBuilder) -> Unit,
        expectedErrors: Iterable<String>
    ) {
        val builder = CompositeBarBuilder()
        configAction.invoke(builder)

        assertFalse { builder.isValid }
        assertEquals(expectedErrors, builder.invalidProperties)
    }


    /**
     * Verify that invoking [CompositeBuilder.build] on a builder whose current state
     * is invalid throws an [InvalidBuilderOperation] exception listing the
     * validation errors.
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("getInvalidFooBuilderConfigActions")
    fun validateInvalidCompositeBuilder_raisesInvalidBuilderOperation(
        configAction: (CompositeFooBuilder) -> Unit,
        expectedErrors: Iterable<String>
    ) {
        val builder = CompositeFooBuilder()
        configAction.invoke(builder)

        val err = assertFailsWith(InvalidBuilderOperation::class) {
            builder.build()
        }
        assertTrue { err.message!!.startsWith("Cannot build int:") }
        assertTrue { expectedErrors.all { err.message!!.contains(it) } }
    }

    /**
     * Verify that invoking [CompositeBuilder.build] on a builder whose current state
     * is invalid throws an [InvalidBuilderOperation] exception listing the
     * validation errors.
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("getInvalidBarBuilderConfigActions")
    fun validateAltInvalidCompositeBuilder_raisesInvalidBuilderOperation(
        configAction: (CompositeBarBuilder) -> Unit,
        expectedErrors: Iterable<String>
    ) {
        val builder = CompositeBarBuilder()
        configAction.invoke(builder)

        val err = assertFailsWith(InvalidBuilderOperation::class) {
            builder.build()
        }
        assertTrue { err.message!!.startsWith("Cannot build int:") }
        assertTrue { expectedErrors.all { err.message!!.contains(it) } }
    }

    companion object {
        @JvmStatic
        private fun getInvalidFooBuilderConfigActions(): List<Arguments> = sequence {
            fooArgsOf(
                "empty builder",
                ::emptyBuilderConfig,
                "Required Property is null",
                "RequiredFoo Builder Property is null",
            )
            fooArgsOf(
                "partial builder",
                ::partialBuilderConfig,
                "RequiredFoo Builder Property is null"
            )
            fooArgsOf(
                "partial required builder",
                ::partialRequiredBuilderConfig,
                "Required Property is null",    // nested 'Required Property'
                "RequiredNotEmpty Property is empty",        // nested 'RequiredNotEmpty Property'
                "RequiredNotNullOrEmpty Property is empty"   // nested 'RequiredNotNullOrEmpty Property'
            )
            fooArgsOf(
                "partial optional builder",
                ::partialOptionalBuilderConfig,
                "Required Property is null",    // nested 'Required Property'
                "RequiredNotEmpty Property is empty",        // nested 'RequiredNotEmpty Property'
                "RequiredNotNullOrEmpty Property is empty"   // nested 'RequiredNotNullOrEmpty Property'
            )
        }.toList()

        @JvmStatic
        private fun getInvalidBarBuilderConfigActions(): List<Arguments> = sequence {
            barArgsOf(
                "empty builder",
                ::emptyBuilderConfig,
                "Required Property is null",
                "RequiredFoo Builder Property is null",
            )
            barArgsOf(
                "partial builder",
                ::partialBuilderConfig,
                "RequiredFoo Builder Property is null"
            )
            barArgsOf(
                "partial required builder",
                ::partialRequiredBuilderConfig,
                "Required Property is null",    // nested 'Required Property'
                "RequiredNotEmpty Property is empty",        // nested 'RequiredNotEmpty Property'
                "RequiredNotNullOrEmpty Property is empty"   // nested 'RequiredNotNullOrEmpty Property'
            )
            barArgsOf(
                "partial optional builder",
                ::partialOptionalBuilderConfig,
                "Required Property is null",    // nested 'Required Property'
                "RequiredNotEmpty Property is empty",        // nested 'RequiredNotEmpty Property'
                "RequiredNotNullOrEmpty Property is empty"   // nested 'RequiredNotNullOrEmpty Property'
            )
        }.toList()

        private suspend fun SequenceScope<Arguments>.fooArgsOf(
            name: String,
            configAction: (CompositeFooBuilder) -> Unit,
            vararg expectedErrors: String
        ) = argsOf(name, configAction, *expectedErrors)

        private suspend fun SequenceScope<Arguments>.barArgsOf(
            name: String,
            configAction: (CompositeBarBuilder) -> Unit,
            vararg expectedErrors: String
        ) = argsOf(name, configAction, *expectedErrors)

        private suspend fun <T: CompositeBuilder<*, *>> SequenceScope<Arguments>.argsOf(
            name: String,
            configAction: (T) -> Unit,
            vararg expectedErrors: String
        ) {
            yield(arguments(
                named(name, configAction),
                expectedErrors.toList()
            ))
        }

        private fun emptyBuilderConfig(builder: CompositeFooBuilder): CompositeFooBuilder = builder.apply { }

        private fun emptyBuilderConfig(builder: CompositeBarBuilder): CompositeBarBuilder = builder.apply { }

        private fun partialBuilderConfig(builder: CompositeFooBuilder): CompositeFooBuilder = builder.apply {
            required = 0
            // requiredFoo = null
        }

        private fun partialBuilderConfig(builder: CompositeBarBuilder): CompositeBarBuilder = builder.apply {
            required = 0
            // requiredFooBuilders = null
        }

        private fun partialRequiredBuilderConfig(builder: CompositeFooBuilder): CompositeFooBuilder = builder.apply {
            required = 0
            requiredFoo = FooBuilder()  // requiredFoo configuration is empty
        }

        private fun partialRequiredBuilderConfig(builder: CompositeBarBuilder): CompositeBarBuilder = builder.apply {
            required = 0
            requiredFooBuilders = listOf(FooBuilder())  // requiredFoo configuration is empty
        }

        private fun partialOptionalBuilderConfig(builder: CompositeFooBuilder): CompositeFooBuilder = builder.apply {
            required = 0
            requiredFoo = FooBuilder().minimumConfig()
            optionalFoo = FooBuilder()  // optionalFoo exists but is incomplete
        }

        private fun partialOptionalBuilderConfig(builder: CompositeBarBuilder): CompositeBarBuilder = builder.apply {
            required = 0
            requiredFooBuilders = listOf(FooBuilder().minimumConfig())
            optionalFoo = FooBuilder()  // optionalFoo exists but is incomplete
        }

        private fun minimumBuilderConfig(builder: CompositeFooBuilder): CompositeFooBuilder = builder.apply {
            required = 0
            requiredFoo = FooBuilder().minimumConfig()
        }

        private fun minimumBuilderConfig(builder: CompositeBarBuilder): CompositeBarBuilder = builder.apply {
            required = 0
            requiredFooBuilders = listOf(FooBuilder().minimumConfig())
        }

        private fun completeBuilderConfig(builder: CompositeFooBuilder): CompositeFooBuilder = minimumBuilderConfig(builder)
            .apply {
                requiredFoo = FooBuilder().completeConfig()
                optionalFoo = FooBuilder().completeConfig()
            }

        private fun completeBuilderConfig(builder: CompositeBarBuilder): CompositeBarBuilder = minimumBuilderConfig(builder)
            .apply {
                requiredFooBuilders = listOf(FooBuilder().completeConfig())
                optionalFoo = FooBuilder().completeConfig()
            }
    }
}

/**
 * CompositeBuilder using constructor with simple list of inner builder properties.
 */
internal class CompositeFooBuilder: CompositeBuilder<CompositeFooBuilder, Int>(
    Int::class.java,
    innerBuilderProperties = listOf(
        { it.requiredFoo },
        { it.optionalFoo }
    ),
    requires("Required Property") { it.required },
    requires("RequiredFoo Builder Property") { it.requiredFoo },
) {
    var required: Int? = null
    var requiredFoo: FooBuilder? = null
    var optionalFoo: FooBuilder? = null

    val expectedResult: Int = 98

    override fun validatedBuild(): Int {
        require(this.isValid)
        require(this.required != null)
        require(this.requiredFoo != null)
        require(this.requiredFoo!!.isValid)
        require(this.optionalFoo?.isValid ?: true)
        return expectedResult
    }
}

/**
 * CompositeBuilder using alternative constructor
 */
internal class CompositeBarBuilder: CompositeBuilder<CompositeBarBuilder, Int>(
    Int::class.java,
    innerBuilderProperties = { builder: CompositeBarBuilder ->
        listOf(builder.optionalFoo) +
        (builder.requiredFooBuilders ?: emptyList())
    },
    requires("Required Property") { it.required },
    requires("RequiredFoo Builder Property") { it.requiredFooBuilders },
) {
    var required: Int? = null
    var requiredFooBuilders: List<FooBuilder>? = null
    var optionalFoo: FooBuilder? = null

    val expectedResult: Int = 98

    override fun validatedBuild(): Int {
        require(this.isValid)
        require(this.required != null)
        require(this.requiredFooBuilders != null)
        require(this.requiredFooBuilders!!.all { it.isValid })
        require(this.optionalFoo?.isValid ?: true)
        return expectedResult
    }
}

