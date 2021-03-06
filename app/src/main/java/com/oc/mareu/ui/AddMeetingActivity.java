package com.oc.mareu.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.oc.mareu.DI.DI;
import com.oc.mareu.R;
import com.oc.mareu.model.Meeting;
import com.oc.mareu.model.MeetingRoom;
import com.oc.mareu.service.MeetingApiService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddMeetingActivity extends AppCompatActivity {

    @BindView(R.id.topic_edit_text)
    EditText topicEditText;
    @BindView(R.id.room_spinner)
    Spinner mSpinner;
    @BindView(R.id.datePicker1)
    DatePicker mDatePicker;
    @BindView(R.id.timePicker1)
    TimePicker mTimePicker;

    @BindView(R.id.add_meeting_textview)
    TextView addMeetingTextView;
    @BindView(R.id.add_ppl_img)
    ImageButton addMailImageButton;
    @BindView(R.id.add_ppl_img2)
    ImageButton addMailImageButton2;
    @BindView(R.id.mail_edit_text)
    EditText mailEditText;
    @BindView(R.id.mail_edit_text2)
    EditText mailEditText2;
    @BindView(R.id.mail_edit_text3)
    EditText mailEditText3;

    private final MeetingApiService mMeetingApiService = DI.getMeetingService();
    List<MeetingRoom> meetingRooms = mMeetingApiService.getMeetingRooms();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        ButterKnife.bind(this);

        setupPicker();
        setupSpinner();
    }

    // Initialize pickers to current time and date
    public void setupPicker() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        mTimePicker.setCurrentHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        mTimePicker.setIs24HourView(true);
        mDatePicker.init(year, month, day, null);
    }

    // Get DatePicker values
    public String retrieveDate() {
        int day  = mDatePicker.getDayOfMonth();
        int month= mDatePicker.getMonth();
        int year = mDatePicker.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(calendar.getTime());
    }

    // Get TimePicker values
    public String retrieveTime() {
        String hour = mTimePicker.getCurrentHour().toString();
        if(mTimePicker.getCurrentHour() < 10 ){
            hour = "0" + hour;
        }

        String minute = mTimePicker.getCurrentMinute().toString();
        if (mTimePicker.getCurrentMinute() < 10){
            minute = "0" + minute;
        }

        String time = hour + ":" + minute;
        return time;
    }

    // Build a String containing mails
    public String mailString(){

        String mailString2 = "";
        String mailString3 = "";

        String mailString1 = mailEditText.getText().toString();
        if (mailEditText2.isShown()){
            mailString2 = ", " + mailEditText2.getText().toString();
        }
        if (mailEditText3.isShown()){
            mailString3 =", " + mailEditText3.getText().toString();
        }

        String mailString = mailString1 + mailString2 + mailString3;
        return mailString;
    }

    // Setup MeetingRoom spinner
    public void setupSpinner(){
        ArrayAdapter roomAdapter = new ArrayAdapter(this, R.layout.spinner , meetingRooms);
        mSpinner.setAdapter(roomAdapter);
    }

    // Open an AlertDialog
    @OnClick(R.id.confirm_button)
    void getNewMeetingData() {
        if(!(mailEditText.getText().toString().isEmpty()) && !(topicEditText.getText().toString().isEmpty())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddMeetingActivity.this);
            builder.setTitle("Information de réunion")
                    .setMessage("Objet de la réunion : " + topicEditText.getText().toString() +
                            "\nDate : " + retrieveDate() +
                            "\nHeure : " + retrieveTime() +
                            "\nLieu de réunion : " + meetingRooms.get(mSpinner.getSelectedItemPosition()) +
                            "\nParticipants : " + mailString()
                    )
                    .setCancelable(false)
                    .setPositiveButton("Confirmer réunion", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            addMeeting();
                            Log.d("Meeting", mMeetingApiService.getMeetings().toString());
                        }
                    })
                    .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(AddMeetingActivity.this, "Réunion non confirmée", Toast.LENGTH_SHORT).show();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(Color.WHITE);
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundColor(Color.WHITE);
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        } else {
            Toast.makeText(this, "Veuillez renseigner toutes les informations", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.add_ppl_img)
    void displayEditText2(){
        mailEditText2.setVisibility(View.VISIBLE);
        addMailImageButton2.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.add_ppl_img2)
    void displayEditText3(){
        mailEditText3.setVisibility(View.VISIBLE);
    }

    public static void navigate(Activity activity) {
        Intent intent = new Intent(activity, AddMeetingActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    void addMeeting(){
        Meeting meeting = new Meeting(
                retrieveTime(),
                retrieveDate(),
                topicEditText.getText().toString(),
                mailString(),
                meetingRooms.get(mSpinner.getSelectedItemPosition())
        );
        mMeetingApiService.addMeeting(meeting);
        finish();
    }
}