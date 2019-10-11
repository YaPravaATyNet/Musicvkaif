package com.example.musicvkaif74.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicvkaif74.MainActivity;
import com.example.musicvkaif74.Models.Group;
import com.example.musicvkaif74.Models.Request;
import com.example.musicvkaif74.Utility.CRMService;
import com.example.musicvkaif74.EntryActivity;
import com.example.musicvkaif74.Models.Course;
import com.example.musicvkaif74.R;
import com.example.musicvkaif74.Utility.SharedPreferencesHelper;
import com.example.musicvkaif74.Utility.SiteService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseFragment extends Fragment {

    public static final String COURSE_KEY = "COURSE_KEY";

    SharedPreferencesHelper helper;
    TextView textView;
    ListView listView;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_courses, container, false);
        helper = new SharedPreferencesHelper(getActivity());
        textView = view.findViewById(R.id.course_description);
        listView = view.findViewById(R.id.courses_list);
        button = view.findViewById(R.id.course_button);
        if (getArguments() != null) {
            Course course = (Course) getArguments().getSerializable(COURSE_KEY);
            if (course != null) {
                textView.setText(Html.fromHtml(course.getDescription() != null ? course.getDescription() : "Отсутсвует").toString().trim());
                List<Group> allGroups = course.getClasses();
                final List<Group> groups = new ArrayList<>();
                for (Group group : allGroups) {
                    if (group.getStatus().equals("opened")) {
                        groups.add(group);
                    }
                }
                ArrayAdapter<Group> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_multiple_choice
                        , groups);
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                listView.setAdapter(adapter);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SparseBooleanArray chosen = listView.getCheckedItemPositions();
                        for (int i = 0; i < chosen.size(); i++) {
                            if (chosen.valueAt(i)) {
                                final Group group = groups.get(chosen.keyAt(i));
                                CRMService.getInstance()
                                        .getCRMApi()
                                        .join(helper.getToken(), new Request(MainActivity.users.get(0).getId(), group.getId(), 1668))
                                        .enqueue(new Callback<Request>() {
                                            @Override
                                            public void onResponse(Call<Request> call, Response<Request> response) {
                                                if (response.code() == 200) {
                                                    Toast.makeText(getActivity(), "Вы успешно записаны", Toast.LENGTH_SHORT).show();
                                                    SiteService.getInstance()
                                                            .getSiteApi()
                                                            .join(helper.getCurrentNumber(), group.getName())
                                                            .enqueue(new Callback<Request>() {
                                                                @Override
                                                                public void onResponse(Call<Request> call, Response<Request> response) {
                                                                }

                                                                @Override
                                                                public void onFailure(Call<Request> call, Throwable t) {
                                                                }
                                                            });
                                                } else {
                                                    Toast.makeText(getActivity(), "Запись недоступна", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Request> call, Throwable t) {
                                                Toast.makeText(getActivity(), EntryActivity.FAIL_MESSAGE, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }
                    }
                });
            }
        }
        return view;
    }

}
