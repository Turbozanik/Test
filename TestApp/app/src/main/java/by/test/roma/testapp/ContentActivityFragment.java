package by.test.roma.testapp;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Context;
import android.os.Build;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class ContentActivityFragment extends ListFragment {



    FragmentTransaction fTrans;
    DetailsFragmentActivity detail;
    Context context;
    ListView lvSimple;
    TextView tvId;
    public ContentActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        detail = new DetailsFragmentActivity();
        fTrans = getFragmentManager().beginTransaction();

        rootView.setTag("frag");
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        List<by.test.roma.testapp.RSSItem> rssItems = RSSItem.listAll(by.test.roma.testapp.RSSItem.class);
        by.test.roma.testapp.RSSItem.findAll(by.test.roma.testapp.RSSItem.class);

        Log.d("listSize",rssItems.toString());

        final String ATTRIBUTE_NAME_ID = "ID";
        final String ATTRIBUTE_NAME_TITLE = "text";
        final String ATTRIBUTE_NAME_LINK = "checked";
        final String ATTRIBUTE_NAME_DESCRIPTION = "edscription";
        final String ATTRIBUTE_NAME_PUBDATE = "date";

        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        Map<String, Object> m;
        for (int i = 0; i < rssItems.size()-1; i++) {
            m = new HashMap<String, Object>();
            m.put(ATTRIBUTE_NAME_ID,rssItems.get(i).getId());
            m.put(ATTRIBUTE_NAME_TITLE, rssItems.get(i).getTitle());
            m.put(ATTRIBUTE_NAME_LINK, rssItems.get(i).getLink());
            m.put(ATTRIBUTE_NAME_DESCRIPTION, rssItems.get(i).getDescription());
            m.put(ATTRIBUTE_NAME_PUBDATE, rssItems.get(i).getDate());
            data.add(m);
        }

        // массив имен атрибутов, из которых будут читаться данные
        String[] from = { ATTRIBUTE_NAME_TITLE,ATTRIBUTE_NAME_ID,ATTRIBUTE_NAME_PUBDATE};
        // массив ID View-компонентов, в которые будут вставлять данные
        int[] to = { R.id.contentListItemTitle,R.id.contentListItemId,R.id.contentListPubDate};

        // создаем адаптер
        SimpleAdapter sAdapter = new SimpleAdapter(getActivity(), data, R.layout.content_list_view_item,
                from, to);

        // определяем список и присваиваем ему адаптер
        setListAdapter(sAdapter);

    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        ActionBar ab = getActivity().getActionBar();
        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
    }
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        TextView numb = (TextView)v.findViewById(R.id.contentListItemId);

        Bundle args = new Bundle();
        args.putInt("id", Integer.parseInt(numb.getText().toString()));
        detail.setArguments(args);

        fTrans.replace(R.id.fragment_container, detail);
        fTrans.commit();
        //Toast.makeText(getActivity(), "position = " + numb.getText(), Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.refreshContent: {

                break;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
