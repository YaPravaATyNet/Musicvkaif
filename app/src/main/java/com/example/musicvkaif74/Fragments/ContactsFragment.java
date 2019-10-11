package com.example.musicvkaif74.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicvkaif74.EntryActivity;
import com.example.musicvkaif74.Models.Contacts;
import com.example.musicvkaif74.R;
import com.example.musicvkaif74.Utility.SiteService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactsFragment extends Fragment {

    TextView address;
    TextView phones;
    TextView site;
    TextView vk;
    TextView instagram;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        address = (TextView) view.findViewById(R.id.contacts_address);
        phones = (TextView) view.findViewById(R.id.contacts_phones);
        site = (TextView) view.findViewById(R.id.contacts_site);
        vk = (TextView) view.findViewById(R.id.contacts_vk);
        instagram = (TextView) view.findViewById(R.id.contacts_instagram);
        SiteService.getInstance()
                .getSiteApi()
                .getContacts()
                .enqueue(new Callback<Contacts>() {
                    @Override
                    public void onResponse(Call<Contacts> call, Response<Contacts> response) {
                        Contacts body = response.body();
                        if (body != null) {
                            address.setText(body.getAddress());
                            phones.setText(body.getPhones());
                            site.setText(body.getSite());
                            vk.setText(body.getVk());
                            instagram.setText(body.getInstagram());
                        }
                    }

                    @Override
                    public void onFailure(Call<Contacts> call, Throwable t) {
                        Toast.makeText(getActivity(), EntryActivity.FAIL_MESSAGE, Toast.LENGTH_SHORT).show();
                    }
                });
        return view;
    }

}
