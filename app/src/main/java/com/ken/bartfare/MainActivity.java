package com.ken.bartfare;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener{
    private ArrayAdapter<CharSequence> adapter;
    private String origin;
    private String destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set up Spinner and set the items in spinner
        Spinner origin_spinner = (Spinner) findViewById(R.id.origin_spinner);
        Spinner destination_spinner = (Spinner) findViewById(R.id.destination_spinner);

        adapter = ArrayAdapter.createFromResource(this, R.array.stations_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        origin_spinner.setAdapter(adapter);
        origin_spinner.setOnItemSelectedListener(this);
        destination_spinner.setAdapter(adapter);
        destination_spinner.setOnItemSelectedListener(this);

        //Obtain Button and set a listener for it
        Button sendBtn = (Button) findViewById(R.id.send);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DownloadXmlTask().execute(new String[]{"http://api.bart.gov/api/sched.aspx?cmd=fare&"});
            }
        });

        //Obtain and setup AdView
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Obtain spinner instance created in the xml
        Spinner spinner = (Spinner)parent;
        //Obtain what station the user choice
        String station = spinner.getItemAtPosition(position).toString();
        //stationVar holds the abbreviation of the station
        String stationVar = "";
        switch(station){
            case "12th St. Oakland City Center":
                stationVar = "12TH";
                break;
            case "16th St. Mission":
                stationVar = "16TH";
                break;
            case "19th St. Oakland":
                stationVar = "19TH";
                break;
            case "24th St. Mission":
                stationVar = "24TH";
                break;
            case "Ashby":
                stationVar = "ASHB";
                break;
            case "Balboa Park":
                stationVar = "BALB";
                break;
            case "Bay Fair":
                stationVar = "BAYF";
                break;
            case "Castro Valley":
                stationVar = "CAST";
                break;
            case "Civic Center/UN Plaza":
                stationVar = "CIVC";
                break;
            case "Coliseum/Oakland Airport":
                stationVar = "COLS";
                break;
            case "Colma":
                stationVar = "COLM";
                break;
            case "Concord":
                stationVar = "CONC";
                break;
            case "Daly City":
                stationVar = "DALY";
                break;
            case "Downtown Berkeley":
                stationVar = "DBRK";
                break;
            case "Dublin/Pleasanton":
                stationVar = "DUBL";
                break;
            case "El Cerrito del Norte":
                stationVar = "DELN";
                break;
            case "El Cerrito Plaza":
                stationVar = "PLZA";
                break;
            case "Embarcadero":
                stationVar = "EMBR";
                break;
            case "Fremont":
                stationVar = "FRMT";
                break;
            case "Fruitvale":
                stationVar = "FTVL";
                break;
            case "Glen Park":
                stationVar = "GLEN";
                break;
            case "Hayward":
                stationVar = "HAYW";
                break;
            case "Lafayette":
                stationVar = "LAFY";
                break;
            case "Lake Merritt":
                stationVar = "LAKE";
                break;
            case "MacArthur":
                stationVar = "MCAR";
                break;
            case "Millbrae":
                stationVar = "MLBR";
                break;
            case "Montgomery St.":
                stationVar = "MONT";
                break;
            case "North Berkeley":
                stationVar = "NBRK";
                break;
            case "North Concord/Martinez":
                stationVar = "NCON";
                break;
            case "Oakland Int'l Airport":
                stationVar = "OAKL";
                break;
            case "Orinda":
                stationVar = "ORIN";
                break;
            case "Pittsburg/Bay Point":
                stationVar = "PITT";
                break;
            case "Pleasant Hill/Contra Costa Centre":
                stationVar = "PHIL";
                break;
            case "Powell St.":
                stationVar = "POWL";
                break;
            case "Richmond":
                stationVar = "RICH";
                break;
            case "Rockridge":
                stationVar = "ROCK";
                break;
            case "San Bruno":
                stationVar = "SBRN";
                break;
            case "San Francisco Int'l Airport":
                stationVar = "SFIA";
                break;
            case "San Leandro":
                stationVar = "SANL";
                break;
            case "South Hayward":
                stationVar = "SHAY";
                break;

            case "South San Francisco":
                stationVar = "";
                break;

            case "Union City":
                stationVar = "";
                break;

            case "Walnut Creek":
                stationVar = "";
                break;

            case "West Dublin/Pleasanton":
                stationVar = "WDUB";
                break;

            case "West Oakland":
                stationVar = "WOAK";
                break;
        }
        //obtain which spinner called this method
        //perform task depending on which spinner was chosen
        if(spinner.getId() == R.id.origin_spinner)
        {
            origin = stationVar;
        }
        else if(spinner.getId() == R.id.destination_spinner)
        {
            destination = stationVar;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class DownloadXmlTask extends AsyncTask<String, Void, String> {
        private String data = "";
        @Override
        protected void onPreExecute(){
            try{
                // Set Request parameter
                //searchtext = searchview.getQuery().toString();
                data += URLEncoder.encode("orig", "UTF-8") + "=" + origin.toLowerCase() + "&" + URLEncoder.encode("dest","UTF-8") + "=" + destination + "&key=MW9S-E7SL-26DU-VV8V";

            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
            }
        }
        @Override
        protected String doInBackground(String... urls) {
            InputStream stream = null;
            String fare = "";
            try {
                URL url = new URL(urls[0] + data);
                Log.d("URL",urls[0] + data);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                stream = conn.getInputStream();
                //BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                //StringBuilder sb = new StringBuilder();
                //String line = null;
                //while( (line = reader.readLine()) != null)
                //{
                 //   sb.append(line + "");
                //}
                //Log.d("XML", sb.toString());
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(stream, null);
                parser.nextTag();
                //parser.nextTag();
                Log.d("FIRST TAG",parser.getName());
                parser.require(XmlPullParser.START_TAG, null, "root");
                while (parser.nextTag() != XmlPullParser.END_TAG) {
                    Log.d("Entered root", "ENTERED");
                    String name = parser.getName();
                    // Starts by looking for the entry tag
                    Log.d("NAME", name);
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }

                    if (name.equals("trip")) {
                        parser.require(XmlPullParser.START_TAG, null, "trip");
                        while (parser.next() != XmlPullParser.END_TAG) {
                            Log.d("Entered trip", fare);
                            if (parser.getEventType() != XmlPullParser.START_TAG) {
                                continue;
                            }
                            String name1 = parser.getName();
                            if (name1.equals("fare")) {
                                parser.require(XmlPullParser.START_TAG, null, "fare");
                                fare = readText(parser);
                                Log.d("FARE", fare);
                                parser.require(XmlPullParser.END_TAG, null, "fare");

                            } else {
                                skip(parser);
                            }
                        }
                    } else {
                        Log.d("OUTERSKIP", parser.getName());
                        skip(parser);
                    }
                }
            } catch (IOException e) {
                return getResources().getString(R.string.connection_error);
            } catch (XmlPullParserException e) {
                return getResources().getString(R.string.xml_error);
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return fare;
        }

        @Override
        protected void onPostExecute(String result) {
            TextView tv = (TextView)findViewById(R.id.fare_value);
            tv.setText(result);

        }

        private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                throw new IllegalStateException();
            }
            int depth = 1;
            while (depth != 0) {
                switch (parser.next()) {
                    case XmlPullParser.END_TAG:
                        depth--;
                        break;
                    case XmlPullParser.START_TAG:
                        depth++;
                        break;
                }
            }
        }
        private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
            String result = "";
            if (parser.next() == XmlPullParser.TEXT) {
                result = parser.getText();
                parser.nextTag();
            }
            return result;
        }
    }
}
