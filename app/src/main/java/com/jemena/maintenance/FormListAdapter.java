package com.jemena.maintenance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FormListAdapter extends RecyclerView.Adapter<FormListAdapter.FormViewHolder> {
    private ArrayList<String> formNames;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class FormViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView formName;

        public FormViewHolder(View v) {
            super(v);
            formName = v.findViewById(R.id.formName);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FormListAdapter(ArrayList<String> myDataset) {
        formNames = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FormListAdapter.FormViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.form_listing, parent, false);
        FormViewHolder vh = new FormViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(FormViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.formName.setText(formNames.get(position));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return formNames.size();
    }
}

