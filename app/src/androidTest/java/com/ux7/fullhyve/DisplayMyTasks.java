package com.ux7.fullhyve;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ux7.fullhyve.ui.activities.HomeView;

import junit.framework.AssertionFailedError;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.ux7.fullhyve.ui.activities.SomeTest.childAtPosition;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class DisplayMyTasks {

  @Rule
  public ActivityTestRule<HomeView> homeViewActivityTestRule = new ActivityTestRule<HomeView>(HomeView.class);


  @Test
  public void testTaskSetMyTasks() throws Exception {
    CountingIdlingResource idlingResource = new CountingIdlingResource("home");
    Espresso.registerIdlingResources(idlingResource);
    //DrawerActions.openDrawer(R.id.drawer_layout);

    Thread.sleep(1000);
    //onView(MenuMatcher.getNavigationItemWithString("Teams")).perform(click());

    onView(
      allOf(withContentDescription("Open navigation drawer"),
        childAtPosition(
          allOf(withId(R.id.toolbar),
            childAtPosition(
              withClassName(is("android.support.design.widget.AppBarLayout")),
              0)),
          1),
        isDisplayed())).perform(click());

    onView(MenuMatcher.getNavigationItemWithString("Projects")).perform(click());

    CountingIdlingResource idlingResource1 = new CountingIdlingResource("project");
    Espresso.registerIdlingResources(idlingResource1);
    //onView(MatchMultiple.withIndex(withText("no class tomorrow"),0)).check(matches(isDisplayed()));
    onView(withId(R.id.project_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    onView(withId(R.id.task_set_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));


    onView(
      allOf(withId(R.id.switchy),
        childAtPosition(
          allOf(withId(R.id.app_bar_switch),
            childAtPosition(
              withClassName(is("android.support.v7.widget.ActionMenuView")),
              1)),
          0),
        isDisplayed())).perform(click());




    final int[] numberOfAdapterItems = new int[1];
    onView(withId(R.id.task_list)).check(matches(new TypeSafeMatcher<View>() {
      @Override
      public boolean matchesSafely(View view) {
        RecyclerView listView = (RecyclerView) view;

        //here we assume the adapter has been fully loaded already
        numberOfAdapterItems[0] = listView.getAdapter().getItemCount();

        return true;
      }

      @Override
      public void describeTo(Description description) {

      }
    }));

    if(numberOfAdapterItems[0]!=0){
      throw new AssertionFailedError();
    }


}
}
