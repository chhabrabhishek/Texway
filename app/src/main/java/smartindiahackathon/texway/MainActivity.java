package smartindiahackathon.texway;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText edt1,edt2;
    FloatingActionButton FAB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeStatusBarColor();
        edt1 = (EditText) findViewById(R.id.source_edittext);
        edt2 = (EditText) findViewById(R.id.destination_edittext);
        FAB = (FloatingActionButton) findViewById(R.id.fab);

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
        Intent intent = new Intent(MainActivity.this,GraphActivity.class);
        startActivity(intent);
        DataBaseManager dataBaseManager = new DataBaseManager(this);
        boolean insert_status = dataBaseManager.insert_data(edt1.getText().toString(),edt2.getText().toString());
        if (insert_status == true){
            Toast.makeText(getApplicationContext(),"Rows are added",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(),"Addition unsuccessful",Toast.LENGTH_SHORT).show();

        }
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
                        String.valueOf(cursor.getLong(5))
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
            position -= 1;

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
