package com.ux7.fullhyve;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.rule.ActivityTestRule;

import com.ux7.fullhyve.ui.activities.EditProfileView;
import com.ux7.fullhyve.ui.activities.HomeView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.ux7.fullhyve.ui.activities.SomeTest.childAtPosition;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

public class EditProfile {
  public String firstName;
  public String lastName;
  @Rule
  public ActivityTestRule<HomeView> homeActivityTestRule=new ActivityTestRule<HomeView>(HomeView.class);

  @Before public void init(){
    firstName="EditedFirstName";
    lastName="EditLastName";
  }

  @Test
  public void editProfile() throws Exception {
    onView(
      allOf(withContentDescription("Open navigation drawer"),
        childAtPosition(
          allOf(withId(R.id.toolbar),
            childAtPosition(
              withClassName(is("android.support.design.widget.AppBarLayout")),
              0)),
          1),
        isDisplayed())).perform(click());

    onView(MenuMatcher.getNavigationItemWithString("Profile")).perform(click());

    onView(withId(R.id.edit_profile_first_name)).perform(clearText(),typeText(firstName),closeSoftKeyboard());
    onView(withId(R.id.edit_profile_last_name)).perform(clearText(),typeText(lastName),closeSoftKeyboard());
    onView(withId(R.id.edit_profile_update_button)).perform(click());
    onView(
      allOf(withContentDescription("Open navigation drawer"),
        childAtPosition(
          allOf(withId(R.id.toolbar),
            childAtPosition(
              withClassName(is("android.support.design.widget.AppBarLayout")),
              0)),
          1),
        isDisplayed())).perform(click());


    onView(withId(R.id.profile_identity_name)).check(matches(withText(firstName+" "+lastName)));



  }
  }
