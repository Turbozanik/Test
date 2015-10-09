package by.test.roma.testapp;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

import at.theengine.android.simple_rss2_android.RSSItem;
import at.theengine.android.simple_rss2_android.SimpleRss2Parser;
import at.theengine.android.simple_rss2_android.SimpleRss2ParserCallback;


/**
 * Created by Roma on 05.10.2015.
 */
public class HelloFragment extends Fragment {

    private ProgressDialog progressDialog;

    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        progressDialog = ProgressDialog.show(getActivity(), "",
                "Update", true);

        SimpleRss2Parser parser = new SimpleRss2Parser("http://www.onliner.by/feed",
                new SimpleRss2ParserCallback() {
                    @Override
                    public void onFeedParsed(List<RSSItem> items) {
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

        progressDialog.dismiss();

        return inflater.inflate(R.layout.hello_fragment, container, false);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

}
