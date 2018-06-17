package com.ux7.fullhyve.ui.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ux7.fullhyve.R;
import com.ux7.fullhyve.services.Handlers.AppHandler;
import com.ux7.fullhyve.ui.adapters.ProjectsRecyclerViewAdapter;
import com.ux7.fullhyve.ui.data.ListMember;
import com.ux7.fullhyve.ui.data.ListProject;
import com.ux7.fullhyve.ui.data.ListTeam;
import com.ux7.fullhyve.ui.interfaces.OnHomeInteractionListener;

import java.util.ArrayList;
import java.util.List;


public class TeamProjectsFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnHomeInteractionListener mListener;

    ListTeam team;
    List<ListProject> projects = new ArrayList<>();

    View fragmentView;
    LinearLayoutManager layoutManager;
    ProjectsRecyclerViewAdapter adapter;


    public TeamProjectsFragment() {
    }

    public void setTeam(ListTeam listTeam) {
        team = listTeam;
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TeamProjectsFragment newInstance(int columnCount) {
        TeamProjectsFragment fragment = new TeamProjectsFragment();
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
        fragmentView = inflater.inflate(R.layout.fragment_project_list, container, false);
        buildRecycler();
        return fragmentView;
    }

    public void buildRecycler() {

        // Set the adapter
        Context context = getActivity();

        RecyclerView recyclerView = (RecyclerView) fragmentView;
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ProjectsRecyclerViewAdapter(projects, mListener);
        recyclerView.setAdapter(adapter);

        getProjects();

    }

    public void getProjects() {

        Activity activity = getActivity();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                adapter.update();

            }
        };

        AppHandler.getInstance().teamHandler.getTeamProjects(team.id, 0, 500, projects, activity, runnable);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(ListMember item);
    }
}
