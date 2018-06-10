package com.ux7.fullhyve.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.util.SortedList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ux7.fullhyve.R;
import com.ux7.fullhyve.ui.adapters.NotificationRecyclerViewAdapter;
import com.ux7.fullhyve.ui.data.ListNotification;
import com.ux7.fullhyve.ui.interfaces.OnHomeInteractionListener;

import java.util.ArrayList;

public class NotificationFragment extends Fragment {

    View fragmentView;

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

        RecyclerView recyclerView = (RecyclerView) fragmentView.findViewById(R.id.notification_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<ListNotification> nlist = new ArrayList<>();
        //nlist.add(ListMessage.getSpinnerValue());
        for (int i = 0; i < 20; i++) {
            ListNotification l = new ListNotification();
            nlist.add(l);
        }

        recyclerView.setAdapter(new NotificationRecyclerViewAdapter(nlist));
        recyclerView.getLayoutManager().scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
