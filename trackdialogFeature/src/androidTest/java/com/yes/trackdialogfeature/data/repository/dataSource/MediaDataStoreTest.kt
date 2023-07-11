package com.yes.trackdialogfeature.data.repository.dataSource

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Before

class MediaDataStoreTest {
    var context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    private val cut=MediaDataStore(context)
    @Before
    fun setUp() {
    }
    @After
    fun tearDown() {
    }
}