package com.tmkarthi.sharefood;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {

    private static final String TAG = "com.tmkarthi.sharefood.MainActivity";
    private AlertDialog progressDialog;
    private EditText editText;
    private EditText quantityText;
    private EditText pickupAddress1Text;
    private EditText pickupAddress2Text;
    private EditText pickupCityText;
    private EditText pickupPhoneText;
    private EditText postalCodeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.donatedFoodDescription);
        quantityText = (EditText) findViewById(R.id.donatedFoodQuantity);
        pickupAddress1Text = (EditText) findViewById(R.id.donatedFoodPickupAddress1);
        pickupAddress2Text = (EditText) findViewById(R.id.donatedFoodPickupAddress2);
        pickupCityText = (EditText) findViewById(R.id.donatedFoodPickupCity);
        pickupPhoneText = (EditText) findViewById(R.id.donatedFoodPickupPhone);
        postalCodeText = (EditText) findViewById(R.id.donatedFoodPostalCode);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void submitDonation(View view) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "submitDonation(...)");
        }
        Button b = (Button) findViewById(R.id.donatedFoodSubmitDonation);
        b.setClickable(false);

        String description = String.valueOf(editText.getText());
        String quantity = String.valueOf(quantityText.getText());
        String pickupAddress1 = String.valueOf(pickupAddress1Text.getText());
        String pickupAddress2 = String.valueOf(pickupAddress2Text.getText());
        String pickupCity = String.valueOf(pickupCityText.getText());
        String pickupPhone = String.valueOf(pickupPhoneText.getText());
        String pickupPostalCode = String.valueOf(postalCodeText.getText());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog
        builder.setView(inflater.inflate(R.layout.progress_dialog, null));
        progressDialog = builder.create();
        progressDialog.setTitle("Creating Donation");
        progressDialog.show();

        if (BuildConfig.DEBUG) {
            Log.d(TAG, "submitDonation(...): About to create SubmitDonationTask");
        }
        new SubmitDonationTask(description, quantity, pickupAddress1, pickupAddress2, pickupCity, pickupPostalCode, pickupPhone).execute("http://10.0.2.2:8000/api/v1/food/donations/");
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "submitDonation(...): About to create SubmitDonationTask");
        }
    }

    private String getEncodedCredentials(String username, String password) {
        return Base64.encodeToString((username + ":" + password).getBytes(),
                Base64.DEFAULT);
    }

    public void listDonations(View view) {
        Intent intent = new Intent(getApplicationContext(), ListActiveDonationsActivity.class);
        startActivity(intent);
    }

    class SubmitDonationTask extends AsyncTask<String, Void, String> {

        private Exception exception;
        private String description;
        private String quantity;
        private String pickupAddress1;
        private String pickupAddress2;
        private String pickupCity;
        private String pickupPostalCode;
        private String pickupPhone;

        public SubmitDonationTask(String description, String quantity,
                String pickupAddress1, String pickupAddress2,
                String pickupCity, String pickupPostalCode, String pickupPhone) {
                    this.description = description;
                    this.quantity = quantity;
                    this.pickupAddress1 = pickupAddress1;
                    this.pickupAddress2 = pickupAddress2;
                    this.pickupCity = pickupCity;
                    this.pickupPostalCode = pickupPostalCode;
                    this.pickupPhone = pickupPhone;
        }

        protected String doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url
                        .openConnection();
                try {
                    urlConnection.setDoOutput(true);
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type",
                            "application/json");
                    urlConnection.setRequestProperty("Accept", "application/json");
                    urlConnection.setRequestProperty("Authorization", "Basic "
                            + getEncodedCredentials("kmarudhachalam", "manager"));

                    JSONObject donationJson = new JSONObject();
                    // {"description":
                    // "marriage south indian full meals food", "quantity": 25, "pickup_address_1": "1,
                    // some street", "pickup_address_2": "some
                    // area", "pickup_city": "bangalore", "postal_code": "560100", "phone_number":
                    // "123456"}";" +
                    donationJson.put("description", description)
                            .put("quantity", quantity)
                            .put("pickup_address_1", pickupAddress1)
                            .put("pickup_address_2", pickupAddress2)
                            .put("pickup_city", pickupCity)
                            .put("postal_code", pickupPostalCode)
                            .put("phone_number", pickupPhone);
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
                    writer.write(donationJson.toString());
                    writer.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String response = "";
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response += line;
                    }
                    return response;
                } finally {
                    urlConnection.disconnect();
                }
            } catch (IOException e) {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "SubmitDonationTask: Received an exception: " + e.getMessage());
                    e.printStackTrace();
                }
                exception = e;
            } catch (JSONException e) {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "SubmitDonationTask: Received an exception: " + e.getMessage());
                    e.printStackTrace();
                }
                exception = e;
            }
            return "failed";
        }

        protected void onPostExecute(String response) {
            String message;
            if (exception != null) {
                message = "Some exception occurred";
            } else {
                message = "Successfully created!";
            }
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_LONG;

            progressDialog.cancel();
            Toast.makeText(context, message, duration).show();
        }
    }
}
