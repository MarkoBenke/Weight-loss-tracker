package com.example.weightlosstracker.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.weightlosstracker.data.local.dao.QuoteDao
import com.example.weightlosstracker.utils.DataGenerator
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class QuoteDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: WeightLossDatabase
    private lateinit var dao: QuoteDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.quoteDao()
    }

    @After
    fun teardown() {
        database.close()
    }


    @Test
    fun insertAndGetQuote() = runBlockingTest {
        val quote = DataGenerator.quoteCache
        dao.insertQuote(quote)

        val allQuotes = dao.getAllQuotes()
        assertThat(allQuotes).contains(quote)
    }
}