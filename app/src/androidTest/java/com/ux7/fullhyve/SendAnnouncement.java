package com.ux7.fullhyve;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
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
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.ux7.fullhyve.ui.activities.SomeTest.childAtPosition;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;



@RunWith(AndroidJUnit4.class)
public class SendAnnouncement {
  public  String userName;
  public String password;
  public String testAnnouncementText;
  @Rule
  public ActivityTestRule<LoginView> loginViewActivityTestRule=new ActivityTestRule<LoginView>(LoginView.class);

  @Before
  public void initValues(){
    userName="samwolde";
    password="1234";
    testAnnouncementText="Test Announcement Text";
  }
  @Test
  public void testSendAnnouncement() throws Exception {

    onView(withId(R.id.username)).perform(clearText(), typeText(userName), closeSoftKeyboard());
    onView(withId(R.id.password)).perform(clearText(), typeText(password), closeSoftKeyboard());
    onView(withId(R.id.login_button)).perform(click());
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

    onView(withId(R.id.announcements_view)).check(matches(isDisplayed()));
    onView(withId(R.id.announcement_to_send)).perform(typeText(testAnnouncementText),closeSoftKeyboard());
    onView(withId(R.id.announcement_send_button)).perform(click());

    onView(withId(R.id.announcements_view))
      .perform(RecyclerViewActions.scrollToPosition(0)).check(matches(RecylerMatcher.atPosition(0, hasDescendant(withText(testAnnouncementText)))));
  }
}
