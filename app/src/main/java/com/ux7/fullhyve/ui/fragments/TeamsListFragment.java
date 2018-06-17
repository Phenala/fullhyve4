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

import com.ux7.fullhyve.R;
import com.ux7.fullhyve.services.Handlers.AppHandler;
import com.ux7.fullhyve.ui.activities.HomeView;
import com.ux7.fullhyve.ui.adapters.TeamsRecyclerViewAdapter;
import com.ux7.fullhyve.ui.data.ListTeam;
import com.ux7.fullhyve.ui.interfaces.OnHomeInteractionListener;

import java.util.ArrayList;
import java.util.List;

public class TeamsListFragment extends Fragment implements HomeView.OnHomeSearchListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private OnHomeInteractionListener mListener;




    List<ListTeam> teams = new ArrayList<>();

    TeamsRecyclerViewAdapter adapter;
    LinearLayoutManager layoutManager;

    public TeamsListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TeamsListFragment newInstance(int columnCount) {
        TeamsListFragment fragment = new TeamsListFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_list, container, false);

        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view;

        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TeamsRecyclerViewAdapter(teams, mListener);
        recyclerView.setAdapter(adapter);

        getTeams();

        return view;
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

    public void getTeams() {

        Activity activity = getActivity();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                adapter.update();

            }
        };

        AppHandler.getInstance().teamHandler.getMyTeams(0, 500, teams,activity, runnable);

    }

    @Override
    public void onSearch(String s) {

        Activity activity = getActivity();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                adapter.update();

            }
        };

        AppHandler.getInstance().teamHandler.searchTeams(0, 500, s, teams,activity, runnable);

    }
}
