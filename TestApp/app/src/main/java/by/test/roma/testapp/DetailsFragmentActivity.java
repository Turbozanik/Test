package by.test.roma.testapp;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * Created by Roma on 08.10.2015.
 */
public class DetailsFragmentActivity extends Fragment implements View.OnClickListener{
    FragmentTransaction fTrans;
    ContentActivityFragment content;
    DetailsFragmentActivity details;
    RSSItem rssItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        details = new DetailsFragmentActivity();
        content = new ContentActivityFragment();
        fTrans = getFragmentManager().beginTransaction();

        return inflater.inflate(R.layout.details_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        int myInt = bundle.getInt("id", 0);

        rssItem = RSSItem.findById(RSSItem.class, (long) myInt);

        ImageView detailsInView = (ImageView)view.findViewById(R.id.detailsImView);
        TextView title = (TextView)view.findViewById(R.id.detailsTitleTv);
        TextView url = (TextView)view.findViewById(R.id.detailsLinkTv);
        TextView description = (TextView)view.findViewById(R.id.textView);
        Button redirectButton = (Button)view.findViewById(R.id.siteReditrectButton);

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                 getActivity().getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);


        String[] splitedArray = rssItem.getDescription().split("<p>");


        String s = "<img src=\"";
        int ix = rssItem.getDescription().indexOf(s)+s.length();
        String imageUrl = rssItem.getDescription().substring(ix, rssItem.getDescription().indexOf("\"", ix + 1));


        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.drawable.icon1)
                .showImageOnFail(R.drawable.icon1)
                .showImageOnLoading(R.drawable.icon1).build();


//download and display image from url
        imageLoader.displayImage(imageUrl, detailsInView, options);

        redirectButton.setOnClickListener(this);
        if(rssItem.getDescription()!=null) {
            description.setText(splitedArray[2].replace("</p>", " "));
            Log.d("123", splitedArray[0]+"||"+splitedArray[1]+"||"+splitedArray[2].replace("</p>"," ")+"||");
        }
        title.setText(rssItem.getTitle());
        url.setText(rssItem.getLink());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                fTrans.replace(R.id.fragment_container,content);
                fTrans.commit();
                break;
            }
            case R.id.refreshContent: {break;}
        }
        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        ActionBar ab = getActivity().getActionBar();
        MenuItem item = menu.findItem(R.id.refreshContent);
        item.setVisible(false);
        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
//        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onClick(View v) {
        String url = rssItem.getLink();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
