package com.ux7.fullhyve;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ux7.fullhyve.ui.activities.HomeView;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.ux7.fullhyve.ui.activities.SomeTest.childAtPosition;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
public class DeleteReply {

  @Rule
  public ActivityTestRule<HomeView> homeViewActivityTestRule=new ActivityTestRule<HomeView>(HomeView.class);

  @Test
  public void testDeleteReply() throws Exception {
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

    onView(MenuMatcher.getNavigationItemWithString("Teams")).perform(click());

    onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    CountingIdlingResource idlingResource1 = new CountingIdlingResource("team");
    Espresso.registerIdlingResources(idlingResource1);
    //onView(MatchMultiple.withIndex(withText("no class tomorrow"),0)).check(matches(isDisplayed()));
    onView(withId(R.id.announcements_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

    final int[] numberOfAdapterItems = new int[1];
    onView(withId(R.id.reply_list)).check(matches(new TypeSafeMatcher<View>() {
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



    Thread.sleep(1000);
    onView(withId(R.id.reply_list))
      .perform(RecyclerViewActions.
        scrollToPosition(numberOfAdapterItems[0] - 1),RecyclerViewActions.actionOnItemAtPosition(numberOfAdapterItems[0]-1,longClick()));


    onView(withText("Delete")).inRoot(isPlatformPopup()).perform(click());
    onView(withText("Delete")).inRoot(isDialog()).perform(click());

    //onView(MatchMultiple.withIndex(withId(R.id.reply_content),0)).check(matches(withText(testReply)));

    onView(withId(R.id.reply_list))
      .perform(RecyclerViewActions.
        scrollToPosition(numberOfAdapterItems[0] - 1))
      .check(matches(not(RecylerMatcher.atPosition(numberOfAdapterItems[0] - 2,not(hasDescendant(withText("Test Announcement Text")))))));



  }


}
