package com.example.fotagclcyau;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
    implements RatingBar.OnRatingBarChangeListener {
    private static final String STATE_IMAGES = "images";
    public static final String IMAGE_MESSAGE = "com.example.fotagclcyau.image";

    private Toolbar mToolBar;
    private RatingBar mRatingBar;
    private RecyclerView mRecyclerView;
    private ImageAdapter mImageAdapter;
    private ArrayList<ImageObject> mImageObjectList;
    private ImageView mClearRating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null){
            mImageObjectList = savedInstanceState.getParcelableArrayList(STATE_IMAGES);
        }

        setContentView(R.layout.activity_main);

//        toolbar
        mToolBar = this.findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

//        ratingbar
        mRatingBar = this.findViewById(R.id.rating);
        mRatingBar.setOnRatingBarChangeListener(this);
//        clear ratingbar
        mClearRating = this.findViewById(R.id.clear_rating);
        mClearRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRatingBar.setRating(0);
            }
        });

//        recycler view
        if (mRecyclerView == null) {
            mRecyclerView = this.findViewById(R.id.recycler_view);
            setRecyclerView(this.getResources().getConfiguration());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        Save custom state objects
        outState.putParcelableArrayList(STATE_IMAGES, mImageObjectList);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.refresh:
                System.out.println("Refresh");
                loadImages();
            case R.id.clear:
                System.out.println("Clear");
                removeImages();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void removeImages(){
        mImageObjectList = new ArrayList<>();
        mImageAdapter.setImageList(mImageObjectList);
        mImageAdapter.notifyDataSetChanged();
    }

    private void loadImages(){
        LoadFromWeb loadFromWeb = new LoadFromWeb();
        loadFromWeb.execute();
    }

    private void setRecyclerView(Configuration configuration){
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager;
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            layoutManager = new GridLayoutManager(this, 2);
        } else {
            layoutManager = new GridLayoutManager(this, 1);
        }

        if (mImageObjectList == null) {
            mImageObjectList = new ArrayList<>();
        }
        mRecyclerView.setLayoutManager(layoutManager);

//        Adapter
        mImageAdapter = new ImageAdapter(this, mImageObjectList);
        mRecyclerView.setAdapter(mImageAdapter);
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        mImageAdapter.getFilter().filter(String.valueOf(v));
    }

    public class LoadFromWeb extends AsyncTask<ArrayList<ImageObject>, Integer, ArrayList<ImageObject>> {
        private static final String IMG_RESOURCE = "https://www.student.cs.uwaterloo.ca/~cs349/s19/assignments/images/";

        @Override
        protected ArrayList doInBackground(ArrayList<ImageObject>... lists) {
            mImageObjectList = new ArrayList<>();
            mImageAdapter.setImageList(mImageObjectList);
            try {
                Document document = Jsoup.connect(IMG_RESOURCE).get();
                Elements elements = document.select("a");
                for (Element element : elements) {
                    String image = element.attr("href");
                    if (image.endsWith("png") || image.endsWith("jpg")){
                        String imageLoc = element.absUrl("href");
                        mImageObjectList.add(new ImageObject(imageLoc, 0)
                        );
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList results) {
            mImageAdapter.notifyDataSetChanged();
        }
    }
}
