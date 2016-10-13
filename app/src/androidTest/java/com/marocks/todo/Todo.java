package com.marocks.todo;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.marocks.todo.ui.LoginView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Todo {

    @Rule
    public ActivityTestRule<LoginView> mActivityTestRule = new ActivityTestRule<>(LoginView.class);

    @Test
    public void todo() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.etEmail_login), isDisplayed()));
        appCompatEditText.perform(replaceText("rohan@test.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.etPwd_login), isDisplayed()));
        appCompatEditText2.perform(replaceText("temp123"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.etDomain), isDisplayed()));
        appCompatEditText3.perform(replaceText("rohan"), closeSoftKeyboard());


        ViewInteraction appCompatButton9 = onView(
                withText("Sign In"));
        appCompatButton9.perform(scrollTo(), click());

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab),
                        withParent(allOf(withId(R.id.coordinatorLayout),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                withId(R.id.note));
        appCompatEditText6.perform(scrollTo(), click());

        ViewInteraction appCompatEditText7 = onView(
                withId(R.id.note));
        appCompatEditText7.perform(scrollTo(), replaceText("hhsshhhffd"), closeSoftKeyboard());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.date), withText("2016-10-13")));
        appCompatEditText8.perform(scrollTo(), click());



        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.weekly), withText("Weekly"),
                        withParent(withId(R.id.radioGrp))));
        appCompatRadioButton.perform(scrollTo(), click());

        ViewInteraction appCompatCheckBox = onView(
                allOf(withId(R.id.mon), withText("M"),
                        withParent(withId(R.id.weekly_layout))));
        appCompatCheckBox.perform(scrollTo(), click());

        ViewInteraction appCompatCheckBox2 = onView(
                allOf(withId(R.id.tus), withText("T"),
                        withParent(withId(R.id.weekly_layout))));
        appCompatCheckBox2.perform(scrollTo(), click());

        ViewInteraction appCompatCheckBox3 = onView(
                allOf(withId(R.id.wen), withText("W"),
                        withParent(withId(R.id.weekly_layout))));
        appCompatCheckBox3.perform(scrollTo(), click());

        ViewInteraction appCompatButton11 = onView(
                allOf(withId(R.id.btn_save), withText("Save")));
        appCompatButton11.perform(scrollTo(), click());



        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.logout), withContentDescription("Help"), isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction appCompatButton13 = onView(
                allOf(withId(android.R.id.button2), withText("No"),
                        withParent(allOf(withId(R.id.buttonPanel),
                                withParent(withId(R.id.parentPanel)))),
                        isDisplayed()));
        appCompatButton13.perform(click());

    }



}
