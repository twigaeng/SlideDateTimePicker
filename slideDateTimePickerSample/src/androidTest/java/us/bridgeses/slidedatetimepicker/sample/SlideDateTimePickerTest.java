package us.bridgeses.slidedatetimepicker.sample;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import us.bridgeses.slidedatetimepicker.SlideDateTimeDialogFragment;
import us.bridgeses.slidedatetimepicker.SlideDateTimeListener;
import us.bridgeses.slidedatetimepicker.SlideDateTimePicker;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withTagValue;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by tbrid on 7/18/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SlideDateTimePickerTest {

    @Rule
    public ActivityTestRule<SampleActivity> activityTestRule =
            new ActivityTestRule<>(SampleActivity.class);

    @Test
    public void testBasicPickerShown() throws Exception {
        SlideDateTimePicker picker =
                new SlideDateTimePicker.Builder(activityTestRule.getActivity().getSupportFragmentManager())
                        .setListener(new SlideDateTimeListener() {
                            @Override
                            public void onDateTimeSet(Date date) {

                            }
                        })
                .build();
        SampleActivity.ClickListener clickListener = new SampleActivity.ClickListener(picker);
        activityTestRule.getActivity().setListener(clickListener);
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.slidingTabLayout)).check(matches(isDisplayed()));
        onView(withText("OK")).check(matches(isDisplayed()));
        onView(withText("Cancel")).check(matches(isDisplayed()));
        onView(withText("None")).check(matches(not(isDisplayed())));
    }

    @Test
    public void testNonePickerShown() throws Exception {
        SlideDateTimePicker picker =
                new SlideDateTimePicker.Builder(activityTestRule.getActivity().getSupportFragmentManager())
                        .setListener(new SlideDateTimeListener() {
                            @Override
                            public void onDateTimeSet(Date date) {

                            }
                        })
                        .setHasNone(true)
                        .build();
        SampleActivity.ClickListener clickListener = new SampleActivity.ClickListener(picker);
        activityTestRule.getActivity().setListener(clickListener);
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.slidingTabLayout)).check(matches(isDisplayed()));
        onView(withText("OK")).check(matches(isDisplayed()));
        onView(withText("Cancel")).check(matches(isDisplayed()));
        onView(withText("None")).check(matches(isDisplayed()));
    }

    @Test
    public void testOkButton() throws Exception {
        SlideDateTimeListener listener = Mockito.mock(SlideDateTimeListener.class);
        SlideDateTimePicker picker =
                new SlideDateTimePicker.Builder(activityTestRule.getActivity().getSupportFragmentManager())
                        .setListener(listener)
                        .setHasNone(true)
                        .build();
        SampleActivity.ClickListener clickListener = new SampleActivity.ClickListener(picker);
        activityTestRule.getActivity().setListener(clickListener);
        onView(withId(R.id.button)).perform(click());
        onView(withText("OK")).perform(click());
        verify(listener, times(1)).onDateTimeSet((Date)any());
    }

    @Test
    public void testCancelButton() throws Exception {
        SlideDateTimeListener listener = Mockito.mock(SlideDateTimeListener.class);
        SlideDateTimePicker picker =
                new SlideDateTimePicker.Builder(activityTestRule.getActivity().getSupportFragmentManager())
                        .setListener(listener)
                        .setHasNone(true)
                        .build();
        SampleActivity.ClickListener clickListener = new SampleActivity.ClickListener(picker);
        activityTestRule.getActivity().setListener(clickListener);
        onView(withId(R.id.button)).perform(click());
        onView(withText("Cancel")).perform(click());
        verify(listener, times(1)).onDateTimeCancel();
    }

    @Test
    public void testNoneButton() throws Exception {
        SlideDateTimeListener listener = Mockito.mock(SlideDateTimeListener.class);
        SlideDateTimePicker picker =
                new SlideDateTimePicker.Builder(activityTestRule.getActivity().getSupportFragmentManager())
                        .setListener(listener)
                        .setHasNone(true)
                        .build();
        SampleActivity.ClickListener clickListener = new SampleActivity.ClickListener(picker);
        activityTestRule.getActivity().setListener(clickListener);
        onView(withId(R.id.button)).perform(click());
        onView(withText("None")).perform(click());
        verify(listener, times(1)).onDateTimeNone();
    }

    @Test
    public void testDateReturned() throws Exception {
        SlideDateTimeListener listener = Mockito.mock(SlideDateTimeListener.class);
        SlideDateTimePicker picker =
                new SlideDateTimePicker.Builder(activityTestRule.getActivity().getSupportFragmentManager())
                        .setListener(listener)
                        .setHasNone(true)
                        .build();
        SampleActivity.ClickListener clickListener = new SampleActivity.ClickListener(picker);
        activityTestRule.getActivity().setListener(clickListener);
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.datePicker)).perform(PickerActions.setDate(2010, 3, 15));
        ArgumentCaptor<Date> argumentCaptor = ArgumentCaptor.forClass(Date.class);
        onView(withText("OK")).perform(click());
        verify(listener).onDateTimeSet(argumentCaptor.capture());
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(argumentCaptor.getValue());
        assertEquals(2010, calendar.get(Calendar.YEAR));
        assertEquals(2, calendar.get(Calendar.MONTH));
        assertEquals(15, calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testTimeReturned() throws Exception {
        SlideDateTimeListener listener = Mockito.mock(SlideDateTimeListener.class);
        SlideDateTimePicker picker =
                new SlideDateTimePicker.Builder(activityTestRule.getActivity().getSupportFragmentManager())
                        .setListener(listener)
                        .setHasNone(true)
                        .build();
        SampleActivity.ClickListener clickListener = new SampleActivity.ClickListener(picker);
        activityTestRule.getActivity().setListener(clickListener);
        onView(withId(R.id.button)).perform(click());
        onView(withTagValue(Matchers.equalTo((Object)1))).perform(click());
        onView(withId(R.id.timePicker)).perform(PickerActions.setTime(11, 22));
        ArgumentCaptor<Date> argumentCaptor = ArgumentCaptor.forClass(Date.class);
        onView(withText("OK")).perform(click());
        verify(listener).onDateTimeSet(argumentCaptor.capture());
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(argumentCaptor.getValue());
        assertEquals(11, calendar.get(Calendar.HOUR));
        assertEquals(22, calendar.get(Calendar.MINUTE));
    }

    @Test
    public void testTabs() throws Exception {
        SlideDateTimeListener listener = Mockito.mock(SlideDateTimeListener.class);
        SlideDateTimePicker picker =
                new SlideDateTimePicker.Builder(activityTestRule.getActivity().getSupportFragmentManager())
                        .setListener(listener)
                        .setHasNone(true)
                        .build();
        SampleActivity.ClickListener clickListener = new SampleActivity.ClickListener(picker);
        activityTestRule.getActivity().setListener(clickListener);
        onView(withId(R.id.button)).perform(click());
        onView(withTagValue(Matchers.equalTo((Object)1))).perform(click());
        onView(withId(R.id.timePicker)).check(matches(isDisplayed()));
        onView(withId(R.id.datePicker)).check(matches(not(isDisplayed())));
        onView(withTagValue(Matchers.equalTo((Object)0))).perform(click());
        onView(withId(R.id.timePicker)).check(matches(not(isDisplayed())));
        onView(withId(R.id.datePicker)).check(matches(isDisplayed()));
    }

    @Test
    public void testSwipe() throws Exception {
        FragmentManager fm = activityTestRule.getActivity().getSupportFragmentManager();
        SlideDateTimeListener listener = Mockito.mock(SlideDateTimeListener.class);
        SlideDateTimePicker picker =
                new SlideDateTimePicker.Builder(fm)
                        .setListener(listener)
                        .setHasNone(true)
                        .build();
        SampleActivity.ClickListener clickListener = new SampleActivity.ClickListener(picker);
        activityTestRule.getActivity().setListener(clickListener);

        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.viewPager)).check(matches(isDisplayed()));

        Fragment fragment = fm.findFragmentByTag(SlideDateTimeDialogFragment.TAG_SLIDE_DATE_TIME_DIALOG_FRAGMENT);
        Espresso.registerIdlingResources(new ViewPagerIdlingResource(
                (ViewPager) fragment.getView().findViewById(R.id.viewPager), "ViewPager")
        );
        onView(withId(R.id.datePicker)).perform(swipeLeft());
        onView(withId(R.id.timePicker)).check(matches(isDisplayed()));
        onView(withId(R.id.datePicker)).check(matches(not(isDisplayed())));
        onView(withId(R.id.timePicker)).perform(swipeRight());
        onView(withId(R.id.timePicker)).check(matches(not(isDisplayed())));
        onView(withId(R.id.datePicker)).check(matches(isDisplayed()));
    }

    @Test
    public void testSetColorDoesntCrash() throws Exception {
        SlideDateTimePicker picker =
                new SlideDateTimePicker.Builder(activityTestRule.getActivity().getSupportFragmentManager())
                        .setListener(new SlideDateTimeListener() {
                            @Override
                            public void onDateTimeSet(Date date) {

                            }
                        })
                        .build();
        SampleActivity.ClickListener clickListener = new SampleActivity.ClickListener(picker);
        activityTestRule.getActivity().setListener(clickListener);
        onView(withId(R.id.button)).perform(click());
        picker.setIndicatorColor(Color.WHITE);
        assertEquals(1,1);
    }

    @Test
    public void testRotateDoesntCrash() throws Exception {
        SlideDateTimePicker picker =
                new SlideDateTimePicker.Builder(activityTestRule.getActivity().getSupportFragmentManager())
                        .setListener(new SlideDateTimeListener() {
                            @Override
                            public void onDateTimeSet(Date date) {

                            }
                        })
                        .build();
        SampleActivity.ClickListener clickListener = new SampleActivity.ClickListener(picker);
        activityTestRule.getActivity().setListener(clickListener);
        onView(withId(R.id.button)).perform(click());
        activityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        onView(withId(R.id.slidingTabLayout)).check(matches(isDisplayed()));
        activityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        onView(withId(R.id.slidingTabLayout)).check(matches(isDisplayed()));
        activityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        onView(withId(R.id.slidingTabLayout)).check(matches(isDisplayed()));
        activityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
        onView(withId(R.id.slidingTabLayout)).check(matches(isDisplayed()));
        assertEquals(1,1);
    }
}
