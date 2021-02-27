package com.marko.weightlosstracker

import com.marko.weightlosstracker.data.local.QuoteDaoTest
import com.marko.weightlosstracker.data.local.UserDaoTest
import com.marko.weightlosstracker.data.local.WeightEntryDaoTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite

@ExperimentalCoroutinesApi
@RunWith(Suite::class)
@Suite.SuiteClasses(QuoteDaoTest::class, UserDaoTest::class, WeightEntryDaoTest::class)
class DaoTestSuite