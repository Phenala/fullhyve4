package com.ux7.fullhyve;

import android.support.design.widget.NavigationView;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ux7.fullhyve.ui.activities.LoginView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerMatchers.isOpen;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.ux7.fullhyve.ui.activities.SomeTest.childAtPosition;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class DisplayTeams {

  public  String userName;
  public String password;
  @Rule
  public ActivityTestRule<LoginView> loginViewActivityTestRule=new ActivityTestRule<LoginView>(LoginView.class);

  @Before
  public void initValues(){
    userName="samwolde";
    password="1234";
  }
  @Test
  public void testDisplayTeams() throws Exception{
    onView(withId(R.id.username)).perform(clearText(),typeText(userName),closeSoftKeyboard());
    onView(withId(R.id.password)).perform(clearText(),typeText(password),closeSoftKeyboard());
    onView(withId(R.id.login_button)).perform(click());

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
    //onView(withId(R.id.nav_options)).perform(NavigationViewActions.navigateTo(R.id.nav_teams));

    onView(withId(R.id.list)).check(matches(isDisplayed()));


  }

}
