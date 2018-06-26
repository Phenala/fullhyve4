//package com.ux7.fullhyve;
//
//import android.support.test.espresso.Espresso;
//import android.support.test.espresso.contrib.RecyclerViewActions;
//import android.support.test.espresso.idling.CountingIdlingResource;
//import android.support.test.rule.ActivityTestRule;
//import android.support.test.runner.AndroidJUnit4;
//
//import com.ux7.fullhyve.cases.MessagingCase;
//import com.ux7.fullhyve.ui.activities.HomeView;
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.action.ViewActions.clearText;
//import static android.support.test.espresso.action.ViewActions.click;
//import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
//import static android.support.test.espresso.action.ViewActions.swipeRight;
//import static android.support.test.espresso.action.ViewActions.typeText;
//import static android.support.test.espresso.assertion.ViewAssertions.matches;
//import static android.support.test.espresso.matcher.RootMatchers.isDialog;
//import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
//import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
//import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
//import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
//import static android.support.test.espresso.matcher.ViewMatchers.withId;
//import static android.support.test.espresso.matcher.ViewMatchers.withText;
//import static com.ux7.fullhyve.ui.activities.SomeTest.childAtPosition;
//import static org.hamcrest.Matchers.allOf;
//import static org.hamcrest.Matchers.is;
//
//@RunWith(AndroidJUnit4.class)
//public class UsersMessaging {
//
//  MessagingCase[] messagingCases;
//
//  @Rule
//  public ActivityTestRule<HomeView> homeViewActivityTestRule=new ActivityTestRule<HomeView>(HomeView.class);
//
//  @Before
//  public void initValues(){
//      messagingCases = new MessagingCase[] {
//
//              new MessagingCase("funkyx","123456", "ravenx","123456", "Test Message 1"),
//              new MessagingCase("ravenx","123456","funkyx","123456",  "Test Message 2"),
//              new MessagingCase("dawnerx","123456","funkyx","123456",  "Test Message 3"),
//              new MessagingCase("funkyx","123456","dawnerx","123456",  "Test Message 4"),
//              new MessagingCase("dawnerx","123456","ravenx","123456",  "Test Message 2"),
//              new MessagingCase("ravenx","123456","dawnerx","123456",  "Test Message 2"),
//
//      };
//  }
//
//  @Test
//  public void sendMessageTest() throws Exception{
//
//      for (MessagingCase messagingCase : messagingCases) {
//
//
//
//          onView(withId(R.id.username)).perform(clearText(), typeText(messagingCase.senderUserName), closeSoftKeyboard());
//          onView(withId(R.id.password)).perform(clearText(), typeText(messagingCase.senderPassword), closeSoftKeyboard());
//          onView(withId(R.id.login_button)).perform(click());
//
//          CountingIdlingResource idlingResource = new CountingIdlingResource("home");
//          Espresso.registerIdlingResources(idlingResource);
//
//          onView(
//                  allOf(withContentDescription("Open navigation drawer"),
//                          childAtPosition(
//                                  allOf(withId(R.id.toolbar),
//                                          childAtPosition(
//                                                  withClassName(is("android.support.design.widget.AppBarLayout")),
//                                                  0)),
//                                  1),
//                          isDisplayed())).perform(click());
//
//          onView(MenuMatcher.getNavigationItemWithString("Log Out")).perform(click());
//
//          onView(withText("Log Out")).inRoot(isDialog()).perform(click());
//
//          Thread.sleep(1000);
//          onView(withId(R.id.login_view)).check(matches(isDisplayed()));
//
//
//
//
//
//
//
//          CountingIdlingResource idlingResource = new CountingIdlingResource("home");
//          Espresso.registerIdlingResources(idlingResource);
//          Thread.sleep(2000);
//          onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
//
//          Espresso.unregisterIdlingResources(idlingResource);
//          CountingIdlingResource idlingResource1 = new CountingIdlingResource("chat");
//          Espresso.registerIdlingResources(idlingResource1);
//
//          onView(withId(R.id.messageToSend)).perform(clearText(), typeText(testMessage), closeSoftKeyboard());
//          onView(withId(R.id.messageSendButton)).perform(click());
//
//          Espresso.registerIdlingResources(idlingResource1);
//          onView(withId(R.id.messages_view))
//                  .perform(RecyclerViewActions.scrollToPosition(0)).check(matches(RecylerMatcher.atPosition(0, hasDescendant(withText(testMessage)))));
//
//          CountingIdlingResource idlingResource1=new CountingIdlingResource("home");
//          Espresso.registerIdlingResources(idlingResource1);
//
//          onView(
//                  allOf(withContentDescription("Open navigation drawer"),
//                          childAtPosition(
//                                  allOf(withId(R.id.toolbar),
//                                          childAtPosition(
//                                                  withClassName(is("android.support.design.widget.AppBarLayout")),
//                                                  0)),
//                                  1),
//                          isDisplayed())).perform(click());
//
//          onView(MenuMatcher.getNavigationItemWithString("Log Out")).perform(click());
//
//          onView(withText("Log Out")).inRoot(isDialog()).perform(click());
//          onView(withId(R.id.login_view)).check(matches(isDisplayed()));
//
//      }
//
//
//  }
//
//
//}
//
//
//
