package com.back4app.back4appandroid9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import java.util.HashMap;
import java.util.ArrayList;
import com.parse.ParseAnalytics;

import java.lang.reflect.Array;
import java.util.Map;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public ArrayList<String> dataList = new ArrayList<String>();
    public String[] myArray = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        final Button retrieve_data = findViewById(R.id.btnRetrieveData);

        retrieve_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myArray = new String[]{};
                final ListView listView = (ListView) findViewById(R.id.listviewA);
                HashMap<String, String> params = new HashMap();
                ParseCloud.callFunctionInBackground("listAllUserEmails", params, new FunctionCallback<ArrayList>() {

                    @Override
                    public void done(ArrayList object, ParseException e) {
                        if (e == null) {
                            // ratings is 4.5
                            Log.d("It's working! :)", object.toString());
                        }
                    }
                });

                myArray = dataList.toArray(new String[dataList.size()]);
                ArrayAdapter<String> adapterList
                        = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_single_choice, myArray);

                listView.setAdapter(adapterList);
            }
        });

    }
}
