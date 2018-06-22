package com.ux7.fullhyve;

import android.support.design.internal.NavigationMenuItemView;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.view.View;

import org.hamcrest.Matcher;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class MenuMatcher {
  public static Matcher<View> getNavigationItemWithString(String string) {
    Matcher<View> childMatcher = allOf(isAssignableFrom(AppCompatCheckedTextView.class), withText(string));
    return allOf(isAssignableFrom(NavigationMenuItemView.class), withChild(childMatcher));
  }
}
