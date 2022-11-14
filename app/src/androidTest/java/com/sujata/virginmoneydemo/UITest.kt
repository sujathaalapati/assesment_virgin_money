package com.sujata.virginmoneydemo

//import org.robolectric.annotation.Config

import android.os.SystemClock
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sujata.virginmoneydemo.ui.peoples.PeoplesRecyclerAdapter
import junit.framework.Assert.assertEquals
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class UITest {

    @get:Rule
    var mActivityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testBottomNav() {
        SystemClock.sleep(2000)
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
        onView(withId(R.id.peoplesFragment)).check(matches(isDisplayed()))
        onView(withId(R.id.peoplesFragment)).perform(click())
        onView(withId(R.id.roomsFragment)).check(matches(isDisplayed()))
        onView(withId(R.id.roomsFragment)).perform(click())
    }

    @Test
    fun testToolBarTitles() {
        SystemClock.sleep(2000)
        onView(allOf(instanceOf(TextView::class.java), withParent(withResourceName("action_bar")))).check(matches(withText("Peoples")))
        onView(withId(R.id.roomsFragment)).perform(click())
        SystemClock.sleep(200)
        onView(allOf(instanceOf(TextView::class.java), withParent(withResourceName("action_bar")))).check(matches(withText("Rooms")))
        onView(withId(R.id.peoplesFragment)).check(matches(isDisplayed())).perform(click())
        onView(allOf(instanceOf(TextView::class.java), withParent(withResourceName("action_bar")))).check(matches(withText("Peoples")))
        onView(withId(R.id.recyclerview)).perform(
            RecyclerViewActions.actionOnItemAtPosition<PeoplesRecyclerAdapter.ViewHolder>(
                0,
                click()
            )
        )
        SystemClock.sleep(200)
        onView(allOf(instanceOf(TextView::class.java), withParent(withResourceName("action_bar")))).check(matches(withText("People Details")))

    }

    @Test
    @SmallTest
    fun testAddItemsWithoutMenuInflation() {
        SystemClock.sleep(1000)
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.onActivity {
            val navigation = BottomNavigationView(it)
            it.setContentView(navigation)
            navigation.menu.add("Item1")
            navigation.menu.add("Item2")
            assertEquals(2, navigation.menu.size())
            navigation.menu.removeItem(0)
            navigation.menu.removeItem(0)
            assertEquals(0, navigation.menu.size())
        }
    }


    @Test
    fun testPeoplesItems() {
        SystemClock.sleep(100)
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
        SystemClock.sleep(7000)
        onView(
            withIndex(
                withId(R.id.peopleName),
                0
            )
        ).check(matches(withText("MAGGIE BREKKE")))

        onView(
            withIndex(
                withId(R.id.titleTV),
                0
            )
        ).check(matches(withText("Future Functionality Strategist")))
        onView(
            withIndex(
                withId(R.id.peopleName),
                1
            )
        ).check(matches(withText("ARMANDO WEBER")))
        onView(
            withIndex(
                withId(R.id.titleTV),
                1
            )
        ).check(matches(withText("Principal Accounts Developer")))

    }

    @Test
    fun testPeoplesDetailItems() {
        SystemClock.sleep(100)
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
        SystemClock.sleep(7000)
        onView(
            withIndex(
                withId(R.id.peopleName),
                0
            )
        ).check(matches(withText("MAGGIE BREKKE")))
        onView(withId(R.id.recyclerview)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,click()))
        SystemClock.sleep(1000)
        onView(withId(R.id.textView)).check(matches(withText("Name")))
        onView(withId(R.id.textView3)).check(matches(withText("Published")))
        onView(withId(R.id.authorNameTV)).check(matches(withText("Maggie Brekke")))
        onView(withId(R.id.publishedDateTV)).check(matches(withText("Crystel.Nicolas61@hotmail.com")))
        pressBack()
    }

    @Test
    fun testRoomDetails(){
        SystemClock.sleep(100)
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
        onView(withId(R.id.roomsFragment)).perform(click())
        SystemClock.sleep(7000)
        onView(
            withIndex(
                withId(R.id.textView1),
                0
            )
        ).check(matches(withText("Room No: 1")))
        onView(
            withIndex(
                withId(R.id.textView2),
                0
            )
        ).check(matches(withText("Available: YES")))
        onView(
            withIndex(
                withId(R.id.textView3),
                0
            )
        ).check(matches(withText("Room Capacity: 53539")))
        onView(
            withIndex(
                withId(R.id.textView1),
                1
            )
        ).check(matches(withText("Room No: 2")))
        onView(
            withIndex(
                withId(R.id.textView2),
                1
            )
        ).check(matches(withText("Available: YES")))
        onView(
            withIndex(
                withId(R.id.textView3),
                1
            )
        ).check(matches(withText("Room Capacity: 34072")))
        pressBack()

    }

    private fun withIndex(matcher: Matcher<View?>, index: Int): Matcher<View?>? {
        return object : TypeSafeMatcher<View?>() {
            var currentIndex = 0
            override fun describeTo(description: Description) {
                description.appendText("with index: ")
                description.appendValue(index)
                matcher.describeTo(description)
            }

            override fun matchesSafely(view: View?): Boolean {
                return matcher.matches(view) && currentIndex++ == index
            }
        }
    }
}