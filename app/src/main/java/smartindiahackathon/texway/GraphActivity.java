package smartindiahackathon.texway;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

public class GraphActivity extends AppCompatActivity {

    LineGraphSeries<DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GraphView graphView = (GraphView) findViewById(R.id.graph_view);
        series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,100),
                new DataPoint(100,80),
                new DataPoint(200,180),
                new DataPoint(290,90),
                (new DataPoint(400,50))
                //new DataPoint(500,300)
        });
        series.setThickness(20);
        //series.setAnimated(true);
        series.setColor(Color.parseColor("#666666"));
        graphView.setBackgroundColor(Color.parseColor("#ffffff"));
        graphView.addSeries(series);
        graphView.getViewport().setScrollable(true);
        graphView.getViewport().setScrollableY(true);
        graphView.getViewport().setScalableY(true);
        graphView.getViewport().setScalable(true);
        graphView.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graphView.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graphView.getGridLabelRenderer().setGridColor(Color.BLACK);
        graphView.getGridLabelRenderer().setPadding(0);

        final PointsGraphSeries<DataPoint> pgs = new PointsGraphSeries<>(new DataPoint[] {
                new DataPoint(200, 180)
        });

        graphView.addSeries(pgs);
        pgs.setShape(PointsGraphSeries.Shape.POINT);
        pgs.setColor(Color.parseColor("#3090c7"));
        pgs.setSize(25);

        Thread timer = new Thread(){
            public void run(){
                try {

                    for (int l=0; l<90; l++) {
                        int y = (180 - l);
                        int x = (200 + l);

                        pgs.resetData(new DataPoint[]{
                                new DataPoint(x, y)
                        });
                        sleep(80);

                        //Log.d("Teja", String.valueOf(l));
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        timer.start();

    }

}


