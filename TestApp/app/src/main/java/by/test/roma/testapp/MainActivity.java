package by.test.roma.testapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import java.util.List;

import at.theengine.android.simple_rss2_android.SimpleRss2Parser;
import at.theengine.android.simple_rss2_android.SimpleRss2ParserCallback;

public class MainActivity extends FragmentActivity {

    private ProgressDialog progressDialog;

    ContentActivityFragment content;
    ConnectivityManager connManager;
    NetworkInfo mWifi;
    NetworkInfo mMob;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        mMob = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        FragmentTransaction fTrans;
        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            fTrans = getSupportFragmentManager().beginTransaction();
            // Create a new Fragment to be placed in the activity layout
            HelloFragment helloFragment = new HelloFragment();

            content = new ContentActivityFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            helloFragment.setArguments(getIntent().getExtras());

            progressDialog = ProgressDialog.show(this, "",
                    "Update", true);
            if (!mWifi.isConnected() && !mMob.isConnected()) {

            SimpleRss2Parser parser = new SimpleRss2Parser("http://www.onliner.by/feed",
                    new SimpleRss2ParserCallback() {
                        @Override
                        public void onFeedParsed(List<at.theengine.android.simple_rss2_android.RSSItem> items) {
                            for(int i = 0; i < items.size(); i++){
                                by.test.roma.testapp.RSSItem rssItem = new by.test.roma.testapp.RSSItem(items.get(i).getTitle(), items.get(i).getLink().toString(), items.get(i).getDescription(),items.get(i).getDate());
                                rssItem.save();
                                Log.d("SimpleRss2ParserDemo", items.get(i).getTitle());
                            }
                        }
                        @Override
                        public void onError(Exception e) {

                        }
                    });
            parser.parseAsync();
            }
            progressDialog.dismiss();
            fTrans.replace(R.id.fragment_container, helloFragment).addToBackStack("hello");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            fTrans.replace(R.id.fragment_container, content).addToBackStack("content");
            fTrans.commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return (super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refreshContent: {
                FragmentTransaction fTrans1 = getSupportFragmentManager().beginTransaction();
                fTrans1.replace(R.id.fragment_container,content);
                fTrans1.commit();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}