package arpit.com.farmis.homerv;

import android.content.Context;

import arpit.com.farmis.R;

public class Presenter {
    private final Item item;
    private Context context;

    public Presenter(Item item, Context context) {
        this.item = item;
        this.context = context;
    }

    public void onBindListRowViewAtPosition(int position, DataLoaded rowView) {
        switch (position) {
            case 0:
                rowView.onDataLoaded(item.getHumidity(), context.getString(R.string.humidity));
                break;
            case 1:
                rowView.onDataLoaded(item.getTemps(), context.getString(R.string.temp));
                break;
            case 2:
                rowView.onDataLoaded(item.getLevel(), context.getString(R.string.water_level));
                break;
            case 3:
                rowView.onDataLoaded(item.getSiloLevel(), context.getString(R.string.silo_level));
                break;
        }

    }

    public int getListRowsCount() {
        return item.getCount();
    }
}
