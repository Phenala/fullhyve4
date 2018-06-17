package com.ux7.fullhyve;

import android.support.design.internal.NavigationMenu;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ux7.fullhyve.ui.activities.LoginView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerMatchers.isOpen;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class LoginTest {
  private String userName;
  private String password;

  @Rule
  public ActivityTestRule<LoginView> loginViewActivityTestRule=new ActivityTestRule<LoginView>(LoginView.class);

  @Before
  public void initValues(){
    userName="samwolde";
    password="1234";
  }

  @Test
  public void loginTest() throws Exception{
        onView(withId(R.id.username)).perform(clearText(),typeText(userName),closeSoftKeyboard());
        onView(withId(R.id.password)).perform(clearText(),typeText(password),closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());

        CountingIdlingResource idlingResource=new CountingIdlingResource("home");
        Espresso.registerIdlingResources(idlingResource);

        //Thread.sleep(2000);
      //onView(withId(R.id.))

        //openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
      onView(isRoot()).perform(swipeRight());
      //onView(withId(R.id.drawer_layout)).perform(DrawerActions.open(R.id.drawer_layout));

      //onView(withId(R.id.drawer_layout)).check(matches(isOpen()));
      onView(withId(R.id.profile_identity_name)).check(matches(withText("Samuel Woldemariam")));

  }

}
