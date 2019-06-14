package arpit.com.farmis.homerv;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import arpit.com.farmis.ChartData;
import arpit.com.farmis.R;

public class RVViewHolder extends RecyclerView.ViewHolder implements DataLoaded {
    // ImageView imageView;
    Context context;
    LineChart lineChart;
    TextView stats, max, curr, min, emptyTV;
    ImageView imageView, emptyIW;


    public RVViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        lineChart = itemView.findViewById(R.id.line_chart);
        stats = itemView.findViewById(R.id.stats_title);
        max = itemView.findViewById(R.id.max);
        curr = itemView.findViewById(R.id.cur);
        min = itemView.findViewById(R.id.min);
        imageView = itemView.findViewById(R.id.info_image);
        this.context = context;
    }


    @Override
    public void onDataLoaded(ArrayList<Float> values, String name) {
        //check card type and load data accor.

        if (values.isEmpty()) {

            if (name.equalsIgnoreCase("humidity")) {
                imageView.setImageResource(R.drawable.ic_humidity);
            } else if (name.equalsIgnoreCase("temperature")) {
                imageView.setImageResource(R.drawable.ic_thermometer);
            } else if (name.equalsIgnoreCase("level")) {
                imageView.setImageResource(R.drawable.ic_arrow_up_water_level);
            } else if (name.equalsIgnoreCase("silo level")) {
//            imageView.setImageResource(R.drawable.ic_humidity);
            }

        } else {
            List<Entry> entries = new ArrayList<Entry>();
            ArrayList<ChartData> chartDatas = new ArrayList<>();

            Date date = new Date();   // given date
            Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
            calendar.setTime(date);   // assigns calendar to given date
            int hour = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
            int position = 24 - hour;

            List<Float> valueList = values.subList(position, values.size());
            System.out.print("pos=> " + position + " hour=> " + hour);
            stats.setText(name);


            if (name.equalsIgnoreCase("humidity")) {
                imageView.setImageResource(R.drawable.ic_humidity);
                max.setText(context.getString(R.string.max) + " " + Collections.max(valueList) + "g/m3");
                curr.setText(context.getString(R.string.current) + " " + values.get(values.size() - 1) + "g/m3");
                min.setText(context.getString(R.string.min) + " " + Collections.min(valueList) + "g/m3");

            } else if (name.equalsIgnoreCase("temperature")) {
                imageView.setImageResource(R.drawable.ic_thermometer);
                max.setText(context.getString(R.string.max) + " " + Collections.max(valueList) + context.getString(R.string.celsius));
                curr.setText(context.getString(R.string.current) + " " + values.get(values.size() - 1) + context.getString(R.string.celsius));
                min.setText(context.getString(R.string.min) + " " + Collections.min(valueList) + context.getString(R.string.celsius));

            } else if (name.equalsIgnoreCase("level")) {
                imageView.setImageResource(R.drawable.ic_arrow_up_water_level);
                max.setText(context.getString(R.string.max) + " " + Collections.max(valueList));
                curr.setText(context.getString(R.string.current) + " " + values.get(values.size() - 1));
                min.setText(context.getString(R.string.min) + " " + Collections.min(valueList));

            } else if (name.equalsIgnoreCase("silo level")) {
                imageView.setImageResource(R.drawable.ic_silo);
                max.setText(context.getString(R.string.max) + Collections.max(valueList));
                curr.setText(context.getString(R.string.current) + values.get(values.size() - 1));
                min.setText(context.getString(R.string.min) + Collections.min(valueList));

            }

            for (int i = 0; i < hour; i++) {
                chartDatas.add(new ChartData(i, valueList.get(i)));
            }

            for (ChartData chartData : chartDatas) {

                // turn your data into Entry objects
                entries.add(new Entry(chartData.getX(), chartData.getY()));
                LineDataSet dataSet = new LineDataSet(entries, "Values"); // add entries to dataset
                dataSet.setColor(Color.GREEN);
                dataSet.setValueTextColor(Color.RED); // styling, ...
                LineData lineData = new LineData(dataSet);
                lineData.setDrawValues(false);
                lineChart.setData(lineData);

                lineChart.getXAxis().setEnabled(true);
                lineChart.getAxisLeft().setDrawAxisLine(true);
                lineChart.getAxisLeft().setLabelCount(5, true);
                lineChart.getAxisLeft().setDrawGridLines(false);
                lineChart.getXAxis().setDrawGridLines(false);
                lineChart.getAxisRight().setDrawAxisLine(false);
                lineChart.getAxisRight().setDrawGridLines(false);
                lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                lineChart.setDrawGridBackground(false);
                lineChart.setDrawBorders(false);
                lineChart.setAutoScaleMinMaxEnabled(true);
                lineChart.getXAxis().setTextColor(Color.rgb(0, 145, 234));

                YAxis leftAxis = lineChart.getAxisLeft();
                leftAxis.setEnabled(true);
                YAxis rightAxis = lineChart.getAxisRight();
                rightAxis.setEnabled(false);
                Legend legend = lineChart.getLegend();
                legend.setEnabled(false);
                lineChart.setBackgroundColor(Color.WHITE);
                // dataSet.setColor(Color.rgb(0, 145, 234));

                lineChart.setClickable(false);

                dataSet.setValueTextSize(5f);
//        set.setColor();
                lineChart.setDragEnabled(false);
                lineChart.setScaleEnabled(false);
                lineChart.setScaleXEnabled(false);
                lineChart.setScaleYEnabled(false);
                lineChart.setPinchZoom(false);
                lineChart.setTouchEnabled(false);
                lineChart.setDragEnabled(false);
                lineChart.setScaleEnabled(false);
                lineChart.setScaleXEnabled(false);
                lineChart.setScaleYEnabled(false);
                lineChart.setPinchZoom(false);
                dataSet.setDrawFilled(true);

                if (Utils.getSDKInt() >= 18) {
                    // fill drawable only supported on api level 18 and above
                    Drawable drawable = ContextCompat.getDrawable(context, R.drawable.chart_gradient);
                    dataSet.setFillDrawable(drawable);
                } else {
                    dataSet.setFillColor(Color.BLUE);
                }

//        chart.getAxisLeft().setTextColor(Color.parseColor("#D45B00")); // left y-axis
                lineChart.getXAxis().setTextSize(14f);
                lineChart.getXAxis().setTextColor(Color.parseColor("#3F51B5"));
                lineChart.getXAxis().setYOffset(10f);
                lineChart.setExtraBottomOffset(15f);
//        chart.getLegend().setTextColor(Color.parseColor("#D45B00"));
                dataSet.setColor(Color.parseColor("#3f51b5"));
//        set.setValueTextColor(Color.parseColor("#D45B00"));
                dataSet.setValueTextSize(15f);
                lineChart.getDescription().setEnabled(false);
                lineChart.getDescription().setEnabled(false);

                lineChart.getAxisRight().setInverted(true);

//        chart.setRenderer(new CurvedBarChartRenderer(chart, chart.getAnimator(), chart.getViewPortHandler()));

                lineChart.notifyDataSetChanged();

                lineChart.invalidate(); // refresh
            }
        }

    }
}
