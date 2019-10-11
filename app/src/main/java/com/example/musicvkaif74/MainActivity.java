package com.example.musicvkaif74;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicvkaif74.Fragments.CourseFragment;
import com.example.musicvkaif74.Fragments.ContactsFragment;
import com.example.musicvkaif74.Fragments.InfoFragment;
import com.example.musicvkaif74.Fragments.ListCourseFragment;
import com.example.musicvkaif74.Fragments.ListNewsFragment;
import com.example.musicvkaif74.Fragments.ListUserFragment;
import com.example.musicvkaif74.Fragments.NewsFragment;
import com.example.musicvkaif74.Fragments.TimetableFragment;
import com.example.musicvkaif74.Models.Course;
import com.example.musicvkaif74.Models.Group;
import com.example.musicvkaif74.Models.User;
import com.example.musicvkaif74.Utility.CRMService;
import com.example.musicvkaif74.Utility.SharedPreferencesHelper;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnUserFragmentListener, OnNewsFragmentListener, OnCourseFragmentListener {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private SharedPreferencesHelper helper;

    public static HashMap<Long, String> groups = new HashMap<>();
    public static List<User> users;
    public static Context context;

    Toolbar toolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        helper = new SharedPreferencesHelper(this);
        users = helper.getUsers();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Расписание");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView textView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_text);
        String number = helper.getCurrentNumber();
        textView.setText(number);

        if (savedInstanceState == null) {
            CRMService.getInstance()
                    .getCRMApi()
                    .getGroups(helper.getToken())
                    .enqueue(new Callback<List<Group>>() {
                        @Override
                        public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                            List<Group> list = response.body();
                            if (list != null) {
                                for (Group group : list) {
                                    groups.put(group.getId(), group.getName());
                                }
                                if (users.size() == 1) {
                                    fragmentManager.beginTransaction()
                                            .replace(R.id.content_main, new TimetableFragment())
                                            .addToBackStack(null)
                                            .commit();
                                } else {
                                    fragmentManager.beginTransaction()
                                            .replace(R.id.content_main, new ListUserFragment())
                                            .addToBackStack(null)
                                            .commit();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Group>> call, Throwable t) {
                            Toast.makeText(MainActivity.this, EntryActivity.FAIL_MESSAGE, Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (fragmentManager.getBackStackEntryCount() <= 1) {
                finish();
            } else {
                fragmentManager.popBackStackImmediate();
                Fragment fragment = fragmentManager.findFragmentById(R.id.content_main);
                int index = 0;
                if (fragment instanceof ListCourseFragment || fragment instanceof CourseFragment) {
                    index = 1;
                } else if (fragment instanceof ListNewsFragment || fragment instanceof  NewsFragment) {
                    index = 2;
                } else if (fragment instanceof InfoFragment) {
                    index = 3;
                } else if (fragment instanceof ContactsFragment) {
                    index = 4;
                }
                MenuItem item = navigationView.getMenu().getItem(index);
                item.setChecked(true);
                toolbar.setTitle(item.getTitle());
            }
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_timetable) {
            if (users.size() == 1) {
                fragmentManager.beginTransaction().replace(R.id.content_main, new TimetableFragment())
                        .addToBackStack(null)
                        .commit();
            } else {
                fragmentManager.beginTransaction().replace(R.id.content_main, new ListUserFragment())
                        .addToBackStack(null)
                        .commit();
            }
        } else if (id == R.id.nav_course) {
            fragmentManager.beginTransaction().replace(R.id.content_main, new ListCourseFragment())
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_news) {
            fragmentManager.beginTransaction().replace(R.id.content_main, new ListNewsFragment())
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_info) {
            fragmentManager.beginTransaction().replace(R.id.content_main, new InfoFragment())
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_contact) {
            fragmentManager.beginTransaction().replace(R.id.content_main, new ContactsFragment())
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_exit) {
             SharedPreferencesHelper helper = new SharedPreferencesHelper(this);
             helper.setCurrentNumber("");
             Intent intent = new Intent(this, EntryActivity.class);
             startActivity(intent);
             finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        toolbar.setTitle(item.getTitle());
        return true;
    }

    @Override
    public void onOpenTimetableFragment(User user) {
        Fragment fragment = fragmentManager.findFragmentById(R.id.content_main);
        if (fragment instanceof ListUserFragment) {
            Fragment newFragment = new TimetableFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(TimetableFragment.USER_KEY, user);
            newFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main, newFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onOpenNewsFragment(String url) {
        Fragment fragment = fragmentManager.findFragmentById(R.id.content_main);
        if (fragment instanceof ListNewsFragment) {
            Fragment newFragment = new NewsFragment();
            Bundle bundle = new Bundle();
            bundle.putString(NewsFragment.NEWS_KEY, url);
            newFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main, newFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onOpenCourseFragment(Course course) {
        Fragment fragment = fragmentManager.findFragmentById(R.id.content_main);
        if (fragment instanceof ListCourseFragment) {
            Fragment newFragment = new CourseFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(CourseFragment.COURSE_KEY, course);
            newFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main, newFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
