package com.ux7.fullhyve.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.ux7.fullhyve.R;
import com.ux7.fullhyve.services.Handlers.AppHandler;
import com.ux7.fullhyve.services.Models.Identity;
import com.ux7.fullhyve.services.Storage.AppData;
import com.ux7.fullhyve.ui.adapters.AnnouncementRecyclerViewAdapter;
import com.ux7.fullhyve.ui.data.ListAnnouncement;
import com.ux7.fullhyve.ui.data.ListTeam;

import java.util.ArrayList;
import java.util.List;


public class AnnouncementsFragment extends Fragment implements AnnouncementRecyclerViewAdapter.OnAnnouncmentRecyclerInteractionListener {

    View fragmentView;
    public ListTeam team;
    List<ListAnnouncement> announcements = new ArrayList<>();
    int retrieveLimit = 7;
    int size = retrieveLimit;
    int announcementEditingId;
    boolean fetchAnnouncements = false;

    LinearLayoutManager layoutManager;
    AnnouncementRecyclerViewAdapter adapter;
    ImageButton sendButton;
    EditText announcementToSend;



    private OnFragmentInteractionListener mListener;

    public AnnouncementsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnnouncementsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnnouncementsFragment newInstance(String param1, String param2) {
        AnnouncementsFragment fragment = new AnnouncementsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void buildAnnouncements() {

        RecyclerView recyclerView = (RecyclerView) fragmentView.findViewById(R.id.announcements_view);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AnnouncementRecyclerViewAdapter(announcements, team, this);
        recyclerView.setAdapter(adapter);

        getAnnouncements();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (layoutManager.findLastVisibleItemPosition() == announcements.size() - 1 && dy != 0) {

                    size += retrieveLimit;

                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            getAnnouncements();
                        }
                    });

                }
            }
        });
    }

    public void buildViews() {

        sendButton = (ImageButton) fragmentView.findViewById(R.id.announcement_send_button);
        announcementToSend = (EditText) fragmentView.findViewById(R.id.announcement_to_send);
        announcementToSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                enterAnnouncement();

            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void getAnnouncements() {

        if (!fetchAnnouncements) {

            fetchAnnouncements = true;

            Activity activity = getActivity();

            Runnable runnable = new Runnable() {
                @Override
                public void run() {

                    adapter.update();

                    fetchAnnouncements = false;

                }
            };

            AppHandler.getInstance().teamHandler.getTeamAnnouncements(team.id, 0, size, announcements, activity, runnable);

        }

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_tab_announcement, container, false);

        buildAnnouncements();
        buildViews();

        return fragmentView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onEditAnnouncement(ListAnnouncement announcement) {

        announcementToSend.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        announcementEditingId = announcement.id;
        announcementToSend.setText(announcement.message);
        sendButton.setImageResource(R.drawable.ic_tick_icon);

    }

    public void enterAnnouncement() {

        if (announcementEditingId == -1) {
            sendAnnouncement();
        } else {
            editAnnouncement();
        }

    }

    @Override
    public void onDeleteAnnouncement(ListAnnouncement announcement) {

        Activity activity = getActivity();

        announcements.remove(announcement);

        adapter.update();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                getAnnouncements();

            }
        };

        AppHandler.getInstance().teamHandler.deleteAnnouncement(announcement.id, activity, runnable);

    }

    public void editAnnouncement() {

        Activity activity = getActivity();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                getAnnouncements();

            }
        };

        AppHandler.getInstance().teamHandler.editAnnouncementReply(announcementEditingId, announcementToSend.getText().toString(), activity, runnable);

        sendButton.setImageResource(R.drawable.ic_send_icon);

        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(announcementToSend.getWindowToken(), 0);

    }

    public void sendAnnouncement() {

        String announcement = announcementToSend.getText().toString();

        ListAnnouncement tempAnnouncement = new ListAnnouncement();
        Identity identity = AppData.getCache().getIdentity();
        tempAnnouncement.senderId = identity.getId();
        tempAnnouncement.sent = true;
        tempAnnouncement.senderName = identity.getName();
        tempAnnouncement.message = announcement;
        tempAnnouncement.id = -1;

        announcements.add(0, tempAnnouncement);

        adapter.update();

        Activity activity = getActivity();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                getAnnouncements();

            }
        };

        AppHandler.getInstance().teamHandler.announce(team.id, announcement, activity, runnable);

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
