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
 * implementation of [AbstractBuilder].
 */
internal class BuilderTest {

    /**
     * Verify that, when all builder validation rules are satisfied,
     * a call to [Builder.build] may succeed.
     */
    @Test
    fun validBuilderCanBuild() {
        val builder = FooBuilder()
        minimumBuilderConfig(builder)

        assertEquals(builder.expectedResult, builder.build())

        completeBuilderConfig(builder)

        assertEquals(builder.expectedResult, builder.build())
    }

    /**
     * Verify that the [Builder.isValid] and [Builder.invalidProperties]
     * properties return the expected results for permutations of invalid builder.
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("getInvalidFooBuilderConfigActions")
    fun validateInvalidBuilder_listsInvalidProperties(
        configAction: (FooBuilder) -> Unit,
        expectedErrors: Iterable<String>
    ) {
        val builder = FooBuilder()
        configAction.invoke(builder)

        assertFalse { builder.isValid }
        assertEquals(expectedErrors, builder.invalidProperties)
    }

    /**
     * Verify that invoking [Builder.build] on a builder whose current state
     * is invalid throws an [InvalidBuilderOperation] exception listing the
     * validation errors.
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("getInvalidFooBuilderConfigActions")
    fun validateInvalidBuilder_raisesInvalidBuilderOperation(
        configAction: (FooBuilder) -> Unit,
        expectedErrors: Iterable<String>
    ) {
        val builder = FooBuilder()
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
            argsOf(
                "empty builder",
                ::emptyBuilderConfig,
                "Required Property is null",
                "RequiredNotEmpty Property is empty",
                "RequiredNotNullOrEmpty Property is empty"
            )
            argsOf(
                "partial builder",
                ::partialBuilderConfig,
                "RequiredNotNullOrEmpty Property is empty"
            )
        }.toList()

        private suspend fun SequenceScope<Arguments>.argsOf(
            name: String,
            configAction: (FooBuilder) -> Unit,
            vararg expectedErrors: String
        ) {
            yield(arguments(
                named(name, configAction),
                expectedErrors.toList()
            ))
        }

        private fun emptyBuilderConfig(builder: FooBuilder): FooBuilder = builder.apply { }

        private fun partialBuilderConfig(builder: FooBuilder): FooBuilder = builder.apply {
            required = 0
            requiredNotEmpty = listOf(0)
            // requiredNotNullOrEmpty = null
        }

        internal fun minimumBuilderConfig(builder: FooBuilder): FooBuilder = builder.apply {
            required = 0
            requiredNotNullOrEmpty = listOf(0)
            requiredNotEmpty = listOf(0)
        }

        internal fun completeBuilderConfig(builder: FooBuilder): FooBuilder = minimumBuilderConfig(builder)
            .apply {
            optional = 0
        }
    }
}


internal class FooBuilder: AbstractBuilder<FooBuilder, Int>(
    Int::class.java,
    requires("Required Property") { it.required },
    requiresNotEmpty("RequiredNotEmpty Property") { it.requiredNotEmpty },
    requiresNotEmpty("RequiredNotNullOrEmpty Property") { it.requiredNotNullOrEmpty },
) {
    var required: Int? = null
    var requiredNotEmpty: List<Int> = emptyList()
    var requiredNotNullOrEmpty: List<Int>? = null
    var optional: Int? = null

    val expectedResult: Int = 99

    override fun validatedBuild(): Int {
        require(this.isValid)
        require(this.required != null)
        require(this.requiredNotNullOrEmpty != null)
        require(this.requiredNotEmpty.isNotEmpty())
        require(this.requiredNotNullOrEmpty?.isNotEmpty() ?: false)
        return expectedResult
    }
}

internal fun FooBuilder.minimumConfig() = BuilderTest.minimumBuilderConfig(this)
internal fun FooBuilder.completeConfig() = BuilderTest.completeBuilderConfig(this)