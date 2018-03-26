package smartindiahackathon.texway;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GraphActivity extends AppCompatActivity {

    LineGraphSeries<DataPoint> series;
    SMSReceiver smsReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        smsReceiver = new SMSReceiver();
        double x_cord= -6.0;
        double y_cord= 5.0;

        GraphView graphView = (GraphView) findViewById(R.id.graph_view);
        series = new LineGraphSeries<DataPoint>();
        for(int i=0 ; i < 5000 ; i++){
            x_cord = x_cord + 0.1;
            y_cord = y_cord + 0.2;
            series.appendData(new DataPoint(x_cord,y_cord),true,500);
        }
        graphView.addSeries(series);
    }
}
