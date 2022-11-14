package com.sujata.virginmoneydemo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.runner.AndroidJUnit4
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.spy
import com.sujata.virginmoneydemo.data.PeoplesRepository
import com.sujata.virginmoneydemo.framework.PeoplesAPIDataSource
import com.sujata.virginmoneydemo.framework.api.PeoplesApiService
import com.sujata.virginmoneydemo.framework.api.Resource
import com.sujata.virginmoneydemo.framework.api.Status
import com.sujata.virginmoneydemo.framework.api.dto.Data
import com.sujata.virginmoneydemo.framework.api.dto.Peoples
import com.sujata.virginmoneydemo.framework.api.dto.PeoplesItem
import com.sujata.virginmoneydemo.ui.peoples.PeoplesViewModel
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.robolectric.annotation.Config
import retrofit2.Response

@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(AndroidJUnit4::class)
class ViewModelTest {

    @Mock
    private lateinit var peoplesApiService: PeoplesApiService


    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


    val dispatcher = StandardTestDispatcher()


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        //MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(dispatcher)
        peoplesApiService = mock()
    }

    @Test
    @Throws(Exception::class)
    fun `People data success`() = runTest {
        launch {
            val peoples = Peoples().apply {
                add(
                    PeoplesItem(
                        "abc.com/s.png",
                        "12-11-22",
                        Data("Some Body", "a@jsa.ck", "kjsa9", "`12334", "Meeting", "h@mc.ck"),
                        email = "ab@as.ck",
                        firstName = "Bill",
                        lastName = "Gates",
                        fromName = "Hello",
                        id = "ashja",
                        jobtitle = "Buisness Man",
                        to = "Hello",
                        favouriteColor = "Red"
                    )
                )
            }
            val peoplesApiService: PeoplesApiService = mock()
            Mockito.`when`(peoplesApiService.getPeoples()).thenReturn(Response.success(peoples))
            assert(peoplesApiService.getPeoples().isSuccessful)
        }
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `test Api Error`() = runTest {

        launch {
            Mockito.`when`(peoplesApiService.getPeoples()).thenReturn(Response.error(500,byteArrayOf().toResponseBody()))
            assert(!peoplesApiService.getPeoples().isSuccessful)
            assert(peoplesApiService.getPeoples().code() == 500)

        }
    }
}