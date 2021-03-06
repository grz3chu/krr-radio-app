package com.devnull.radio.tests;

import android.content.res.Configuration;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.filters.SdkSuppress;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;

import com.devnull.radio.tests.utils.OrientationChangeAction;
import com.devnull.radio.tests.utils.TestUtils;
import com.devnull.radio.tests.utils.conditionwatcher.ConditionWatcher;
import com.devnull.radio.tests.utils.conditionwatcher.IsMusicPlayingCondition;
import com.devnull.radio.ActivityMain;
import com.devnull.radio.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class UIRotationTest {

    @Rule
    public ActivityTestRule<ActivityMain> activityRule
            = new ActivityTestRule<>(ActivityMain.class);

    @Before
    public void setUp() {
        TestUtils.populateFavourites(ApplicationProvider.getApplicationContext(), 5);
        TestUtils.populateHistory(ApplicationProvider.getApplicationContext(), 5);
    }

    private ViewInteraction getPlayButton() {
        return onView(allOf(withId(R.id.buttonPlay), isDisplayingAtLeast(80)));
    }

    @Test
    public void stationsFragment_ShouldNotCrash_WhenScreenRotated() {
        onView(isRoot()).perform(OrientationChangeAction.orientationLandscape());
    }

    @Test
    public void historyFragment_ShouldNotCrash_WhenScreenRotated() {
        onView(ViewMatchers.withId(R.id.nav_item_history)).perform(ViewActions.click());
        onView(isRoot()).perform(OrientationChangeAction.orientationLandscape());
    }

    @Test
    public void favouritesFragment_ShouldNotCrash_WhenScreenRotated() {
        onView(withId(R.id.nav_item_starred)).perform(ViewActions.click());
        onView(isRoot()).perform(OrientationChangeAction.orientationLandscape());
    }

    @Test
    public void settingsFragment_ShouldNotCrash_WhenScreenRotated() {
        onView(withId(R.id.nav_item_starred)).perform(ViewActions.click());
        onView(isRoot()).perform(OrientationChangeAction.orientationLandscape());
    }

    @Test
    @SdkSuppress(minSdkVersion = 23)
    public void playback_ShouldNotStop_WhenScreenRotated() {
        getPlayButton().perform(ViewActions.click());

        ConditionWatcher.waitForCondition(new IsMusicPlayingCondition(true), ConditionWatcher.SHORT_WAIT_POLICY);

        int orientation = activityRule.getActivity().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            onView(isRoot()).perform(OrientationChangeAction.orientationLandscape());
        } else {
            onView(isRoot()).perform(OrientationChangeAction.orientationPortrait());
        }

        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        uiDevice.openNotification();
        TestUtils.expectRunningNotification(uiDevice);

        ConditionWatcher.waitForCondition(new IsMusicPlayingCondition(true), ConditionWatcher.SHORT_WAIT_POLICY);
    }
}
