package com.ux7.fullhyve;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ux7.fullhyve.ui.activities.LoginView;
import com.ux7.fullhyve.ui.activities.RegisterView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.ux7.fullhyve.ui.activities.SomeTest.childAtPosition;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;


@RunWith(AndroidJUnit4.class)
public class SignUp {
  public String userName;
  public String password;
  public String firstName;
  public String lastName;
  @Rule
  public ActivityTestRule<LoginView> loginViewActivityTestRule=new ActivityTestRule<LoginView>(LoginView.class);

  @Before
  public void initValues(){
    firstName="FirstName";
    lastName="LastName";
    userName="someusername";
    password="1234";
  }

  @Test
  public void testSignUp(){
    onView(withId(R.id.login_sign_up)).perform(click());
    onView(withId(R.id.register_first_name)).perform(clearText(),typeText(firstName),closeSoftKeyboard());
    onView(withId(R.id.register_last_name)).perform(clearText(),typeText(lastName),closeSoftKeyboard());
    onView(withId(R.id.register_username)).perform(clearText(),typeText(userName),closeSoftKeyboard());
    onView(withId(R.id.register_password)).perform(clearText(),typeText(password),closeSoftKeyboard());
    onView(withId(R.id.register_confirm_password)).perform(clearText(),typeText(password),closeSoftKeyboard());
    onView(withId(R.id.register_button)).perform(click());

    onView(
      allOf(withContentDescription("Open navigation drawer"),
        childAtPosition(
          allOf(withId(R.id.toolbar),
            childAtPosition(
              withClassName(is("android.support.design.widget.AppBarLayout")),
              0)),
          1),
        isDisplayed())).perform(click());



    onView(withText("You have successfully been registered.")).inRoot(new ToastMatcher()).check(matches(isDisplayed()));
    onView(withId(R.id.profile_identity_name)).check(matches(withText(firstName+" "+lastName)));



  }



}
