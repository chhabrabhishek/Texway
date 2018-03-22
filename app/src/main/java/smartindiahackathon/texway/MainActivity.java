package smartindiahackathon.texway;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText edt1,edt2;
    FloatingActionButton FAB;
    DataBaseManager dataBaseManager;

    String[] From_To = {"Delhi-Mumbai","Delhi-Gurgaon","Ghaziabad-Pune","Paattya-Budapest","Jdgw-kdgjfg","jhwfdut-kbgjfg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeStatusBarColor();
        edt1 = (EditText) findViewById(R.id.source_edittext);
        edt2 = (EditText) findViewById(R.id.destination_edittext);
        FAB = (FloatingActionButton) findViewById(R.id.fab);
        dataBaseManager = new DataBaseManager(this);

        ListView listView = (ListView) findViewById(R.id.lists);

        CustomAdapter customAdapter = new CustomAdapter();
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
        boolean isInserted = dataBaseManager.insert_data(edt1.getText().toString(),edt2.getText().toString());
        if(isInserted == true){
            Toast.makeText(MainActivity.this,"DATA INSERTED",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(MainActivity.this,"DATA NOT INSERTED",Toast.LENGTH_LONG).show();
        }

    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return From_To.length;
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
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.custom_list,null);
            TextView textView = (TextView) convertView.findViewById(R.id.from_to_to);

            textView.setText(From_To[position]);
            return convertView;
        }
    }

}
