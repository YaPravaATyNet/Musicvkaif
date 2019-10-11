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
import com.example.musicvkaif74.Models.Post;
import com.example.musicvkaif74.OnNewsFragmentListener;
import com.example.musicvkaif74.R;
import com.example.musicvkaif74.Utility.SiteService;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListNewsFragment extends Fragment {

    private OnNewsFragmentListener listener;

    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_news, container, false);
        listView = (ListView) view.findViewById(R.id.list_news);
        SiteService.getInstance()
                .getSiteApi()
                .getNews()
                .enqueue(new Callback<List<Post>>() {
                    @Override
                    public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                        final List<Post> news = response.body();
                        if (news != null) {
                            Collections.reverse(news);
                            ArrayAdapter<Post> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, news);
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Post oneNews = news.get(position);
                                    listener.onOpenNewsFragment(oneNews.getContent());
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Post>> call, Throwable t) {
                        Toast.makeText(getActivity(), EntryActivity.FAIL_MESSAGE, Toast.LENGTH_SHORT).show();
                    }
                });
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (OnNewsFragmentListener) context;
    }

}
