package com.marocks.todo;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SplashTest {

    @Rule
    public ActivityTestRule<Splash> mActivityTestRule = new ActivityTestRule<>(Splash.class);

    @Test
    public void splashTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.etEmail_login), isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.etEmail_login), isDisplayed()));
        appCompatEditText2.perform(replaceText("rohan@test.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.etPwd_login), isDisplayed()));
        appCompatEditText3.perform(replaceText("temp123"), closeSoftKeyboard());




        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.etDomain), isDisplayed()));
        appCompatEditText7.perform(replaceText("rohan"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                withText("Sign In"));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.todoList),
                        withParent(allOf(withId(R.id.swipe),
                                withParent(withId(R.id.coordinatorLayout)))),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btn_save), withText("Save")));
        appCompatButton2.perform(scrollTo(), click());

    }

}
