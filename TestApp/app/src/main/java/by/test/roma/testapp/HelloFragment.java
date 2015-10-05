package by.test.roma.testapp;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
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
    final ParseObject parseItem = new ParseObject("RssParceItem");
    List <ParseObject> parseList = new ArrayList<ParseObject>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        progressDialog = ProgressDialog.show(getActivity(), "",
                "Update", true);

        SimpleRss2Parser parser = new SimpleRss2Parser("http://www.onliner.by/feed",
                new SimpleRss2ParserCallback() {
                    @Override
                    public void onFeedParsed(List<RSSItem> items) {
                        for(int i = 0; i < items.size(); i++){
                            parseItem.put("Title",items.get(i).getTitle());
                            parseItem.put("Link",items.get(i).getLink().toString());
                            parseItem.put("Description",items.get(i).getDescription());
                            parseList.add(parseItem);
                            Log.d("SimpleRss2ParserDemo", items.get(i).getTitle());
                        }
                        try {
                            ParseObject.pinAll(parseList);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        parser.parseAsync();

        progressDialog.dismiss();

        return inflater.inflate(R.layout.hello_fragment, container, false);
    }
}
