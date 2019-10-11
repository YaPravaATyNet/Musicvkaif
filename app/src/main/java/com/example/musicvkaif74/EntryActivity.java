package com.example.musicvkaif74;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.musicvkaif74.Models.ApiKey;
import com.example.musicvkaif74.Models.User;
import com.example.musicvkaif74.Responses.TokenResponse;
import com.example.musicvkaif74.Responses.UserResponse;
import com.example.musicvkaif74.Utility.CRMService;
import com.example.musicvkaif74.Utility.SharedPreferencesHelper;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EntryActivity extends AppCompatActivity {

    public static final String FAIL_MESSAGE = "Соединение не установлено";

    private SharedPreferencesHelper helper;

    CircularProgressView progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        helper = new SharedPreferencesHelper(this);
        progressView = (CircularProgressView) findViewById(R.id.entry_circle);
        receiveToken();

        ArrayAdapter<String> usedNumberAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line,
                new ArrayList<>(helper.getNumbers()));
        final AutoCompleteTextView editText = (AutoCompleteTextView) findViewById(R.id.entry_et);
        editText.setAdapter(usedNumberAdapter);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editText.showDropDown();
                }
            }
        });

        String currentNumber = helper.getCurrentNumber();
        if (!currentNumber.equals("")) {
            entry(currentNumber);
        }
        Button btn = (Button) findViewById(R.id.entry_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = formatNumber(editText.getText().toString());
                if (helper.getToken().equals("")) {
                    receiveToken();
                }
                entry(number);
            }
        });
    }

    public void receiveToken() {
        CRMService.getInstance()
                .getCRMApi()
                .getToken(new ApiKey())
                .enqueue(new Callback<TokenResponse>() {
                    @Override
                    public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                        TokenResponse tokenResponse = response.body();
                        if (tokenResponse != null) {
                            helper.setToken(tokenResponse.getAccessToken());
                        } else {
                            Toast.makeText(EntryActivity.this, FAIL_MESSAGE, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TokenResponse> call, Throwable t) {
                        Toast.makeText(EntryActivity.this, FAIL_MESSAGE, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public String formatNumber (String number) {
        if (number.charAt(0) == '8') {
            return "7" + number.substring(1);
        }
        if (number.charAt(0) == '+') {
            return number.substring(1);
        }
        return number;
    }

    public void entry(final String number) {
        if (number.length() != 11 || !TextUtils.isDigitsOnly(number)) {
            Toast.makeText(this, "Неправильный номер", Toast.LENGTH_SHORT).show();
            return;
        }
        progressView.setVisibility(View.VISIBLE);
        progressView.startAnimation();
        CRMService.getInstance()
                .getCRMApi()
                .getUsers(helper.getToken(), number)
                .enqueue(new Callback<UserResponse>() {

                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if (response.code() == 200) {
                            if (response.body() != null) {
                                List<User> users = response.body().getUsers();
                                List<User> goodUsers = new ArrayList<>();
                                for (User u : users) {
                                    if (number.equals(String.valueOf(u.getPhone()))) {
                                        goodUsers.add(u);
                                    }
                                }
                                if (goodUsers.isEmpty()) {
                                    Toast.makeText(EntryActivity.this, "Неправильный номер", Toast.LENGTH_SHORT).show();
                                } else {
                                    helper.setUsers(goodUsers);
                                    helper.addNumber(number);
                                    helper.setCurrentNumber(number);
                                    Intent intent = new Intent(EntryActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        } else {
                            Toast.makeText(EntryActivity.this, FAIL_MESSAGE, Toast.LENGTH_SHORT).show();
                        }
                        progressView.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Toast.makeText(EntryActivity.this, FAIL_MESSAGE, Toast.LENGTH_SHORT).show();
                        progressView.setVisibility(View.INVISIBLE);
                    }

                });
    }


}
