package com.ux7.fullhyve.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ux7.fullhyve.R;
import com.ux7.fullhyve.ui.activities.AddMember;
import com.ux7.fullhyve.ui.adapters.MemberRecyclerViewAdapter;
import com.ux7.fullhyve.ui.data.ListMember;

import java.util.ArrayList;


public class MemberFragment extends Fragment {

    View view;
    Context context;

    public MemberFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MemberFragment newInstance(int columnCount) {
        MemberFragment fragment = new MemberFragment();
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
        view = inflater.inflate(R.layout.fragment_member_list, container, false);

        context = getActivity();

        initializeRecyclerView();
        initializeFloatingActionButton();

        return view;
    }

    public void initializeRecyclerView() {

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.member_list);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));


        ArrayList<ListMember> items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            items.add(new ListMember());
        }

        final FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.add_member_fab);

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

        recyclerView.setAdapter(new MemberRecyclerViewAdapter(items));
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    public void initializeFloatingActionButton() {

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.add_member_fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, AddMember.class);
                startActivity(intent);

            }

        });

    }










    @Override
    public void onDetach() {
        super.onDetach();
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
