package com.ux7.fullhyve.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ux7.fullhyve.R;
import com.ux7.fullhyve.ui.data.TeamDetail;
import com.ux7.fullhyve.ui.util.CircleTransform;
import com.ux7.fullhyve.ui.util.U;


public class TeamDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public View rootView;

    TeamDetail teamDetail;

    private OnFragmentInteractionListener mListener;

    public TeamDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeamDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeamDetailFragment newInstance(String param1, String param2) {
        TeamDetailFragment fragment = new TeamDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_team_detail, container, false);

        buildTeamDetail();

        return rootView;
    }

    public void buildTeamDetail() {

        ((TextView)rootView.findViewById(R.id.team_name)).setText(teamDetail.name);
        ((TextView)rootView.findViewById(R.id.team_focus)).setText(teamDetail.focus);
        ((TextView)rootView.findViewById(R.id.team_description)).setText(teamDetail.description);
        ((TextView)rootView.findViewById(R.id.team_member_count)).setText(teamDetail.members + " members");
        ImageView teamImageView = ((ImageView)rootView.findViewById(R.id.team_image));

        if (teamDetail.image != null)
            Picasso.with(getActivity())
                    .load(U.getImageUrl(teamDetail.image))
                    .transform(new CircleTransform())
                    .into(teamImageView);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void setTeamDetail(TeamDetail detail) {
        teamDetail = detail;
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
