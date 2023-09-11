package com.dicoding.courseschedule.ui.home

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.add.AddCourseActivity
import org.junit.Before
import org.junit.Test

class HomeActivityTest {

    @Before
    fun setUp(){
        ActivityScenario.launch(HomeActivity::class.java)
    }

    @Test
    fun showAddCourseActivity(){
        Intents.init()
        onView(withId(R.id.action_add)).perform(click())
        Intents.intended(IntentMatchers.hasComponent(AddCourseActivity::class.java.name))
        Intents.release()
    }
}