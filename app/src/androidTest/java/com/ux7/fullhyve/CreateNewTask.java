package com.ux7.fullhyve;

import android.support.annotation.IdRes;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ux7.fullhyve.ui.activities.HomeView;
import com.ux7.fullhyve.ui.adapters.TaskSetRecyclerViewAdapter;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.ux7.fullhyve.ui.activities.SomeTest.childAtPosition;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class CreateNewTask {
  public String taskName;
  public String taskDescription;
  public String taskAssignee;

  @Rule
  public ActivityTestRule<HomeView> homeViewActivityTestRule = new ActivityTestRule<HomeView>(HomeView.class);

  @Before
  public void init(){
    taskName="new task";
    taskDescription="Some description blah blah";
    taskAssignee="FirstName LastName";
  }


  @Test
  public void createTask() throws Exception {
    CountingIdlingResource idlingResource = new CountingIdlingResource("home");
    Espresso.registerIdlingResources(idlingResource);

    Thread.sleep(1000);

    onView(
      allOf(withContentDescription("Open navigation drawer"),
        childAtPosition(
          allOf(withId(R.id.toolbar),
            childAtPosition(
              withClassName(is("android.support.design.widget.AppBarLayout")),
              0)),
          1),
        isDisplayed())).perform(click());

    onView(MenuMatcher.getNavigationItemWithString("Projects")).perform(click());

    CountingIdlingResource idlingResource1 = new CountingIdlingResource("project");
    Espresso.registerIdlingResources(idlingResource1);
    Thread.sleep(1000);
    onView(withId(R.id.project_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    onView(withId(R.id.task_set_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    onView(withId(R.id.task_add_task_fab)).perform(click());

    onView(withId(R.id.new_task_name)).perform(clearText(),typeText(taskName),closeSoftKeyboard());
    onView(withId(R.id.new_task_select_assignee_button)).perform(click());
    onView(allOf(withId(R.id.add_member_radio), childAtPosition(childAtPosition(withId(R.id.add_member_list), 0), 3), isDisplayed())).perform(click());

    onView(withText("Assign")).perform(click());
    onView(withId(R.id.new_task_description)).perform(clearText(),typeText(taskDescription),closeSoftKeyboard());
    onView(withText("Create")).perform(click());


//
//    final int[] numberOfAdapterItems = new int[1];
//    onView(withId(R.id.task_list)).check(matches(new TypeSafeMatcher<View>() {
//      @Override
//      public boolean matchesSafely(View view) {
//        RecyclerView listView = (RecyclerView) view;
//
//        //here we assume the adapter has been fully loaded already
//        numberOfAdapterItems[0] = listView.getAdapter().getItemCount();
//
//        return true;
//      }
//
//      @Override
//      public void describeTo(Description description) {
//
//      }
//    }));
    
    Thread.sleep(2000);

    onData(is(instanceOf(TaskSetRecyclerViewAdapter.ViewHolder.class)))
      .inAdapterView(allOf(withId(R.id.task_list), isDisplayed()))
      .atPosition(20).perform(scrollTo()).check(matches(hasDescendant(withText(taskName))));

    Thread.sleep(3000);

//    onView(withId(R.id.task_list))
//      .perform(RecyclerViewActions.scrollToPosition(numberOfAdapterItems[0] - 1))
//      .check(matches(RecylerMatcher.atPosition(numberOfAdapterItems[0] - 1,hasDescendant(withText(taskName)))));



  }
}
