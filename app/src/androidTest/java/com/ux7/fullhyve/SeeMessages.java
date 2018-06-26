package com.ux7.fullhyve;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ux7.fullhyve.ui.activities.HomeView;
import com.ux7.fullhyve.ui.activities.LoginView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


public class SeeMessages {
  @Rule
  public ActivityTestRule<HomeView> homeViewActivityTestRule=new ActivityTestRule<HomeView>(HomeView.class);


  @Test
  public void testMessages() throws Exception{

    CountingIdlingResource idlingResource=new CountingIdlingResource("home");
    Espresso.registerIdlingResources(idlingResource);
    Thread.sleep(2000);
    onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

    Espresso.unregisterIdlingResources(idlingResource);
    CountingIdlingResource idlingResource1=new CountingIdlingResource("chat");
    Espresso.registerIdlingResources(idlingResource1);

    onView(withId(R.id.messages_view)).check(matches(isDisplayed() ));

    Thread.sleep(4000);

  }

}
