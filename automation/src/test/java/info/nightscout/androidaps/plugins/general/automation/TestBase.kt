package info.nightscout.androidaps

import info.nightscout.shared.logging.AAPSLoggerTest
import info.nightscout.androidaps.utils.rx.AapsSchedulers
import info.nightscout.androidaps.utils.rx.TestAapsSchedulers
import org.junit.Before
import org.junit.Rule
import org.mockito.ArgumentMatcher
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import java.util.*

@Suppress("SpellCheckingInspection")
open class TestBase {

    val aapsLogger = AAPSLoggerTest()
    val aapsSchedulers: AapsSchedulers = TestAapsSchedulers()

    // Add a JUnit rule that will setup the @Mock annotated vars and log.
    // Another possibility would be to add `MockitoAnnotations.initMocks(this) to the setup method.
    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setupLocale() {
        Locale.setDefault(Locale.ENGLISH)
        System.setProperty("disableFirebase", "true")
    }

    // Workaround for Kotlin nullability.
    // https://medium.com/@elye.project/befriending-kotlin-and-mockito-1c2e7b0ef791
    // https://stackoverflow.com/questions/30305217/is-it-possible-to-use-mockito-in-kotlin
    fun <T> anyObject(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    fun <T> argThatKotlin(matcher: ArgumentMatcher<T>): T {
        Mockito.argThat(matcher)
        return uninitialized()
    }


    fun <T> eqObject(expected: T): T {
        Mockito.eq<T>(expected)
        return uninitialized()
    }

    @Suppress("Unchecked_Cast")
    fun <T> uninitialized(): T = null as T
}