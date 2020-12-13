package com.oc.mareu.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.oc.mareu.R;
import com.oc.mareu.model.Meeting;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingRecyclerViewAdapter.MeetingViewHolder> {

    private final List<Meeting> mMeetings;

    public MeetingRecyclerViewAdapter(List<Meeting> meetings) {
        this.mMeetings = meetings;
    }

    @Override
    public MeetingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meeting_item, parent, false);
        return new MeetingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MeetingViewHolder holder, int position) {
        Meeting meeting = mMeetings.get(position);
        String meetingDescription = meeting.getTopic() + " - " + meeting.getTime() + " - " + meeting.getMeetingRooms().getDisplayedName();

        holder.mMeetingRoomNumber.setColorFilter(mMeetings.get(position).getMeetingRooms().getBackgroundColor());
        holder.mMeetingDescription.setText(meetingDescription);
        holder.mMeetingMails.setText(meeting.getEmails());

        holder.mDeleteMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMeetings.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mMeetings.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    public class MeetingViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.room_imageview)
        public ImageView mMeetingRoomNumber;
        @BindView(R.id.meeting_description)
        public TextView mMeetingDescription;
        @BindView(R.id.meeting_mails)
        public TextView mMeetingMails;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteMeeting;

        public MeetingViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
