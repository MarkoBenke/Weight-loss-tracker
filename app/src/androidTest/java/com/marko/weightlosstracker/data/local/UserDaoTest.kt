package com.marko.weightlosstracker.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.marko.weightlosstracker.data.local.dao.UserDao
import com.marko.weightlosstracker.utils.DataGenerator
import com.google.common.truth.Truth.assertThat
import com.marko.weightlosstracker.data.local.db.WeightLossDatabase
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
class UserDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: WeightLossDatabase
    private lateinit var dao: UserDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.userDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertAndGetUser() = runBlockingTest {
        val user = DataGenerator.userCache
        dao.insertUser(user)

        val dbUser = dao.getUser()
        assertThat(dbUser).isEqualTo(user)
    }

    @Test
    fun updateUser() = runBlockingTest {
        val user = DataGenerator.userCache
        dao.insertUser(user)

        val insertedUser = dao.getUser()
        assertThat(insertedUser).isEqualTo(user)
        insertedUser?.startWeight = 999f
        dao.updateUser(insertedUser!!)

        val updatedUser = dao.getUser()
        assertThat(insertedUser).isEqualTo(updatedUser)
    }
}