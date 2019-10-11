package com.example.musicvkaif74.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.musicvkaif74.EntryActivity;
import com.example.musicvkaif74.Models.Course;
import com.example.musicvkaif74.Models.Group;
import com.example.musicvkaif74.OnCourseFragmentListener;
import com.example.musicvkaif74.R;
import com.example.musicvkaif74.Utility.CRMService;
import com.example.musicvkaif74.Utility.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCourseFragment extends Fragment {

    private OnCourseFragmentListener listener;

    ListView listView;
    SharedPreferencesHelper helper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_course, container, false);
        helper = new SharedPreferencesHelper(getActivity());
        listView = (ListView) view.findViewById(R.id.list_course);
        CRMService.getInstance()
                .getCRMApi()
                .getCourses(helper.getToken(), true)
                .enqueue(new Callback<List<Course>>() {
                    @Override
                    public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                        List<Course> courses = response.body();
                        if (courses != null) {
                            final List<Course> notArchiveCourse = new ArrayList<>();
                            for (Course course : courses) {
                                boolean check = false;
                                if (course.getClasses() != null) {
                                    for (Group group : course.getClasses()) {
                                        if (group.getStatus().equals("opened")) {
                                            check = true;
                                            break;
                                        }
                                    }
                                    if (check) {
                                        notArchiveCourse.add(course);
                                    }
                                }
                            }
                            ArrayAdapter<Course> adapter = new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_list_item_1, notArchiveCourse);
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Course course = notArchiveCourse.get(position);
                                    listener.onOpenCourseFragment(course);
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Course>> call, Throwable t) {
                        Toast.makeText(getActivity(), EntryActivity.FAIL_MESSAGE, Toast.LENGTH_SHORT).show();
                    }
                });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (OnCourseFragmentListener) context;
    }
}
