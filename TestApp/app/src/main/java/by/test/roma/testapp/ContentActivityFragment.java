package by.test.roma.testapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ContentActivityFragment extends Fragment {

    public List<RSSItem> items;
    private ListView recyclerView;
    LinearLayoutManager manager;

    public ContentActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        rootView.setTag("frag");

        items = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("RssParceItem");
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + list.size() + " recipes");
                    for (ParseObject obj : list) {
                        //initializeData(obj);
                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
                //initializeAdapter();
            }
        });

        recyclerView = (ListView) rootView.findViewById(R.id.listView);
        manager = new LinearLayoutManager(getActivity());

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

}
