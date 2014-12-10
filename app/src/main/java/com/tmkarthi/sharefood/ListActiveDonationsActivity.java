package com.tmkarthi.sharefood;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;


public class ListActiveDonationsActivity extends ListActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Donation[] donations = new Donation[]{
                new Donation("Marriage", 10), new Donation("Some left overs", 20)
        };
        setListAdapter(new DonationListAdapter(this, R.layout.view_list_donations, Arrays.asList(donations)));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_active_donations, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String getEncodedCredentials(String username, String password) {
        return Base64.encodeToString((username + ":" + password).getBytes(),
                Base64.DEFAULT);
    }

    public class DonationListAdapter extends ArrayAdapter<Donation> {

        private final LayoutInflater layoutInflater;

        public DonationListAdapter(Context context, int resource, List<Donation> objects) {
            super(context, resource, objects);
            layoutInflater = getLayoutInflater();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.view_list_donations, parent, false);
            }
            TextView description = (TextView) convertView.findViewById(R.id.donatedFoodDescription);
            TextView quantity = (TextView) convertView.findViewById(R.id.donatedFoodQuantity);
            Donation donation = getItem(position);
            description.setText(donation.getDescription());
            quantity.setText("" + donation.getQuantity());
            return convertView;
        }
    }


/*
    class GetListDonationTask extends AsyncTask<String, Void, String> {

        private Exception exception;

        protected String doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url
                        .openConnection();
                try {
                    urlConnection.setDoOutput(true);
                    urlConnection.setRequestMethod("GET");
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
*/

}
