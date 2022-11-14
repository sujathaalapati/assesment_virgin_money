package com.sujata.virginmoneydemo

import androidx.test.runner.AndroidJUnit4
import com.sujata.virginmoneydemo.domain.PeoplesData
import com.sujata.virginmoneydemo.domain.RoomsData
import com.sujata.virginmoneydemo.ui.peoples.PeoplesRecyclerAdapter
import com.sujata.virginmoneydemo.ui.rooms.RoomsRecyclerAdapter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import javax.inject.Inject

@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(AndroidJUnit4::class)
class AdapterTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var adapter: PeoplesRecyclerAdapter

    @Inject
    lateinit var roomAdapter: RoomsRecyclerAdapter

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun `people adapter test`() {
        assert(adapter.peoplesData.isEmpty())
        val list = arrayListOf(
            PeoplesData(avatar = null, "Bill", "Businessman", "Gates", "a@a.com", "Red"),
            PeoplesData(avatar = null, "Elon", "CEO Tesla", "Musk", "a@a.com", "Blue")
        )
        adapter.setListData(list)
        assert(adapter.peoplesData.size == 2)
        assert(adapter.itemCount == 2)
        assert((adapter.peoplesData as ArrayList<PeoplesData>).remove(adapter.peoplesData.first()))
        assert(adapter.peoplesData.size == 1)
        assert(adapter.peoplesData[0].firstName == "Elon")
        assert(adapter.peoplesData[0].jobtitle == "CEO Tesla")
    }

    @Test
    fun `room adapter test`() {
        assert(roomAdapter.roomsData.isEmpty())
        val list = arrayListOf(
            RoomsData(
                createdAt = "8-9-22",
                id = "19",
                isOccupied = false,
                maxOccupancy = 1000
            ),  RoomsData(
                createdAt = "8-9-21",
                id = "190",
                isOccupied = true,
                maxOccupancy = 2000
            )
        )
        roomAdapter.setListData(list)
        assert(roomAdapter.roomsData.size == 2)
        assert(roomAdapter.itemCount == 2)
        assert((roomAdapter.roomsData as ArrayList<RoomsData>).remove(roomAdapter.roomsData.last()))
        assert(roomAdapter.roomsData.size == 1)
        assert(roomAdapter.roomsData[0].id == "19")
        assert(!roomAdapter.roomsData[0].isOccupied)
    }
}