package com.ux7.fullhyve.ui.activities;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.ux7.fullhyve.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Switchmyteam {

  @Rule
  public ActivityTestRule<HomeView> mActivityTestRule = new ActivityTestRule<>(HomeView.class);

  @Test
  public void switchmyteam() {
    ViewInteraction appCompatButton = onView(
      allOf(withId(R.id.login_button), withText("Sign In"),
        childAtPosition(
          childAtPosition(
            withClassName(is("android.widget.LinearLayout")),
            0),
          2),
        isDisplayed()));
    appCompatButton.perform(click());

    ViewInteraction appCompatImageButton = onView(
      allOf(withContentDescription("Open navigation drawer"),
        childAtPosition(
          allOf(withId(R.id.toolbar),
            childAtPosition(
              withClassName(is("android.support.design.widget.AppBarLayout")),
              0)),
          1),
        isDisplayed()));
    appCompatImageButton.perform(click());

    ViewInteraction navigationMenuItemView = onView(
      allOf(childAtPosition(
        allOf(withId(R.id.design_navigation_view),
          childAtPosition(
            withId(R.id.nav_view),
            0)),
        3),
        isDisplayed()));
    navigationMenuItemView.perform(click());

    ViewInteraction appCompatImageButton2 = onView(
      allOf(withContentDescription("Open navigation drawer"),
        childAtPosition(
          allOf(withId(R.id.toolbar),
            childAtPosition(
              withClassName(is("android.support.design.widget.AppBarLayout")),
              0)),
          1),
        isDisplayed()));
    appCompatImageButton2.perform(click());

    ViewInteraction navigationMenuItemView2 = onView(
      allOf(childAtPosition(
        allOf(withId(R.id.design_navigation_view),
          childAtPosition(
            withId(R.id.nav_view),
            0)),
        4),
        isDisplayed()));
    navigationMenuItemView2.perform(click());

    ViewInteraction recyclerView = onView(
      allOf(withId(R.id.project_list),
        childAtPosition(
          withId(R.id.content_frame),
          0)));
    recyclerView.perform(actionOnItemAtPosition(0, click()));

    ViewInteraction tabView = onView(
      allOf(childAtPosition(
        childAtPosition(
          withId(R.id.project_tabs),
          0),
        2),
        isDisplayed()));
    tabView.perform(click());

    ViewInteraction tabView2 = onView(
      allOf(childAtPosition(
        childAtPosition(
          withId(R.id.project_tabs),
          0),
        0),
        isDisplayed()));
    tabView2.perform(click());

    ViewInteraction recyclerView2 = onView(
      allOf(withId(R.id.task_set_list),
        childAtPosition(
          withClassName(is("android.support.design.widget.CoordinatorLayout")),
          1)));
    recyclerView2.perform(actionOnItemAtPosition(1, click()));

    ViewInteraction appCompatImageButton3 = onView(
      allOf(withContentDescription("Navigate up"),
        childAtPosition(
          allOf(withId(R.id.action_bar),
            childAtPosition(
              withId(R.id.action_bar_container),
              0)),
          2),
        isDisplayed()));
    appCompatImageButton3.perform(click());

    ViewInteraction recyclerView3 = onView(
      allOf(withId(R.id.task_set_list),
        childAtPosition(
          withClassName(is("android.support.design.widget.CoordinatorLayout")),
          1)));
    recyclerView3.perform(actionOnItemAtPosition(0, click()));

    ViewInteraction recyclerView4 = onView(
      allOf(withId(R.id.task_list),
        childAtPosition(
          withId(R.id.linearLayout),
          1)));
    recyclerView4.perform(actionOnItemAtPosition(0, click()));

    ViewInteraction appCompatImageButton4 = onView(
      allOf(withContentDescription("Navigate up"),
        childAtPosition(
          allOf(withId(R.id.action_bar),
            childAtPosition(
              withId(R.id.action_bar_container),
              0)),
          2),
        isDisplayed()));
    appCompatImageButton4.perform(click());

    ViewInteraction switch_ = onView(
      allOf(withId(R.id.switchy),
        childAtPosition(
          allOf(withId(R.id.app_bar_switch),
            childAtPosition(
              withClassName(is("android.support.v7.widget.ActionMenuView")),
              1)),
          0),
        isDisplayed()));
    switch_.perform(click());

  }

  private static Matcher<View> childAtPosition(
    final Matcher<View> parentMatcher, final int position) {

    return new TypeSafeMatcher<View>() {
      @Override
      public void describeTo(Description description) {
        description.appendText("Child at position " + position + " in parent ");
        parentMatcher.describeTo(description);
      }

      @Override
      public boolean matchesSafely(View view) {
        ViewParent parent = view.getParent();
        return parent instanceof ViewGroup && parentMatcher.matches(parent)
          && view.equals(((ViewGroup) parent).getChildAt(position));
      }
    };
  }
}
