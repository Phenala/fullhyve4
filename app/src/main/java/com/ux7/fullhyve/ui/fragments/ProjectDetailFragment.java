package com.ux7.fullhyve.ui.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ux7.fullhyve.R;
import com.ux7.fullhyve.ui.data.ListProject;
import com.ux7.fullhyve.ui.data.ProjectDetail;
import com.ux7.fullhyve.ui.util.CircleTransform;
import com.ux7.fullhyve.ui.util.Images;
import com.ux7.fullhyve.ui.util.Util;

public class ProjectDetailFragment extends Fragment {

    public View rootView;

    ListProject project;

    private OnFragmentInteractionListener mListener;

    public ProjectDetailFragment() {
        // Required empty public constructor
    }

    public void setProject(ListProject listProject) {
        this.project = listProject;
    }

    public static ProjectDetailFragment newInstance(String param1, String param2) {
        ProjectDetailFragment fragment = new ProjectDetailFragment();
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
        rootView = inflater.inflate(R.layout.fragment_project_detail, container, false);

        buildProjectDetail();

        return rootView;
    }

    public void buildProjectDetail() {

        ProjectDetail projectDetail = project.detail;
        ((TextView)rootView.findViewById(R.id.project_name)).setText(projectDetail.name);
        ((TextView)rootView.findViewById(R.id.project_focus)).setText(projectDetail.focus);
        ((TextView)rootView.findViewById(R.id.project_description)).setText(projectDetail.description);
        ((TextView)rootView.findViewById(R.id.project_member_count)).setText(projectDetail.contributors + " contributors");
        ImageView projectImageView = ((ImageView)rootView.findViewById(R.id.project_image));
        projectImageView.setBackgroundResource(Images.PROJECT);

        if (projectDetail.image != null) {

            Picasso.with(getActivity())
                    .load(Util.getImageUrl(projectDetail.image))
                    .transform(new CircleTransform())
                    .into(projectImageView);

        } else {

            projectImageView.setImageResource(Images.PROJECT);

        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
