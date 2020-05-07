package com.jemena.maintenance;

import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.util.Function;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RadioPromptCreateActivity extends AppCompatActivity {
    ArrayList<String> options;
    OptionArrayAdapter adapter;
    EditText prompt;
    ListView list;
    Button addOptionButton;
    Button previewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.radio_prompt_create);

        list = this.findViewById(R.id.option_list);
        prompt = this.findViewById(R.id.prompt);
        prompt.setText("Prompt");

        options = new ArrayList();
        adapter = new OptionArrayAdapter(this, R.id.option_list, options);
        list.setAdapter(adapter);

        // Todo: create a function that calls all configs in the right order
        configureAddOptionButton();
        configurePreviewButton();
    }


    private void configureAddOptionButton() {
        addOptionButton = this.findViewById(R.id.add_option_button);
        addOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                options.add(new String("Radio Option"));
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void configurePreviewButton() {
        previewButton = this.findViewById(R.id.preview_button);
        previewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent previewIntent = new Intent(view.getContext(), RadioPromptActivity.class);
                previewIntent.putExtra("options", options);
                previewIntent.putExtra("prompt", prompt.getText().toString());
                startActivity(previewIntent);
            }
        });
    }
    private class OptionArrayAdapter extends ArrayAdapter<String> {
        private LayoutInflater inflater;

        public OptionArrayAdapter(Context context, int textViewResourceId, List<String> options) {
            super(context, textViewResourceId, options);
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {



            if (convertView == null) {
                convertView = inflater.inflate(R.layout.removable_item, null);
            }

            configureTextView(convertView, position);
            configureCloseButton(convertView, position);


            return convertView;
        }


        private void configureTextView(View convertView, int position) {
            TextView text = convertView.findViewById(R.id.removable_edit_text);
            String radioButtonText = getItem(position);
            text.setText(radioButtonText);
            text.setTag(position);
        }


        private void configureCloseButton(View convertView, int position) {
            ImageButton closeButton = convertView.findViewById(R.id.removable_imageButton);
            closeButton.setTag(position);

            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = (int) view.getTag();
                    options.remove(index);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }
}
