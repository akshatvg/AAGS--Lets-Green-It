package arpit.com.farmis;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import arpit.com.farmis.homerv.Item;
import arpit.com.farmis.homerv.Presenter;
import arpit.com.farmis.homerv.RVAdapter;
import arpit.com.farmis.network.DownloadJSON;
import arpit.com.farmis.network.JsonLoaded;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, JsonLoaded {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String URl = "Server Url";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.secToolbar)
    Toolbar secToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.sec_toolbar_close)
    ImageView close;
    @BindView(R.id.fragment_home_recycler)
    RecyclerView recyclerView;
    private BottomSheetBehavior mBottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final Handler handler = new Handler();
        final int delay = 3000; //milliseconds

        DownloadJSON downloadJSON = new DownloadJSON("http://darsh.southindia.cloudapp.azure.com:8080/get/all",
                MainActivity.this);
        downloadJSON.execute();

        handler.postDelayed(new Runnable(){
            public void run(){
                DownloadJSON downloadJSON = new DownloadJSON("Server Url",
                        MainActivity.this);
                downloadJSON.execute();
                handler.postDelayed(this, delay);
            }
        }, delay);

        navigationView.setNavigationItemSelectedListener(this);

        View bottomSheet = findViewById(R.id.home_logs_container);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                secToolbar.setAlpha(slideOffset);
                int min = 0, max = 360;
                //homeToolbarCross.animate().rotationBy(360);
                close.setRotation(slideOffset * 180f);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NestedScrollView nestedScrollView = findViewById(R.id.home_logs_container);
                        nestedScrollView.smoothScrollTo(0, 0);
                        nestedScrollView.smoothScrollTo(0, 0);
                        mBottomSheetBehavior.setState(mBottomSheetBehavior.STATE_COLLAPSED);
                    }
                });
                bottomSheet.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            }
        });


    }

    @OnClick(R.id.fab)
    public void fabClick(View view) {
         new MaterialStyledDialog.Builder(this)
                .setTitle(R.string.dialog_title)
                .setDescription(R.string.dialog_desc)
                .setIcon(R.drawable.ic_supervisor_account_black_24dp)
                .withIconAnimation(true)
                .setPositiveText(R.string.call_now)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:1376936363"));
                        startActivity(callIntent);
                    }})
                .show();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(MainActivity.this, Alert.class);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onJsonLoaded(JSONObject json) {
        Log.d(TAG, "Json => " + json.toString());
        try {
            ParseJSON parseJSON = new ParseJSON(json);
            ArrayList<Float> hums = parseJSON.getJSONArray("humidity");
            ArrayList<Float> temps = parseJSON.getJSONArray("temperature");
            ArrayList<Float> level = parseJSON.getJSONArray("level");
            ArrayList<Float> silo = parseJSON.getJSONArray("silo_level");

            final Item item = new Item(temps, hums, level,silo,4);
            runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    // Stuff that updates the UI
                    loadRVCard(item);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void loadRVCard(Item item) {


        final Presenter[] myListPresenter = {new Presenter(item, this)};
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new RVAdapter(myListPresenter[0], MainActivity.this));
    }
}
