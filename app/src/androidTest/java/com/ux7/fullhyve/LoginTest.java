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

import com.ux7.fullhyve.cases.LoginCase;
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
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.ux7.fullhyve.ui.activities.SomeTest.childAtPosition;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class LoginTest {
  private LoginCase[] cases;

  @Rule
  public ActivityTestRule<LoginView> loginViewActivityTestRule= new ActivityTestRule<>(LoginView.class);

  @Before
  public void initValues(){
      cases = new LoginCase[] {

              new LoginCase("samwolde", "1234", "Samuel Kedison"),
              new LoginCase("abekebe", "123", "Abebe Ayele"),
              new LoginCase("ravenx", "123456", "Raven Dynamite"),
              new LoginCase("mulekebe", "12345", "Poodles Workineh"),
              new LoginCase("funkyx", "123456", "Funky Dough"),
              new LoginCase("dawnerx", "123456", "Dawner Born"),
              new LoginCase("yonhaile", "6789", "Yonas Shimeles"),
              new LoginCase("jondoe", "6789", "Jon Doe"),
              new LoginCase("alexmarc", "6789", "Alex Pablo"),

      };
  }

  @Test
  public void loginTest() throws Exception{

      for (LoginCase loginCase : cases) {
          onView(withId(R.id.username)).perform(clearText(), typeText(loginCase.username), closeSoftKeyboard());
          onView(withId(R.id.password)).perform(clearText(), typeText(loginCase.password), closeSoftKeyboard());
          onView(withId(R.id.login_button)).perform(click());

          CountingIdlingResource idlingResource = new CountingIdlingResource("home");
          Espresso.registerIdlingResources(idlingResource);

          //Thread.sleep(2000);
          //onView(withId(R.id.))

          //openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
          onView(isRoot()).perform(swipeRight());
          //onView(withId(R.id.drawer_layout)).perform(DrawerActions.open(R.id.drawer_layout));

          //onView(withId(R.id.drawer_layout)).check(matches(isOpen()));
          onView(withId(R.id.profile_identity_name)).check(matches(withText(loginCase.name)));

          CountingIdlingResource idlingResource1 = new CountingIdlingResource("home");
          Espresso.registerIdlingResources(idlingResource1);

          onView(
                  allOf(withContentDescription("Open navigation drawer"),
                          childAtPosition(
                                  allOf(withId(R.id.toolbar),
                                          childAtPosition(
                                                  withClassName(is("android.support.design.widget.AppBarLayout")),
                                                  0)),
                                  1),
                          isDisplayed())).perform(click());

          onView(MenuMatcher.getNavigationItemWithString("Log Out")).perform(click());

          onView(withText("Log Out")).inRoot(isDialog()).perform(click());

          Thread.sleep(1000);
          onView(withId(R.id.login_view)).check(matches(isDisplayed()));

      }

  }

}
