/**
 * Name: Sai Manogna Pentyala
 * Andrew: spentyal
 * Task: Android App
 * Last Modified: April 4, 2020
 *
 * This class calls the web service to
 * retrieve restaurant list and
 * list of images from a restaurant.
 */

package com.spentyal.andrew.restaurantfinderandroidapp;

// imports to create the android app

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * retrieves the restaurant details
 */
public class GetRestaurant {

    // captures the list of restaurants in a city
    RestaurantListActivity resList = null;
    // captures the list of pictures of a restaurant
    RestaurantDetailsActivity resDetails = null;

    /**
     * search method to find the restaurants list in a city
     */
    public void search(String cityName, String stateName, RestaurantListActivity restaurantListActivity) {
        // captures the list of restaurants in a city
        this.resList = restaurantListActivity;
        // calls the search method asynchronously
        new AsyncZomatoResListSearch().execute(cityName, stateName);
    }

    /**
     * search method to find the restaurants pictures
     */
    public void searchForPicture(String cityName, String stateName, String resName, RestaurantDetailsActivity restaurantDetailsActivity) {
        // captures the list of pictures of a restaurant
        this.resDetails = restaurantDetailsActivity;
        // calls the search method asynchronously
        new AsyncZomatoResDtlsSearch().execute(cityName, stateName, resName);
    }

    /*
     * AsyncTask provides a simple way to use a thread separate from the UI thread in which to do network operations.
     * doInBackground is run in the helper thread.
     * onPostExecute is run in the UI thread, allowing for safe UI updates.
     */
    private class AsyncZomatoResListSearch extends AsyncTask<String, Void, List<String>> {
        // runs in the helper thread
        protected List<String> doInBackground(String... urls) {
            return search(urls[0], urls[1]);
        }

        // used to populate the restaurants on the view
        protected void onPostExecute(List<String> restaurantList) {
            // if list of restaurants has been retrieved
            if(restaurantList != null && !restaurantList.isEmpty()) {
                // show a success messgae
                resList.populateRestaurantList(restaurantList, "Success");
            } else {
                // show an error message
                resList.populateRestaurantList(restaurantList, "Error");
            }
        }

        /*
         * calls the webservice to retrieve the list of restaurants in a city
         */
        private List<String> search(String cityName, String stateName) {

            // captures the restaurant list
            List<String> restaurantList = new ArrayList<String>();
            // connection object
            HttpURLConnection conn;
            // status of the connection request
            int status = 0;
            // response from the connection request
            String response = "";

            try {

                // to replace the special character so that it can be parsed correctly in the url
                cityName = cityName.replaceAll("&", "%26");
                // to replace the special character so that it can be parsed correctly in the url
                stateName = stateName.replaceAll("&", "%26");

                // to replace the special character so that it can be parsed correctly in the url
                cityName = cityName.replaceAll("\'", "%27");
                // to replace the special character so that it can be parsed correctly in the url
                stateName = stateName.replaceAll("\'", "%27");

                // to replace the special character so that it can be parsed correctly in the url
                cityName = cityName.replaceAll(" ", "%20");
                // to replace the special character so that it can be parsed correctly in the url
                stateName = stateName.replaceAll(" ", "%20");

                // to replace the special character so that it can be parsed correctly in the url
                cityName = cityName.replaceAll(",", "%2C");
                // to replace the special character so that it can be parsed correctly in the url
                stateName = stateName.replaceAll(",", "%2C");

                // makes a call to the web service be passing the city name and state name
                URL url = new URL("https://enigmatic-refuge-48961.herokuapp.com/getRestaurantList?cityName=" + cityName + "&stateName=" + stateName);
                // opens a connection
                conn = (HttpURLConnection) url.openConnection();
                // set request method to GET
                conn.setRequestMethod("GET");
                // set the response to be accepted in json format
                conn.setRequestProperty("Accept", "application/json");
                // wait for response
                status = conn.getResponseCode();

                // If things went poorly
                if (status != 200) {
                    // captures the error response
                    String msg = conn.getResponseMessage();
                }

                // captures every line in the response
                String output = "";
                // things went well so let's read the response
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                // as long as there is data in the response
                while ((output = br.readLine()) != null) {
                    // captures the response from web service
                    response += output;
                }

                if(response != null) {

                    // creates the json object of the response
                    JSONObject jo = new JSONObject(response);

                    // retrieves the restaurant array
                    JSONArray restaurantArray = jo.getJSONArray("restaurant_list");

                    if(restaurantArray != null && restaurantArray.length() > 0) {
                        // for each restaurant
                        for (int i = 0; i < restaurantArray.length(); i++) {
                            String restaurant = restaurantArray.getString(i);
                            restaurant = restaurant.trim();
                            restaurantList.add(restaurant);
                        }
                    }
                }

                // close the connection
                conn.disconnect();

                // return the list of restaurants
                return restaurantList;

                // handles IO and JSON Exceptions
            } catch (IOException | JSONException e) {
                System.err.println("Fatal transport error: " + e.getMessage());
                e.printStackTrace();
            } catch(Exception e) {
                e.printStackTrace();
            }

            return null;
        }

    }


