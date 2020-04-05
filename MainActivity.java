/**
 * Name: Sai Manogna Pentyala
 * Andrew: spentyal
 * Task: Android App
 * Last Modified: April 4, 2020
 *
 * This class displays the home page
 * where the user is prompted
 * to enter the city name
 * and the state name
 */

package com.spentyal.andrew.restaurantfinderandroidapp;

// imports to create the android app

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

/**
 * The view for the first screen that the user sees, where the user enters the city name ad the state name
 */
public class MainActivity extends AppCompatActivity {

    @Override
    /** activity for the view is initialized here */
    protected void onCreate(Bundle savedInstanceState) {
        // creates the view - the first screen for the app
        super.onCreate(savedInstanceState);
        // creates the view as per activity_main.xml
        setContentView(R.layout.activity_main);

        // button used to submit the user input
        Button submitButton = (Button) findViewById(R.id.submit_button);

        // add click listener on the submit button
        submitButton.setOnClickListener(new View.OnClickListener() {

            /** specifies what to do when the submit button is clicked */
            public void onClick(View v) {
                // captures the city name
                String city_name_input = ((EditText) findViewById(R.id.city_name_input)).getText().toString();
                // captures the state name
                String state_name_input = ((EditText) findViewById(R.id.state_name_input)).getText().toString();
                // the input is moved from this class to "RestaurantListActivity" class
                Intent intent = new Intent(MainActivity.this, RestaurantListActivity.class);
                // the city name is passed as a parameter to "RestaurantListActivity" class
                intent.putExtra("cityName", city_name_input);
                // the state name is passed as a parameter to "RestaurantListActivity" class
                intent.putExtra("stateName", state_name_input);
                //start the activity connect to the specified class
                startActivity(intent);
            }
        });
    }
}
