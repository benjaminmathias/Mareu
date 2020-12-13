package com.oc.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.oc.mareu.DI.DI;
import com.oc.mareu.R;
import com.oc.mareu.model.Meeting;
import com.oc.mareu.model.MeetingRoom;
import com.oc.mareu.service.MeetingApiService;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private List<Meeting> mMeetings;
    private List<MeetingRoom> mMeetingRooms;
    private MeetingApiService mMeetingApiService;
    private MeetingRecyclerViewAdapter mAdapter;

    private String dateString;
    private DatePickerDialog.OnDateSetListener mOnDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mMeetingApiService = DI.getMeetingService();
        setupRecyclerView();

        mOnDateSetListener = setupDatePickerDialog();
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    private void setupRecyclerView() {
        mMeetings = mMeetingApiService.getMeetings();
        mAdapter = new MeetingRecyclerViewAdapter(this.mMeetings);
        mRecyclerView.setAdapter(this.mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.add_meeting)
    public void addMeeting(View view) {
        AddMeetingActivity.navigate(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.date_filter_menu:
                mOnDateSetListener = setupDatePickerDialog();
                setupPicker();
                return true;
            case R.id.meetingroom_filter_menu:
                setupMeetingRoomFilter();
                return true;
            case R.id.remove_filter_menu:
                setupRecyclerView();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupPicker() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, mOnDateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener setupDatePickerDialog() {
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String dayString = String.valueOf(dayOfMonth);
                String monthString = String.valueOf(month + 1);

                if (dayOfMonth < 10) {
                    dayString = "0" + dayOfMonth;
                }
                if (month + 1 < 10) {
                    monthString = "0" + (month + 1);
                }

                dateString = dayString + "-" + monthString + "-" + year;

                mMeetings = mMeetingApiService.dateFilter(dateString);
                mAdapter = new MeetingRecyclerViewAdapter(mMeetings);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        };

        return onDateSetListener;
    }


    private void setupMeetingRoomFilter() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.meetingroom_filter_dialog, null);
        Spinner mSpinner = dialogView.findViewById(R.id.meeting_room_filter_spinner);

        mMeetingRooms = mMeetingApiService.getMeetingRooms();

        ArrayAdapter roomAdapter = new ArrayAdapter(this, R.layout.spinner, mMeetingRooms);
        mSpinner.setAdapter(roomAdapter);
        builder.setView(dialogView)
                .setTitle("Sélectionnez une salle de réunion :")
                .setPositiveButton("Filtrer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        MeetingRoom selectedRoom = mMeetingRooms.get(mSpinner.getSelectedItemPosition());
                        mMeetings = mMeetingApiService.meetingRoomFilter(selectedRoom);
                        mAdapter = new MeetingRecyclerViewAdapter(mMeetings);
                        mRecyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(Color.WHITE);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundColor(Color.WHITE);
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
    }

}