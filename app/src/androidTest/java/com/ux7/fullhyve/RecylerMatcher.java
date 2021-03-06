package com.ux7.fullhyve;

import android.support.annotation.NonNull;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class RecylerMatcher {


  public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
    return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
      @Override
      public void describeTo(Description description) {
        description.appendText("has item at position " + position + ": ");
        itemMatcher.describeTo(description);
      }

      @Override
      protected boolean matchesSafely(final RecyclerView view) {
        RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
        if (viewHolder == null) {
          // has no item on such position
          return false;
        }
        return itemMatcher.matches(viewHolder.itemView);
      }
    };
  }

}
