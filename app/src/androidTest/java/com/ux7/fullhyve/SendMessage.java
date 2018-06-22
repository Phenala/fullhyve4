package com.ux7.fullhyve;

import android.support.annotation.NonNull;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ux7.fullhyve.ui.activities.HomeView;
import com.ux7.fullhyve.ui.activities.LoginView;
import com.ux7.fullhyve.ui.util.U;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class SendMessage {
  public  String userName;
  public String password;
  public String testMessage;
  @Rule
  public ActivityTestRule<HomeView> homeViewActivityTestRule=new ActivityTestRule<HomeView>(HomeView.class);

  @Before
  public void initValues(){
    testMessage="Message test test message";
  }

  @Test
  public void sendMessageTest() throws Exception{
    CountingIdlingResource idlingResource=new CountingIdlingResource("home");
    Espresso.registerIdlingResources(idlingResource);
    Thread.sleep(2000);
    onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

    Espresso.unregisterIdlingResources(idlingResource);
    CountingIdlingResource idlingResource1=new CountingIdlingResource("chat");
    Espresso.registerIdlingResources(idlingResource1);

    onView(withId(R.id.messageToSend)).perform(clearText(),typeText(testMessage),closeSoftKeyboard());
    onView(withId(R.id.messageSendButton)).perform(click());

    Espresso.registerIdlingResources(idlingResource1);
    onView(withId(R.id.messages_view))
      .perform(RecyclerViewActions.scrollToPosition(0)).check(matches(RecylerMatcher.atPosition(0, hasDescendant(withText(testMessage)))));




  }


}



