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
import com.ux7.fullhyve.ui.adapters.ProjectsRecyclerViewAdapter;
import com.ux7.fullhyve.ui.data.ListProject;
import com.ux7.fullhyve.ui.interfaces.OnHomeInteractionListener;

import java.util.ArrayList;
import java.util.List;

public class ProjectsListFragment extends Fragment implements HomeView.OnHomeSearchListener {

    List<ListProject> projects = new ArrayList<>();

    View fragmentView;
    LinearLayoutManager layoutManager;
    ProjectsRecyclerViewAdapter adapter;


    private OnHomeInteractionListener mListener;

    public ProjectsListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ProjectsListFragment newInstance(int columnCount) {
        ProjectsListFragment fragment = new ProjectsListFragment();
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
        fragmentView = inflater.inflate(R.layout.fragment_project_list, container, false);

        buildRecycler();

        return fragmentView;
    }

    public void buildRecycler() {

        Context context = fragmentView.getContext();
        RecyclerView recyclerView = (RecyclerView) fragmentView;
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
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

        AppHandler.getInstance().projectHandler.getMyProjects(0, 500, projects, activity, runnable);

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

    @Override
    public void onSearch(String s) {

        Activity activity = getActivity();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                adapter.update();

            }
        };

        AppHandler.getInstance().projectHandler.searchProjects(0, 500, s, projects, activity, runnable);

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
