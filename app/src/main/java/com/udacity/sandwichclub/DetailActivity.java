package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String TAG = "DetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
            if (sandwich == null) {
                throw new NullPointerException();
            }
        }catch (NullPointerException e){
            // Sandwich data unavailable
            Log.e(TAG, e.getMessage());
            closeOnError();
            return;
        }catch (JSONException e){
            // Sandwich data unavailable
            Log.e(TAG, e.getMessage());
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        changeTextAndStateOfTextview(R.id.also_known_tv, R.id.also_known_label, getSingleStringBlockOfList(sandwich.getAlsoKnownAs()));
        changeTextAndStateOfTextview(R.id.origin_tv, R.id.origin_label, sandwich.getPlaceOfOrigin());
        changeTextAndStateOfTextview(R.id.description_tv, R.id.description_label, sandwich.getDescription());
        changeTextAndStateOfTextview(R.id.ingredients_tv, R.id.ingredients_label, getSingleStringBlockOfList(sandwich.getIngredients()));
    }

    private void changeTextAndStateOfTextview(int idTextview, int idLabel, String entry){
        TextView entry_tv = (TextView) findViewById(idTextview);
        TextView label_tv = (TextView) findViewById(idLabel);
        if(entry == null || entry.length() == 0){
            entry_tv.setVisibility(View.GONE);
            label_tv.setVisibility(View.GONE);
        }else {entry_tv.setText(entry);
            entry_tv.setVisibility(View.VISIBLE);
            label_tv.setVisibility(View.VISIBLE);
        }
    }

    private String getSingleStringBlockOfList(List<String> myList){
        final StringBuilder allItems = new StringBuilder();
        for (int i = 0; i < myList.size(); i++) {
            allItems.append(myList.get(i));
            if(i != myList.size()-1){
                allItems.append("\n");
            }
        }
        return allItems.toString();
    }
}
