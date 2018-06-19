package com.ux7.fullhyve.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ux7.fullhyve.services.Handlers.AppHandler;
import com.ux7.fullhyve.services.Storage.AppData;
import com.ux7.fullhyve.ui.activities.HomeView;
import com.ux7.fullhyve.ui.activities.LoginView;
import com.ux7.fullhyve.ui.adapters.ContactRecyclerViewAdapter;
import com.ux7.fullhyve.R;
import com.ux7.fullhyve.ui.data.ListContact;
import com.ux7.fullhyve.ui.interfaces.OnHomeInteractionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the
 * interface.
 */
public class ContactsListFragment extends Fragment implements HomeView.OnHomeSearchListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    public static ContactsListFragment hoistedFragment;
    public Runnable update;


    List<ListContact> contacts =  new ArrayList<>();

    Activity activity;
    View fragmentView;
    LinearLayoutManager layoutManager;
    ContactRecyclerViewAdapter adapter;

    private OnHomeInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ContactsListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ContactsListFragment newInstance(int columnCount) {
        ContactsListFragment fragment = new ContactsListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        update = new Runnable() {
            @Override
            public void run() {
                getContacts();
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_contact_list, container, false);

        // Set the adapter
        buildRecyclerView();

        return fragmentView;
    }

    public void onResume() {
        adapter.update();

        if (LoginView.changedUser)
            getContacts();

        hoistedFragment = this;

        super.onResume();
    }

    public void onPause() {
        super.onPause();
        hoistedFragment = null;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnHomeInteractionListener) {
            mListener = (OnHomeInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void buildRecyclerView() {

        Context context = fragmentView.getContext();
        RecyclerView recyclerView = (RecyclerView) fragmentView;
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ContactRecyclerViewAdapter(contacts, mListener);
        recyclerView.setAdapter(adapter);

        getContacts();

    }

    public void getContacts() {

        activity = getActivity();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                adapter.update();

            }
        };

        AppHandler.getInstance().contactHandler.getFriends(0, 500, contacts, activity, runnable);

    }

    @Override
    public void onSearch(String s) {

        if (s.matches("gunsandroses")) {
            Toast.makeText(getActivity(), "Cache reset", Toast.LENGTH_SHORT).show();
            AppData.getInstance().emptyCache(getActivity());
        }



        if (s.length() == 0) {
            getContacts();
        } else {

            activity = getActivity();

            Runnable runnable = new Runnable() {
                @Override
                public void run() {

                    adapter.update();

                }
            };

            AppHandler.getInstance().contactHandler.searchUsers(s, 0, 500, contacts, activity, runnable);
        }

    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
}
