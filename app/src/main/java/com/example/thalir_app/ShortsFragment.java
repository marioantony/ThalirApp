package com.example.thalir_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ShortsFragment extends Fragment {

        private RecyclerView recyclerView;
        private BookAdapter bookAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shorts, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewId2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bookAdapter = new BookAdapter();
        recyclerView.setAdapter(bookAdapter);

        queryAllBook();

        return view;
    }

    private void queryAllBook() {
        ApolloClient apolloClient = ApolloClient.builder()
                .serverUrl(UrlHelper.BASE_URL)
                .build();

        apolloClient.query(AllBooksQuery.builder().build()).enqueue(new ApolloCall.Callback<AllBooksQuery.Data>() {
            @Override
            public void onResponse(@NonNull Response<AllBooksQuery.Data> response) {
                List<AllBooksQuery.AllBook> books = response.getData().allBooks();
                updateBookList(books);
            }

            @Override
            public void onFailure(@NonNull ApolloException e) {

            }
        });
    }

private void updateBookList(List<AllBooksQuery.AllBook> books){
    if (isAdded() && getActivity() != null) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bookAdapter.setBooks(books);
                bookAdapter.notifyDataSetChanged();
            }
        });
    }
}
}