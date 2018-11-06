package com.grzegorziwanek.tumblrviewer

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented getObjects_from_database, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under getObjects_from_database.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.grzegorziwanek.tumblrviewer", appContext.packageName)
    }
}
