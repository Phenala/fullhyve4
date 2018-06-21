package com.ux7.fullhyve.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ux7.fullhyve.R;
import com.ux7.fullhyve.services.Handlers.AppHandler;
import com.ux7.fullhyve.services.Storage.AppData;
import com.ux7.fullhyve.ui.activities.AddMemberView;
import com.ux7.fullhyve.ui.adapters.MemberRecyclerViewAdapter;
import com.ux7.fullhyve.ui.data.ListMember;
import com.ux7.fullhyve.ui.data.ListProject;
import com.ux7.fullhyve.ui.data.ListTeam;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class MemberFragment extends Fragment implements MemberRecyclerViewAdapter.OnMemberInteractionListener {

    public static boolean get = false;

    View view;
    Context context;
    ListTeam team;
    ListProject project;
    boolean seeInviteButton;

    MemberOf type;
    List<ListMember> members = new ArrayList<>();
    LinearLayoutManager layoutManager;
    MemberRecyclerViewAdapter adapter;

    @Override
    public void onRemoveMember(ListMember member) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                getMembers();

            }
        };

        if (type == MemberOf.TEAM)
            AppHandler.getInstance().teamHandler.removeMembers(team.id, new int[] {member.id}, getActivity(), runnable);
        else if (type == MemberOf.PROJECT) {
            int[] empty = new int[]{};
            int[] users = new int[] {member.id};
            AppHandler.getInstance().projectHandler.removeContributors(project.id, member.team ? users : empty, member.team ? empty : users, getActivity(), runnable);
        }

    }

    public enum MemberOf {

        TEAM,
        PROJECT

    }

    public void setMemberType(MemberOf takeType) {

        type = takeType;

    }

    public void setTeam(ListTeam team) {
        this.team = team;
    }

    public void setProject(ListProject listProject) {
        this.project = listProject;
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

        getMembers();

        return view;
    }

    public void initializeRecyclerView() {

        final FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.add_member_fab);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.member_list);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        if (type == MemberOf.PROJECT && project.id == AppData.getCache().getIdentity().getId()) {
            adapter = new MemberRecyclerViewAdapter(members, true, this);
        } else if (type == MemberOf.TEAM && team.id == AppData.getCache().getIdentity().getId()) {
            adapter = new MemberRecyclerViewAdapter(members, true, this);
        } else {
            adapter = new MemberRecyclerViewAdapter(members, false, this);
        }
        recyclerView.setAdapter(adapter);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int itemPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                    if (itemPosition == (0)) {
                        // here you can fetch new data from server.
                        if (!seeInviteButton) {
                            fab.show();
                        }
                    } else {
                        fab.hide();
                    }
                }
            }
        });
    }

    public void getMembers() {

        Activity activity = getActivity();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                identifyLeader();
                if (type == MemberOf.PROJECT) {
                    AddMemberView.intersectionTeams = new ArrayList<>();
                    AddMemberView.intersectionUsers = new ArrayList<>();

                    for (ListMember member : members) {
                        if (member.team)
                            AddMemberView.intersectionTeams.add(member);
                        else
                            AddMemberView.intersectionUsers.add(member);
                    }

                }
                adapter.update();

            }
        };

        if (type == MemberOf.TEAM)

            AppHandler.getInstance().teamHandler.getTeamMembers(team.id, 0, 500, members, activity, runnable);

        else if (type == MemberOf.PROJECT)

            AppHandler.getInstance().projectHandler.getContributors(project.id, 0, 500, members, activity, runnable);

    }

    public void identifyLeader() {

        Log.e("Memberfragemnt", project + "");

        for (ListMember member : members) {

            if (type == MemberOf.TEAM && member.id == team.detail.leaderId) {
                member.leader = true;
            } else if (type == MemberOf.PROJECT && !member.team && member.id == project.detail.leaderId) {
                member.leader = true;
            }

        }

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

                Intent intent = new Intent(context, AddMemberView.class);
                if (type == MemberOf.TEAM) {

                    intent.putExtra("type", AddMemberView.AddUserType.INVITE_TO_TEAM);
                    intent.putExtra("teamId", team.id);

                } else if (type == MemberOf.PROJECT) {

                    intent.putExtra("type", AddMemberView.AddUserType.INVITE_TO_PROJECT);
                    intent.putExtra("projectId", project.id);

                }

                startActivity(intent);

            }

        });

         seeInviteButton = !((type == MemberOf.TEAM && team.detail.leaderId == AppData.getCache().getIdentity().getId())
                || (type == MemberOf.PROJECT && project.detail.leaderId == AppData.getCache().getIdentity().getId()));

         if (seeInviteButton)
             fab.setVisibility(View.GONE);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            if (requestCode == 0) {

                data.getSerializableExtra("users");

            }

        }

    }




    public void onResume() {
        if (get) {
            getMembers();
            get = false;
        }
        super.onResume();
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
