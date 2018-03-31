package smartindiahackathon.texway;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private String API_ADDR = "http://192.168.43.241:5000/map";

    EditText edt1,edt2;
    FloatingActionButton FAB;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeStatusBarColor();
        edt1 = (EditText) findViewById(R.id.source_edittext);
        edt2 = (EditText) findViewById(R.id.destination_edittext);
        FAB = (FloatingActionButton) findViewById(R.id.fab);
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy  HH:mm");
        date = simpleDateFormat.format(calendar.getTime());
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);
        ListView listView = (ListView) findViewById(R.id.lists);
        CustomAdapter customAdapter = new CustomAdapter(this);
        listView.setAdapter(customAdapter);
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#3E85F7"));
        }
    }

    public void switch_text(View view){
        String var;
        var = edt2.getText().toString();
        edt2.setText(edt1.getText().toString());
        edt1.setText(var);

    }

    public void AddData(View view){
//        GpsLocation gpsLocation = new GpsLocation(getApplicationContext());
//        Location location = gpsLocation.getLocation();
//        if (location!=null){
//            String latitude = Double.toString(location.getLatitude());
//            String longitude = Double.toString(location.getLongitude());
//            SmsManager smsManager = SmsManager.getDefault();
//            smsManager.sendTextMessage("+918368119807",null,"Latitude : "+latitude+"\n"+"Longitude : "+longitude,null,null);
//        }
//
//        DataBaseManager dataBaseManager = new DataBaseManager(this);
//        if (edt1.getText().toString().trim().length()==0 || edt2.getText().toString().trim().length()==0) {
//            Toast.makeText(getApplicationContext(),"TextBox can't be empty",Toast.LENGTH_SHORT).show();
//        }
//        else{
//            boolean insert_status = dataBaseManager.insert_data(edt1.getText().toString(), edt2.getText().toString(), date);
//            if (insert_status == true) {
//                Toast.makeText(getApplicationContext(), "Rows are added", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(getApplicationContext(), "Addition unsuccessful", Toast.LENGTH_SHORT).show();
//
//            }
//        }
        this.onSubmit(edt1.getText().toString(), edt2.getText().toString());
    }

    public void onSubmit(String from, String to) {
        final Context thisContext = this;

        String url = Uri.parse(API_ADDR)
                .buildUpon()
                .appendQueryParameter("from", from)
                .appendQueryParameter("to", to)
                .build().toString();

        JsonArrayRequest req = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    ArrayList<Float[]> all = new ArrayList<>();

                    for (int l=0; l < response.length(); l++) {
                        JSONArray internal = response.getJSONArray(l);
                        JSONArray iinternal = internal.getJSONArray(1);
                        all.add(new Float[] { (float)iinternal.getDouble(0),(float)iinternal.getDouble(1)  });
                        Log.d("C", String.valueOf(iinternal.get(0)));
                    }

                    GraphActivity.arrayList = all;

                    Intent intent = new Intent(MainActivity.this,GraphActivity.class);
                    startActivity(intent);

                    Toast.makeText(thisContext, "Got!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(thisContext, "Failed Acquiring Route Info", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(req);
    }


    class CustomAdapter extends BaseAdapter{
        private DataBaseManager dataBaseManager;
        private ArrayList<String[]> allRows;

        CustomAdapter(Context context) {
            super();

            allRows = new ArrayList<>();
            dataBaseManager = new DataBaseManager(context);

            Cursor cursor = dataBaseManager.getAlldata();
            while(cursor.moveToNext()){
                allRows.add(new String[]{
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)
                });
            }
        }

        @Override
        public int getCount() {
            return allRows.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public int getItemViewType(int position) {
            return position == 0?0:1;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(getItemViewType(position) == 0) {
                return getLayoutInflater().inflate(R.layout.list_header,null);
            }

            // except for the header
            position = position-1;

            convertView = getLayoutInflater().inflate(R.layout.custom_list,null);
            TextView textView = (TextView) convertView.findViewById(R.id.from);
            TextView textView1 = (TextView) convertView.findViewById(R.id.to);
            TextView textView2 = (TextView) convertView.findViewById(R.id.from2);
            TextView textView3 = (TextView) convertView.findViewById(R.id.tos);
            TextView recentElTimestamp = (TextView) convertView.findViewById(R.id.recent_list_el_timestamp);
            textView.setText(allRows.get(position)[0]);
            textView1.setText(allRows.get(position)[1]);
            textView2.setText(allRows.get(position)[2]);
            textView3.setText(allRows.get(position)[3]);
            recentElTimestamp.setText(allRows.get(position)[4]);
            return convertView;
        }
    }


}
