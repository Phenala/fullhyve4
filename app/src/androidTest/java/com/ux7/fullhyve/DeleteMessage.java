package com.ux7.fullhyve;


import android.support.test.InstrumentationRegistry;
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
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class DeleteMessage {

  public String testMessage;
  @Rule
  public ActivityTestRule<HomeView> homeViewActivityTestRule=new ActivityTestRule<HomeView>(HomeView.class);

  @Before
  public void initValues(){
    testMessage="Message test test message";
  }

  @Test
  public void testDeleteMessage() throws Exception{
    CountingIdlingResource idlingResource=new CountingIdlingResource("home");

    Espresso.registerIdlingResources(idlingResource);
    Thread.sleep(1000);
    onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

    Espresso.unregisterIdlingResources(idlingResource);
    CountingIdlingResource idlingResource1=new CountingIdlingResource("chat");
    Espresso.registerIdlingResources(idlingResource1);
    onView(withId(R.id.messages_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0,longClick()));
    //onView(withId(R.id.message_option_delete)).perform(click());
    //openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
    onView(withText("Delete")).inRoot(isPlatformPopup()).perform(click());
    onView(withText("Delete")).inRoot(isDialog()).perform(click());
    Espresso.registerIdlingResources(idlingResource1);
    onView(withId(R.id.messages_view)).perform(RecyclerViewActions.scrollToPosition(0)).check(matches(RecylerMatcher.atPosition(0, not(hasDescendant(withText("message test"))))));

  }

}
