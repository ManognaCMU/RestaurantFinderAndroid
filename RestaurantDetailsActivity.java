/**
 * Name: Sai Manogna Pentyala
 * Andrew: spentyal
 * Task: Android App
 * Last Modified: April 4, 2020
 *
 * This class displays the list
 * of restaurants image upto
 * the count of 4. Also displays the
 * home button and back button
 * for the user to move across views
 */

package com.spentyal.andrew.restaurantfinderandroidapp;

// imports to create the android app

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

/**
 * The view for the third screen where the user sees upto 4 pictures of the restaurant
 */
public class RestaurantDetailsActivity extends AppCompatActivity {

    // captures the restaurant name
    String restaurantName;

    @Override
    /** activity for the view is initialized here */
    protected void onCreate(Bundle savedInstanceState) {
        // creates the view - the third screen for the app
        super.onCreate(savedInstanceState);
        // creates the view as per activity_restaurant_details.xml
        setContentView(R.layout.activity_restaurant_details);

        // captures the restaurant name
        restaurantName = getIntent().getStringExtra("restaurantName");
        // captures the city name
        String cityName = getIntent().getStringExtra("cityName");
        // captures the state name
        String stateName = getIntent().getStringExtra("stateName");

        // class that retrieves details of the restaurants in a city
        GetRestaurant gr = new GetRestaurant();
        // searches for the pictures of restaurants in a particular city and state
        gr.searchForPicture(cityName, stateName, restaurantName, this);

    }

    // populates the restaurants pictures on the android app
    public void populateRestaurantPictures(List<Bitmap> restaurantPicList) {

        // captures the image of the restaurant
        ImageView pictureView = null;
        // captures the name of the restaurant
        TextView restaurantNameView = findViewById(R.id.restaurant_name);
        // makes the restaurantNameView visible
        restaurantNameView.setVisibility(View.VISIBLE);
        // sets the restaurant name on the restaurantNameView
        restaurantNameView.setText(restaurantName.toString());
        // button used to go to the previous screen
        Button backButton = (Button) findViewById(R.id.button5);
        // button used to go to the home page
        Button homeButton = (Button) findViewById(R.id.button4);
        // set the back button visible
        backButton.setVisibility(View.VISIBLE);
        // set the home button visible
        homeButton.setVisibility(View.VISIBLE);

        // to show upto 4 pictures of the restaurants
        for (int i = 0; i < 4; i++) {

            // get the bitmap of the picture from the list
            Bitmap picture = restaurantPicList.get(i);

            if (i == 0) {
                // sets the first image of the restaurant
                pictureView = (ImageView) findViewById(R.id.imageView1);
            } else if (i == 1) {
                // sets the second image of the restaurant
                pictureView = (ImageView) findViewById(R.id.imageView2);
            } else if (i == 2) {
                // sets the third image of the restaurant
                pictureView = (ImageView) findViewById(R.id.imageView3);
            } else if (i == 3) {
                // sets the fourth image of the restaurant
                pictureView = (ImageView) findViewById(R.id.imageView4);
            }

            // if a picture of the restaurant is available
            if (picture != null) {
                //set the image on the view
                pictureView.setImageBitmap(picture);
                // make the pictureView visible
                pictureView.setVisibility(View.VISIBLE);
                // if a picture of the restaurant is not available
            } else {
                // make the pictureView invisible
                pictureView.setVisibility(View.INVISIBLE);
            }

            // to invalidate the data on the pictureView
            pictureView.invalidate();

        }

       // add click listener on the back button
        backButton.setOnClickListener(new View.OnClickListener() {

            /** specifies what to do when the back button is clicked */
            public void onClick(View v) {
                // goes to the previous screen
                finish();
            }
        });

        // add click listener on the home button
        homeButton.setOnClickListener(new View.OnClickListener() {

            /** specifies what to do when the home button is clicked */
            public void onClick(View v) {
                // the input is moved from this class to "MainActivity" class
                Intent intent = new Intent(RestaurantDetailsActivity.this, MainActivity.class);
                //start the activity connect to the specified class
                startActivity(intent);
            }
        });

    }
}
