package com.example.musicvkaif74.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicvkaif74.Utility.CRMService;
import com.example.musicvkaif74.EntryActivity;
import com.example.musicvkaif74.Models.Lesson;
import com.example.musicvkaif74.Responses.LessonResponse;
import com.example.musicvkaif74.MainActivity;
import com.example.musicvkaif74.R;
import com.example.musicvkaif74.Utility.SharedPreferencesHelper;
import com.example.musicvkaif74.Models.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimetableFragment extends Fragment {
    public final static String USER_KEY = "USER_KEY";

    private SharedPreferencesHelper helper;

    TextView textView;
    ListView listView;
    TextView tvNotTimetable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timetable, container, false);
        helper = new SharedPreferencesHelper(getActivity());
        textView = view.findViewById(R.id.timetable_name_user);
        listView = view.findViewById(R.id.timetable_lessons);
        tvNotTimetable = view.findViewById(R.id.timetable_text);

        User user;
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable(USER_KEY);
        } else {
            user = MainActivity.users.get(0);
        }
            textView.setText(user.getName());
            List<String> list = new ArrayList<>();
            String date = "";
            GregorianCalendar curDate = new GregorianCalendar();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date = dateFormat.format(curDate.getTime());
            list.add(date);
            curDate.add(Calendar.DAY_OF_YEAR, 7);
            date = dateFormat.format(curDate.getTime());
            list.add(date);
            CRMService.getInstance()
                    .getCRMApi()
                    .getLessons(helper.getToken(), user.getId(), list)
                    .enqueue(new Callback<LessonResponse>() {
                        @Override
                        public void onResponse(Call<LessonResponse> call, Response<LessonResponse> response) {
                            if (response.body() != null) {
                                List<Lesson> lessons = response.body().getLessons();
                                if (lessons.isEmpty()) {
                                    tvNotTimetable.setText("В ближайшее время занятий нет");
                                }
                                for (Lesson lesson : lessons) {
                                    lesson.setClassName(MainActivity.groups.get(lesson.getClassId()));
                                }
                                Lesson[] lessonsArray = lessons.toArray(new Lesson[lessons.size()]);
                                Arrays.sort(lessonsArray, new Comparator<Lesson>() {
                                    @Override
                                    public int compare(Lesson o1, Lesson o2) {
                                        return o1.getDate().compareTo(o2.getDate());
                                    }
                                });
                                ArrayAdapter<Lesson> adapter = new ArrayAdapter<>(getActivity(),
                                        android.R.layout.simple_list_item_1, Arrays.asList(lessonsArray));
                                listView.setAdapter(adapter);
                                listView.setEnabled(false);
                            }
                        }

                        @Override
                        public void onFailure(Call<LessonResponse> call, Throwable t) {
                            Toast.makeText(getActivity(), EntryActivity.FAIL_MESSAGE, Toast.LENGTH_SHORT).show();
                        }
                    });
        return view;
    }

}
