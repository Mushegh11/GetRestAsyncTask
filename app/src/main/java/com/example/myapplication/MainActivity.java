package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import static com.example.myapplication.utils.NetworkUtils.generateURL;
import static com.example.myapplication.utils.NetworkUtils.getResponseFromURL;

public class MainActivity extends AppCompatActivity {

    EditText inputtext;
    Button searchbtn;
    TextView outputtext;

    class VKQueryTask extends AsyncTask<URL,Void,String>
    {
        @Override
        protected String doInBackground(URL... urls) {
            String response=null;
            try {
                //mi hat generatedURL enq uxarkum dra hamar url@ klini 0 indexov
                response = getResponseFromURL(urls[0]);

            } catch (IOException e) {
                e.printStackTrace();
            }
                return response; //return aneluc et string@ nstuma onPostExecute-i parametri mej
        }

        @Override
        protected void onPostExecute(String response) {

            String firstname=null;
            String lastname=null;
            //Parse anenq JSON@ Stringi`
            try {
                JSONObject jsonObject=new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                JSONObject userinfo = jsonArray.getJSONObject(0);
                firstname = userinfo.getString("first_name");
                lastname = userinfo.getString("last_name");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            outputtext.setText(firstname + lastname);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputtext = findViewById(R.id.inputtext);
        outputtext = findViewById(R.id.outputtext);
        searchbtn = findViewById(R.id.Searchbtn);

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                URL genereteURL = generateURL(inputtext.toString());

                new VKQueryTask().execute(genereteURL); //otdelniy patokov


            }
        });


    }
}
