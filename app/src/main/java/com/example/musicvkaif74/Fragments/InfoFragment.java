package com.example.musicvkaif74.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.musicvkaif74.EntryActivity;
import com.example.musicvkaif74.Models.Post;
import com.example.musicvkaif74.R;
import com.example.musicvkaif74.Utility.SiteService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoFragment extends Fragment {

    WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        webView = (WebView) view.findViewById(R.id.info_web);
        SiteService.getInstance()
                .getSiteApi()
                .getTeachers()
                .enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        Post body = response.body();
                        if (body != null) {
                            webView.loadData(body.getContent(), "text/html", "UTF-8");
                        }
                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {
                        Toast.makeText(getActivity(), EntryActivity.FAIL_MESSAGE, Toast.LENGTH_SHORT).show();
                    }
                });
        return view;
    }
}
