package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
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

    TextView mainNametxt, alsoKnownAstxt, placeOfOrigintxt, descriptiontxt, ingredientstxt,
            alsoKnownAstxtla, placeOfOrigintxtla, descriptiontxtla, ingredientstxtla;
    ImageView ingredientsIv;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        populateUI();
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
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
            if (!sandwich.getMainName().isEmpty() && sandwich.getMainName() != null) {
                mainNametxt.setText(sandwich.getMainName());
            }
            if (!sandwich.getAlsoKnownAs().isEmpty() && sandwich.getAlsoKnownAs() != null) {
                for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {
                    if (i == sandwich.getAlsoKnownAs().size() - 1) {
                        alsoKnownAstxt.append(sandwich.getAlsoKnownAs().get(i));
                    } else {
                        alsoKnownAstxt.append(sandwich.getAlsoKnownAs().get(i) + ", ");
                    }
                }
            } else {
                alsoKnownAstxtla.setVisibility(View.GONE);
            }

            if (!sandwich.getPlaceOfOrigin().isEmpty() && sandwich.getPlaceOfOrigin() != null) {
                placeOfOrigintxt.setText(sandwich.getPlaceOfOrigin());
            } else {
                placeOfOrigintxtla.setVisibility(View.GONE);
            }

            if (!sandwich.getDescription().isEmpty() && sandwich.getDescription() != null) {
                descriptiontxt.setText(sandwich.getDescription());
            } else {
                descriptiontxtla.setVisibility(View.GONE);
            }
            if (!sandwich.getIngredients().isEmpty() && sandwich.getIngredients() != null) {
                List<String> ingredientsList = sandwich.getIngredients();
                ingredientstxt.setText("");
                for (int i = 0; i < ingredientsList.size(); i++) {
                    if (i == sandwich.getIngredients().size() - 1) {
                        ingredientstxt.append(sandwich.getIngredients().get(i));
                    } else {
                        ingredientstxt.append(sandwich.getIngredients().get(i) + ", ");
                    }
                }
            } else {
                ingredientstxtla.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }


        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);
        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        ingredientsIv = findViewById(R.id.image_iv);
        mainNametxt = findViewById(R.id.origin_tv);
        alsoKnownAstxt = findViewById(R.id.also_known_tv);
        placeOfOrigintxt = findViewById(R.id.origin_detail_tv);
        descriptiontxt = findViewById(R.id.description_tv);
        ingredientstxt = findViewById(R.id.ingredients_tv);

        alsoKnownAstxtla = findViewById(R.id.also_known_la);
        placeOfOrigintxtla = findViewById(R.id.origin_detail_la);
        descriptiontxtla = findViewById(R.id.description_la);
        ingredientstxtla = findViewById(R.id.ingredients_la);

    }
}
