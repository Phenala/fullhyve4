package com.ux7.fullhyve;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.rule.ActivityTestRule;

import com.ux7.fullhyve.ui.activities.HomeView;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.ux7.fullhyve.ui.activities.SomeTest.childAtPosition;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

public class ChangeTaskStatus {

  @Rule
  public ActivityTestRule<HomeView> homeViewActivityTestRule = new ActivityTestRule<HomeView>(HomeView.class);


  @Test
  public void testTaskSetTaskDetail() throws Exception {
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
    onView(withId(R.id.task_list)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));
    onView(withId(R.id.task_status)).check(matches(withText("Waiting")));
    onView(withId(R.id.task_change_status_button)).perform(click());
    onView(withId(R.id.task_status)).check(matches(withText("In Progress")));
    onView(withId(R.id.task_change_status_button)).perform(click());
    onView(withText("Approve")).perform(click());
  }


}
