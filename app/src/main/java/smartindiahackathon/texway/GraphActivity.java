package smartindiahackathon.texway;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {

    LineGraphSeries<DataPoint> series;
    PointsGraphSeries<DataPoint> pgs;
    public static ArrayList<Float[]> arrayList;
    double latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GraphView graphView = (GraphView) findViewById(R.id.graph_view);
        series = new LineGraphSeries<>(insertDataPoints());

        series.setThickness(15);
        series.setColor(Color.parseColor("#666666"));
        graphView.setBackgroundColor(Color.parseColor("#ffffff"));
        graphView.addSeries(series);
        graphView.getViewport().setScrollable(true);
        graphView.getViewport().setScrollableY(true);
        graphView.getViewport().setScalableY(true);
        graphView.getViewport().setScalable(true);
        graphView.getGridLabelRenderer().setNumVerticalLabels(10);
//        graphView.getGridLabelRenderer().setNumHorizontalLabels(10);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setXAxisBoundsManual(true);
//        graphView.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graphView.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graphView.getGridLabelRenderer().setGridColor(Color.BLACK);
        graphView.getGridLabelRenderer().setPadding(0);

//        GpsLocation gpsLocation = new GpsLocation(getApplicationContext());
//
//        Location location = gpsLocation.getLocation();
//        double lat = location.getLatitude();
//        double lng = location.getLongitude();
//
//        Double[] xy = toCartesian(lat, lng);
//        double x = xy[0];
//        double y = xy[1];
//        Log.d("H", String.valueOf(x));
//        Log.d("H", String.valueOf(y));

        pgs = new PointsGraphSeries<>(new DataPoint[]{
                new DataPoint(2775508.82605f, 12557456.8623f)
        });

        graphView.addSeries(pgs);
        pgs.setShape(PointsGraphSeries.Shape.POINT);
        pgs.setColor(Color.parseColor("#3090c7"));
        pgs.setSize(15);
    }
//        Thread timer = new Thread(){
//            public void run(){
//                try {
//
//                    for (int l=0; l<100; l++) {
//                        int y = 2775508+l;
//                        int x = 12557456+l;
//
//                        pgs.resetData(new DataPoint[]{
//                                new DataPoint(x, y)
//                        });
//                        sleep(500);
//                    }
//                }catch (InterruptedException e){
//                    e.printStackTrace();
//                }
//            }
//        };
//        timer.start();
//}

//    public Double[] toCartesian(Double lat, Double lng) {
//        Log.d("HE", String.valueOf(lat));
//        Log.d("HE", String.valueOf(lng));
//
//        double x = (double) (lng*20037508.34)/180;
//        double y = Math.log(Math.tan((90+lat)*Math.PI/360))/(Math.PI/180);
//        y *= 20037508.34/180;
//
//        return new Double[] { x, y };
//    }

    public DataPoint[] insertDataPoints(){
        int n=arrayList.size();
        DataPoint[] values = new DataPoint[n];
        for(int i=0;i<n;i++){
            Float[] cord = arrayList.get(i);
            DataPoint v = new DataPoint(cord[0],cord[1]);
            values[i] = v;
        }
        return values;
    }

}


