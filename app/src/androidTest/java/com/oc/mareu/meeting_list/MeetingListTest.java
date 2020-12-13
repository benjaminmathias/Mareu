package com.oc.mareu.meeting_list;

import android.widget.DatePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.oc.mareu.DI.DI;
import com.oc.mareu.R;
import com.oc.mareu.service.DummyMeetingGenerator;
import com.oc.mareu.service.MeetingApiService;
import com.oc.mareu.ui.MainActivity;
import com.oc.mareu.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.oc.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;

import static org.junit.Assert.*;

/**
 * Test class for list of meetings
 */
@RunWith(AndroidJUnit4.class)
public class MeetingListTest {

    // Number of item in our RecyclerView
    private static int ITEMS_COUNT = 4;

    private MainActivity mActivity;
    MeetingApiService mApiService;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule(MainActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityTestRule.getActivity();
        assertThat(mActivity, notNullValue());

        mApiService = DI.getMeetingService();
        mApiService.getMeetings().clear();
        mApiService.getMeetings().add(DummyMeetingGenerator.DUMMY_MEETING.get(0));
        mApiService.getMeetings().add(DummyMeetingGenerator.DUMMY_MEETING.get(1));
        mApiService.getMeetings().add(DummyMeetingGenerator.DUMMY_MEETING.get(2));
        mApiService.getMeetings().add(DummyMeetingGenerator.DUMMY_MEETING.get(3));
    }

    /**
     * We ensure that our recyclerview is displaying at least one item
     */
    @Test
    public void myMeetingList_shouldBeEmpty() {
        onView(withId(R.id.recycler_view))
                .check(matches(ViewMatchers.hasMinimumChildCount(0)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myMeetingList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 1
        onView(withId(R.id.recycler_view)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));
        // Then : the number of element is 3
        onView(withId(R.id.recycler_view)).check(withItemCount(ITEMS_COUNT - 1));
    }

    /**
     * When we click on the FAB, open the AddMeetingActivity
     */
    @Test
    public void myMainActivityFAB_clickAction_shouldOpenAddMeetingActivity() {
        // When perform click on a element
        onView(withId(R.id.add_meeting))
                .perform(click());
        // Then : open the profile activity
        onView(withId(R.id.add_meeting_activity)).check(matches(isDisplayed()));
    }

    /**
     * We ensure that our RecyclerView display newly added meetings
     */
    @Test
    public void checkIf_myMeetingList_displayAddedMeetings() {
        // Given : we add 1 element to the list
        onView(withId(R.id.add_meeting)).perform(click());
        // When add meeting activity is opened
        onView(withId(R.id.topic_edit_text)).perform(replaceText("test"));
        onView(withId(R.id.mail_edit_text)).perform(replaceText("test"));
        // We confirm the meeting
        onView(withId(R.id.confirm_button)).perform(click());
        // We access the AlertDialog to confirm
        onView(withText("Confirmer réunion")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());
        // Then : the number of item in our list is 5
        onView(withId(R.id.recycler_view)).check(withItemCount(ITEMS_COUNT + 1));
    }

    /**
     * We ensure that our RecyclerView dateFilter works
     */
    @Test
    public void myMeetingList_clickToolbarAction_shouldDisplayDateFilteredList() {
        // Given : check that we have 4 initial meetings
        onView(withId(R.id.recycler_view)).check(withItemCount(ITEMS_COUNT));
        // We add a new meeting to a different date
        onView(withId(R.id.add_meeting)).perform(click());
        // When add meeting activity is opened
        onView(withId(R.id.topic_edit_text)).perform(replaceText("test"));
        onView(withId(R.id.mail_edit_text)).perform(replaceText("test"));
        onView(withId(R.id.datePicker1)).perform(PickerActions.setDate(2020, 12, 20));
        // We confirm the meeting
        onView(withId(R.id.confirm_button)).perform(click());
        // We access the AlertDialog to confirm
        onView(withText("Confirmer réunion")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());
        // We check that our meeting is added
        onView(withId(R.id.recycler_view)).check(withItemCount(ITEMS_COUNT + 1));
        // When : we click on the toolbar and select a date
        onView(withId(R.id.filter_item)).perform(click());
        onView(ViewMatchers.withText("Filtrer par date")).perform(click());
        onView(isAssignableFrom(DatePicker.class)).perform(PickerActions.setDate(2020, 12, 20));
        onView(withId(android.R.id.button1)).perform(click());
        // Then : We only have the filtered meeting
        onView(withId(R.id.recycler_view)).check(withItemCount(1));
    }

    /**
     * We ensure that our RecyclerView roomFilter works
     */
    @Test
    public void myMeetingList_clickToolbarAction_shouldDisplayRoomFilteredList() {
        // Given : check that we have 4 initial meetings with 4 different rooms
        onView(withId(R.id.recycler_view)).check(withItemCount(ITEMS_COUNT));
        // When : we click on the toolbar to filter by meeting room
        onView(withId(R.id.filter_item)).perform(click());
        onView(ViewMatchers.withText("Filtrer par lieu")).perform(click());
        // We select a meeting room
        onView(withId(R.id.meeting_room_filter_spinner)).perform(click());
        onView(withText("Salle 2")).inRoot(RootMatchers.isPlatformPopup()).perform(click());
        onView(withText("Filtrer")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());
        // Then : The RecyclerView only contains one item
        onView(withId(R.id.recycler_view)).check(withItemCount(1));
    }

    /**
     * We ensure that we can remove filters
     */
    @Test
    public void myMeetingList_clickToolbarAction_shouldDisplayNoFilteredList() {
        // Given : check that we have 4 initial meetings with 4 different rooms
        onView(withId(R.id.recycler_view)).check(withItemCount(ITEMS_COUNT));
        // When : we click on the toolbar to filter by meeting room
        onView(withId(R.id.filter_item)).perform(click());
        onView(ViewMatchers.withText("Filtrer par lieu")).perform(click());
        // We select a meeting room
        onView(withId(R.id.meeting_room_filter_spinner)).perform(click());
        onView(withText("Salle 2")).inRoot(RootMatchers.isPlatformPopup()).perform(click());
        onView(withText("Filtrer")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());
        // Then : The RecyclerView only contains one item
        onView(withId(R.id.recycler_view)).check(withItemCount(1));
        // When : we click on the toolbar to remove the filter
        onView(withId(R.id.filter_item)).perform(click());
        onView(ViewMatchers.withText("Retirer le filtre")).perform(click());
        // Then : Our RecyclerView contain all items
        onView(withId(R.id.recycler_view)).check(withItemCount(ITEMS_COUNT));
    }

}