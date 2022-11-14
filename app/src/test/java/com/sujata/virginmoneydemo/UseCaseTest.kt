package com.sujata.virginmoneydemo

import androidx.test.runner.AndroidJUnit4
import com.nhaarman.mockito_kotlin.mock
import com.sujata.virginmoneydemo.data.PeopleDataSource
import com.sujata.virginmoneydemo.data.RoomsDataSource
import com.sujata.virginmoneydemo.domain.PeoplesData
import com.sujata.virginmoneydemo.domain.RoomsData
import com.sujata.virginmoneydemo.framework.PeoplesAPIDataSource
import com.sujata.virginmoneydemo.framework.RoomsAPIDataSource
import com.sujata.virginmoneydemo.framework.api.PeoplesApiService
import com.sujata.virginmoneydemo.framework.api.dto.*
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.annotation.Config
import retrofit2.Response
import javax.inject.Inject

@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(AndroidJUnit4::class)
class UseCaseTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var datasource: RoomsDataSource

    @Inject
    lateinit var peoplesAPIDataSource: PeoplesAPIDataSource

    @Inject
    lateinit var roomApiDataSource: RoomsAPIDataSource

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun `test room data source`() = runTest {
        val dataSource = mock<RoomsDataSource>()
        launch {
            val domainItems = listOf<RoomsData>(
                RoomsData(
                    createdAt = "Some",
                    id = "1",
                    isOccupied = false,
                    maxOccupancy = 1000
                )
            )
            Mockito.`when`(dataSource.getRoomsData()).thenReturn(domainItems)
            assert(dataSource.getRoomsData().size == 1)
        }
    }

    @Test
    fun `test people data source`() = runTest {
        val dataSource = mock<PeopleDataSource>()
        launch {
            val list = listOf<PeoplesData>(
                PeoplesData(avatar = null, "Bill", "Businessman", "Gates", "a@a.com", "Red"),
                PeoplesData(avatar = null, "Elon", "CEO Tesla", "Musk", "a@a.com", "Blue")
            )
            Mockito.`when`(dataSource.getPeoplesData()).thenReturn(list)
            assert(dataSource.getPeoplesData().size == 2)
            assert(dataSource.getPeoplesData().last().lastName == "Musk")
            assert(dataSource.getPeoplesData().first().favouriteColor == "Red")
        }
    }

    @Test
    fun `test people transformation`() = runTest {
        val apiService = mock<PeoplesApiService>()
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
            Mockito.`when`(apiService.getPeoples()).thenReturn(Response.success(peoples))
            assert(apiService.getPeoples().isSuccessful)
            val transform =
                peoplesAPIDataSource.transformPeoplesList(apiService.getPeoples().body()!!)
            assert(transform.size == peoples.size)
            assert(transform.first().lastName == peoples.first().lastName)
            assert(transform.first().jobtitle == peoples.first().jobtitle)
        }
    }

    @Test
    fun `test room transformation`() = runTest {
        val apiService = mock<PeoplesApiService>()
        launch {
            val rooms = Rooms().apply {
                add(
                    RoomsItem(
                        "12-11-22",
                        "218782178",
                        isOccupied = false,
                        maxOccupancy = 2510
                    )
                )

                add(
                    RoomsItem(
                        "13-11-22",
                        "29187821178",
                        isOccupied = false,
                        maxOccupancy = 1210
                    )
                )
                add(
                    RoomsItem(
                        "12-12-22",
                        "21548782178",
                        isOccupied = false,
                        maxOccupancy = 2010
                    )
                )

            }
            Mockito.`when`(apiService.getRooms()).thenReturn(Response.success(rooms))
            assert(apiService.getRooms().isSuccessful)
            val transform =
                roomApiDataSource.transformRoomData(apiService.getRooms().body()!!)
            assert(transform.size == rooms.size)
            assert(transform.first().id == rooms.first().id)
            assert(transform.first().createdAt == rooms.first().createdAt)
            assert(transform[1].id == rooms[1].id)
            assert(transform[1].createdAt == rooms[1].createdAt)
            assertEquals(transform[1].maxOccupancy, 1210)
            assertEquals(rooms[1].maxOccupancy, 1210)
        }
    }
}