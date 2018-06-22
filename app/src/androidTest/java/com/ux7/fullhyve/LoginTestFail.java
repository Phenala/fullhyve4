package com.ux7.fullhyve;


import android.support.test.espresso.Espresso;
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
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class LoginTestFail {

  public  String userName;
  public String password;
  @Rule
  public ActivityTestRule<LoginView> loginViewActivityTestRule=new ActivityTestRule<LoginView>(LoginView.class);

  @Before
  public void initValues(){
    userName="wrongUserName";
    password="1234";
  }
  @Test
  public void testLoginFail(){
    onView(withId(R.id.username)).perform(clearText(),typeText(userName),closeSoftKeyboard());
    onView(withId(R.id.password)).perform(clearText(),typeText(password),closeSoftKeyboard());
    onView(withId(R.id.login_button)).perform(click());

    CountingIdlingResource idlingResource=new CountingIdlingResource("home");
    Espresso.registerIdlingResources(idlingResource);

    onView(withText("Username or Password is incorrect.")).inRoot(new ToastMatcher()).check(matches(isDisplayed()));




  }

}