    /*
     * AsyncTask provides a simple way to use a thread separate from the UI thread in which to do network operations.
     * doInBackground is run in the helper thread.
     * onPostExecute is run in the UI thread, allowing for safe UI updates.
     */
    private class AsyncZomatoResDtlsSearch extends AsyncTask<String, Void, List<Bitmap>> {

        // runs in the helper thread
        protected List<Bitmap> doInBackground(String... urls) {
            return search(urls[0], urls[1], urls[2]);
        }

        // used to populate the restaurants pictures on the view
        protected void onPostExecute(List<Bitmap> restaurantPicList) {
            resDetails.populateRestaurantPictures(restaurantPicList);
        }

        /*
         * calls the webservice to retrieve the list of restaurants pictures
         */
        private List<Bitmap> search(String cityName, String stateName, String resName) {

            // captures the pictures of a restaurant
            List<Bitmap> restaurantPicList = new ArrayList<Bitmap>();
            // connection object
            HttpURLConnection conn;
            // status of the connection request
            int status = 0;
            // response from the connection request
            String response = "";

            try {

                // to replace the special character so that it can be parsed correctly in the url
                cityName = cityName.replaceAll("&", "%26");
                // to replace the special character so that it can be parsed correctly in the url
                stateName = stateName.replaceAll("&", "%26");
                // to replace the special character so that it can be parsed correctly in the url
                resName = resName.replaceAll("&", "%26");

                // to replace the special character so that it can be parsed correctly in the url
                cityName = cityName.replaceAll("\'", "%27");
                // to replace the special character so that it can be parsed correctly in the url
                stateName = stateName.replaceAll("\'", "%27");
                // to replace the special character so that it can be parsed correctly in the url
                resName = resName.replaceAll("\'", "%27");

                // to replace the special character so that it can be parsed correctly in the url
                cityName = cityName.replaceAll(" ", "%20");
                // to replace the special character so that it can be parsed correctly in the url
                stateName = stateName.replaceAll(" ", "%20");
                // to replace the special character so that it can be parsed correctly in the url
                resName = resName.replaceAll(" ", "%20");

                // to replace the special character so that it can be parsed correctly in the url
                cityName = cityName.replaceAll(",", "%2C");
                // to replace the special character so that it can be parsed correctly in the url
                stateName = stateName.replaceAll(",", "%2C");
                // to replace the special character so that it can be parsed correctly in the url
                resName = resName.replaceAll(",", "%2C");

                // makes a call to the web service be passing the city name, state name and restaurant name
                URL url = new URL("https://enigmatic-refuge-48961.herokuapp.com/getRestaurantPhotos?cityName=" + cityName + "&stateName=" + stateName + "&restaurantName=" + resName);
                // opens a connection
                conn = (HttpURLConnection) url.openConnection();
                // set request method to GET
                conn.setRequestMethod("GET");
                // set the response to be accepted in json format
                conn.setRequestProperty("Accept", "application/json");

                // wait for response
                status = conn.getResponseCode();

                // If things went poorly
                if (status != 200) {
                    // captures the error response
                    String msg = conn.getResponseMessage();
                }

                // captures every line in the response
                String output = "";
                // things went well so let's read the response
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                // as long as there is data to read
                while ((output = br.readLine()) != null) {
                    // captures the response from web service
                    response += output;
                }

                if (response != null) {

                    // creates the json object of the response
                    JSONObject jo = new JSONObject(response);
                    // retrieves the restaurant pic array
                    JSONArray restaurantPicArray = jo.getJSONArray("restaurant_pic_list");
                    // for each restaurant
                    for (int i = 0; i < restaurantPicArray.length(); i++) {
                        String pictureURL = restaurantPicArray.getString(i);
                        pictureURL = pictureURL.trim();
                        URL u = new URL(pictureURL);
                        Bitmap restPic = getRemoteImage(u);
                        restaurantPicList.add(restPic);
                    }
                }

                // close the connection
                conn.disconnect();
                // returns the list of pictures of a restaurant
                return restaurantPicList;

                // handles IO Exception
            } catch (IOException | JSONException e) {
                System.err.println("Fatal transport error: " + e.getMessage());
                e.printStackTrace();
                // handles any exception
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    /*
     * Given a URL referring to an image, return a bitmap of that image
     * thanks to android interesting picture lab
     */
    private Bitmap getRemoteImage(final URL url) {
        try {
            // open a connection object
            final URLConnection conn = url.openConnection();
            // establish the connection
            conn.connect();
            // read data from the picture URL
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            // create a bitmap image of the picture URL
            Bitmap bm = BitmapFactory.decodeStream(bis);
            // close the input stream object
            bis.close();
            // return the bitmap image
            return bm;

            // handles the IO Exception
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
