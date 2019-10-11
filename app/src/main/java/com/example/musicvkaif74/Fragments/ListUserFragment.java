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

import com.example.musicvkaif74.MainActivity;
import com.example.musicvkaif74.OnUserFragmentListener;
import com.example.musicvkaif74.R;
import com.example.musicvkaif74.Models.User;

public class ListUserFragment extends Fragment {

    private OnUserFragmentListener listener;

    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_list_user, container, false);
        listView = view.findViewById(R.id.list_user);
        ArrayAdapter<User> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, MainActivity.users);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = MainActivity.users.get(position);
                listener.onOpenTimetableFragment(user);
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (OnUserFragmentListener) context;
    }
}
