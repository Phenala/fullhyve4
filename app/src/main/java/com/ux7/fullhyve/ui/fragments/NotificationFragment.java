package com.ux7.fullhyve.ui.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.util.SortedList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Cache;
import com.squareup.picasso.Picasso;
import com.ux7.fullhyve.R;
import com.ux7.fullhyve.services.Handlers.AppHandler;
import com.ux7.fullhyve.ui.adapters.NotificationRecyclerViewAdapter;
import com.ux7.fullhyve.ui.data.ListNotification;
import com.ux7.fullhyve.ui.interfaces.OnHomeInteractionListener;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment implements NotificationRecyclerViewAdapter.OnNotificationRecyclerInteractionListener {


    List<ListNotification> notificationList = new ArrayList<>();

    View fragmentView;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    NotificationRecyclerViewAdapter adapter;

    private OnHomeInteractionListener mListener;

    public NotificationFragment() {
        // Required empty public constructor
    }


    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_notification, container, false);
        buildRecyclerView();
        return fragmentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    public void buildRecyclerView() {

        recyclerView = (RecyclerView) fragmentView.findViewById(R.id.notification_list);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NotificationRecyclerViewAdapter(notificationList, getActivity(), this);
        recyclerView.setAdapter(adapter);

        getNotifications();

    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void getNotifications() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                adapter.update();

            }
        };

        AppHandler.getInstance().loginHandler.getNotifications(0, 500, notificationList, getActivity(), runnable);

    }

    @Override
    public void onInteractNotification(ListNotification notification) {

        getNotifications();

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
}
