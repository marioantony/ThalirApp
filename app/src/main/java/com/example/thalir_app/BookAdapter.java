package com.example.thalir_app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private List<AllBooksQuery.AllBook> books;

    public void setBooks(List<AllBooksQuery.AllBook> books) {
        this.books = books;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AllBooksQuery.AllBook book = books.get(position);

        // Bind the schedule data to the views in the item layout
        holder.farmerNameId.setText(book.farmer().firstName());
        holder.locationId.setText(book.farmer().address);
        holder.orderedBagId.setText(String.valueOf(book.requestedBag+" Bags"));
    }

    @Override
    public int getItemCount() {
        return books != null ? books.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView farmerNameId;
        public TextView locationId;
        public TextView orderedBagId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            farmerNameId = itemView.findViewById(R.id.farmerNameId);
            locationId = itemView.findViewById(R.id.locationId);
            orderedBagId = itemView.findViewById(R.id.orderedBagId);
        }
    }
}
