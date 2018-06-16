package com.ux7.fullhyve.ui.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ux7.fullhyve.R;
import com.ux7.fullhyve.services.Handlers.AppHandler;
import com.ux7.fullhyve.ui.activities.NewTaskSetView;
import com.ux7.fullhyve.ui.adapters.TaskSetRecyclerViewAdapter;
import com.ux7.fullhyve.ui.data.ListProject;
import com.ux7.fullhyve.ui.data.ListTaskSet;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TaskSetFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TaskSetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskSetFragment extends Fragment {

    View fragmentView;
    ListProject project;
    List<ListTaskSet> taskSetList = new ArrayList<>();


    FloatingActionButton fab;
    TaskSetRecyclerViewAdapter adapter;
    LinearLayoutManager layoutManager;

    private OnFragmentInteractionListener mListener;

    public TaskSetFragment() {
        // Required empty public constructor
    }


    public static TaskSetFragment newInstance(String param1, String param2) {
        TaskSetFragment fragment = new TaskSetFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    public void setProject(ListProject listProject) {
        this.project = listProject;
    }

    public void buildFloatingActionButton() {

        fab = (FloatingActionButton)fragmentView.findViewById(R.id.add_task_set_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewTaskSetView.class);
                intent.putExtra("project", project);
                startActivity(intent);
            }
        });

    }

    public void buildRecycler() {

        RecyclerView recyclerView = (RecyclerView) fragmentView.findViewById(R.id.task_set_list);
        //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        //recyclerView.addItemDecoration(dividerItemDecoration);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int itemPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                    if (itemPosition == (0)) {
                        // here you can fetch new data from server.
                        fab.show();
                    } else {
                        fab.hide();
                    }
                }
            }
        });

        adapter = new TaskSetRecyclerViewAdapter(taskSetList, project);
        recyclerView.setAdapter(adapter);

        getTaskSets();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_task_set_list, container, false);

        buildFloatingActionButton();
        buildRecycler();

        return fragmentView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void getTaskSets() {

        Activity activity = getActivity();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                adapter.update();

            }
        };

        AppHandler.getInstance().projectHandler.getTaskSets(project.id, 0, 500, taskSetList, activity, runnable);

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
