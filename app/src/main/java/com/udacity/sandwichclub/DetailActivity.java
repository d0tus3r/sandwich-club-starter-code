package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;



public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final ImageView ingredientsIv = findViewById(R.id.image_iv);


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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
        //callback assists with hiding image view if error loading image
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv, new com.squareup.picasso.Callback(){
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        ingredientsIv.setVisibility(View.GONE);
                    }
        });

        populateUI(sandwich);


        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        //grab string from resources for later use in populating UI
        final String SANDWICH_DATA_ERROR = getResources().getString(R.string.err_no_data);
        //create textviews - wire to activity_details - set text
        //aka tv
        TextView akaTv = findViewById(R.id.also_known_tv);
        //Test for data before populating
        if (sandwich.getAlsoKnownAs().isEmpty()){
            akaTv.setText(SANDWICH_DATA_ERROR);
        } else {
            String akaList = TextUtils.join(", ", sandwich.getAlsoKnownAs());
            akaTv.setText(akaList);
        }
        //origin
        TextView originTv = findViewById(R.id.origin_tv);
        //Test for data before populating
        if (sandwich.getPlaceOfOrigin().isEmpty()){
            originTv.setText(SANDWICH_DATA_ERROR);
        } else {
            originTv.setText(sandwich.getPlaceOfOrigin());
        }
        //ingredients
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        //Test for data before populating
        if (sandwich.getIngredients().isEmpty()){
            ingredientsTv.setText(SANDWICH_DATA_ERROR);
        } else {
            String ingredientsList = TextUtils.join(", ", sandwich.getIngredients());
            ingredientsTv.setText(ingredientsList);
        }
        //description
        TextView descriptionTv = findViewById(R.id.description_tv);
        //Test for data before populating
        if (sandwich.getDescription().isEmpty()){
            descriptionTv.setText(SANDWICH_DATA_ERROR);
        } else {
            descriptionTv.setText(sandwich.getDescription());
        }
    }
}
