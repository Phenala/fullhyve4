package com.ux7.fullhyve;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.DrawerActions;
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

import static android.support.test.espresso.Espresso.onData;
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
import static com.ux7.fullhyve.ui.activities.SomeTest.childAtPosition;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;


@RunWith(AndroidJUnit4.class)
public class CreateNewTeam {

  public String teamName;
  public String teamFocus;
  public String teamDescription;

  @Rule
  public ActivityTestRule<HomeView> homeViewActivityTestRule=new ActivityTestRule<HomeView>(HomeView.class);

  @Before
  public void initValues(){
    teamName="New Team";
    teamFocus="Team Focus";
    teamDescription="Describe Team Here";

  }

  @Test
  public void testDisplayTeams() throws Exception{

    CountingIdlingResource idlingResource=new CountingIdlingResource("home");
    Espresso.registerIdlingResources(idlingResource);

    //onView(isRoot()).perform(swipeRight());
    //onView(isRoot()).perform(swipeLeft());
    DrawerActions.openDrawer(R.id.drawer_layout);
    //onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
    Thread.sleep(1000);
    ViewInteraction navigationMenuItemView = onView(
      allOf(childAtPosition(
        allOf(withId(R.id.design_navigation_view),
          childAtPosition(
            withId(R.id.nav_view),
            0)),
        3),
        isDisplayed()));

    ViewInteraction perform = navigationMenuItemView.perform(click());
    onView(MenuMatcher.getNavigationItemWithString("Teams")).perform(click());

    onView(withId(R.id.fab)).perform(click());

    onView(withId(R.id.new_team_name)).perform(clearText(),typeText(teamName),closeSoftKeyboard());
    onView(withId(R.id.new_team_focus)).perform(clearText(),typeText(teamFocus),closeSoftKeyboard());
    onView(withId(R.id.new_team_description)).perform(clearText(),typeText(teamDescription),closeSoftKeyboard());

    onView(withId(R.id.new_team_create)).perform(click());



    final int[] numberOfAdapterItems = new int[1];
    onView(withId(R.id.list)).check(matches(new TypeSafeMatcher<View>() {
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


      onView(withId(R.id.list))
        .perform(RecyclerViewActions.scrollToPosition(numberOfAdapterItems[0] - 1))
      .check(matches(RecylerMatcher.atPosition(numberOfAdapterItems[0] - 1,hasDescendant(withText(teamName)))));



  }





}
