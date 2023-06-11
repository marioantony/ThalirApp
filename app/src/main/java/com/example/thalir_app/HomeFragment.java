package com.example.thalir_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ScheduleAdapter scheduleAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        scheduleAdapter = new ScheduleAdapter();
        recyclerView.setAdapter(scheduleAdapter);

        // Make the GraphQL query and populate the RecyclerView
        queryAllSchedules();

        return view;
    }

    private void queryAllSchedules() {
        ApolloClient apolloClient = ApolloClient.builder()
                .serverUrl(UrlHelper.BASE_URL)
                .build();

        apolloClient.query(AllSchedulesQuery.builder().build()).enqueue(new ApolloCall.Callback<AllSchedulesQuery.Data>() {
            @Override
            public void onResponse(@NonNull Response<AllSchedulesQuery.Data> response) {
                List<AllSchedulesQuery.AllSchedule> schedules = response.getData().allSchedules();
                updateScheduleList(schedules);
            }

            @Override
            public void onFailure(@NonNull ApolloException e) {
                // Handle failure
            }
        });
    }

    private void updateScheduleList(List<AllSchedulesQuery.AllSchedule> schedules) {
        // Update the RecyclerView adapter with the new list of schedules
        if (isAdded() && getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    scheduleAdapter.setSchedules(schedules);
                    scheduleAdapter.notifyDataSetChanged();
                }
            });
        }

    }

    // ScheduleAdapter class definition goes here

}
