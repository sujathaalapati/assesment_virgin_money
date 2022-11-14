package com.sujata.virginmoneydemo

import android.content.res.Resources.NotFoundException
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.CoreMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher


object ToolbarMatchers {
    fun withToolbarTitle(text: CharSequence): Matcher<View> {
        return WithCharSequenceMatcher(CoreMatchers.`is`(text), ToolbarMethod.GET_TITLE)
    }

    fun withToolbarSubtitle(text: CharSequence): Matcher<View> {
        return WithCharSequenceMatcher(CoreMatchers.`is`(text), ToolbarMethod.GET_SUBTITLE)
    }

    fun withToolbarTitle(resourceId: Int): Matcher<View> {
        return WithStringResourceMatcher(resourceId, ToolbarMethod.GET_TITLE)
    }

    fun withToolbarSubTitle(resourceId: Int): Matcher<View> {
        return WithStringResourceMatcher(resourceId, ToolbarMethod.GET_SUBTITLE)
    }

    private class WithCharSequenceMatcher constructor(
        private val charSequenceMatcher: Matcher<CharSequence>,
        private val method: ToolbarMethod
    ) :
        BoundedMatcher<View, Toolbar>(Toolbar::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("with text: ")
            charSequenceMatcher.describeTo(description)
        }

        override fun matchesSafely(toolbar: Toolbar): Boolean {
            val actualText: CharSequence = when (method) {
                ToolbarMethod.GET_TITLE -> toolbar.title
                ToolbarMethod.GET_SUBTITLE -> toolbar.subtitle
                else -> throw IllegalStateException("Unexpected Toolbar method: $method")
            }
            return charSequenceMatcher.matches(actualText)
        }
    }

    internal class WithStringResourceMatcher constructor(
        private val resourceId: Int,
        private val method: ToolbarMethod
    ) : BoundedMatcher<View, Toolbar>(Toolbar::class.java) {
        private var resourceName: String? = null

        private var expectedText: String? = null
        override fun describeTo(description: Description) {
            description.appendText("with string from resource id: ").appendValue(resourceId)
            if (null != resourceName) {
                description.appendText("[").appendText(resourceName).appendText("]")
            }
            if (null != expectedText) {
                description.appendText(" value: ").appendText(expectedText)
            }
        }

        public override fun matchesSafely(toolbar: Toolbar): Boolean {
            if (null == expectedText) {
                try {
                    expectedText = toolbar.resources.getString(resourceId)
                    resourceName = toolbar.resources.getResourceEntryName(resourceId)
                } catch (ignored: NotFoundException) {
                    /* view could be from a context unaware of the resource id. */
                }
            }
            val actualText: CharSequence = when (method) {
                ToolbarMethod.GET_TITLE -> toolbar.title
                ToolbarMethod.GET_SUBTITLE -> toolbar.subtitle
                else -> throw IllegalStateException("Unexpected Toolbar method: $method")
            }
            // FYI: actualText may not be string ... its just a char sequence convert to string.
            return null != expectedText && null != actualText && expectedText == actualText.toString()
        }
    }

    internal enum class ToolbarMethod {
        GET_TITLE, GET_SUBTITLE
    }
}