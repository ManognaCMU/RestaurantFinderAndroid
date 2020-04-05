/**
 * Name: Sai Manogna Pentyala
 * Andrew: spentyal
 * Task: Android App
 * Last Modified: April 4, 2020
 *
 * This class displays the list
 * of restaurants in a city and
 * a state. Also provides the flexibility
 * for the user move to the home page.
 * Also displays an error message if a wrong
 * input is given by the user
 */

package com.spentyal.andrew.restaurantfinderandroidapp;

// imports to create the android app

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * The view for the second screen where the user sees up to a list of 5 restaurants in the city
 */
public class RestaurantListActivity extends AppCompatActivity {

    // captures the restaurants as a list
    private RecyclerView recyclerView;
    // captures the city name
    String cityInput;
    // captures the state name
    String stateInput;

    @Override
    /** activity for the view is initialized here */
    protected void onCreate(Bundle savedInstanceState) {
        // creates the view - the second screen for the app
        super.onCreate(savedInstanceState);
        // creates the view as per activity_restaurant_list.xml
        setContentView(R.layout.activity_restaurant_list);

        // captures the city name passed as a parameter from the first screen
        cityInput = getIntent().getStringExtra("cityName");
        // captures the state name passed as a parameter from the second screen
        stateInput = getIntent().getStringExtra("stateName");

        // class that retrieves details of the restaurants in a city
        GetRestaurant gr = new GetRestaurant();
        // searches for the list of restaurants in a particular city and state
        gr.search(cityInput, stateInput, this);
    }

    // populates the list of restaurants on the android app
    public void populateRestaurantList(final List<String> restaurantList, String message) {

        // set up the Recycler View
        recyclerView = findViewById(R.id.restaurant_RecycleView);
        // sets up a linear layout for the list view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // to display the list of restaurant as a recycler view on the screen
        MyAdapter adapter = new MyAdapter(this, restaurantList, new MyAdapter.ItemClickListener() {
            @Override
            /** specifies what to do on clicking on the restaurant */
            public void onItemClick(View view, int position) {
                // the input is moved from this class to "RestaurantDetailsActivity" class
                Intent intent = new Intent(RestaurantListActivity.this, RestaurantDetailsActivity.class);
                // the city name is passed as a parameter to "RestaurantDetailsActivity" class
                intent.putExtra("cityName", cityInput);
                // the state name is passed as a parameter to "RestaurantDetailsActivity" class
                intent.putExtra("stateName", stateInput);
                // the restaurant name is passed as a parameter to "RestaurantDetailsActivity" class
                intent.putExtra("restaurantName", restaurantList.get(position));
                // start the activity connect to the specified class
                startActivity(intent);
            }
        });

        // captures the name of the restaurant
        TextView screenheading = findViewById(R.id.textView);
        // makes the restaurantNameView visible
        screenheading.setVisibility(View.VISIBLE);

        // captures the note
        TextView note = findViewById(R.id.textView2);
        // makes the note visible
        note.setVisibility(View.VISIBLE);
        if(message != null && message.equalsIgnoreCase("Error")) {
            note.setText("No Data - Please go back and enter a valid input");
        }

        // button used to go to the previous screen
        Button backButton = (Button) findViewById(R.id.button);
        // makes the back button visible
        backButton.setVisibility(View.VISIBLE);

        // add click listener on the back button
        backButton.setOnClickListener(new View.OnClickListener() {

            /** specifies what to do when the home button is clicked */
            public void onClick(View v) {
                // the input is moved from this class to "MainActivity" class
                Intent intent = new Intent(RestaurantListActivity.this, MainActivity.class);
                //start the activity connect to the specified class
                startActivity(intent);
            }
        });


        // set the list created as a recycler view on the app
        recyclerView.setAdapter(adapter);

    }

}
