package manqaro.projectz.Cartoonizer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import java.util.ArrayList;

import manqaro.projectz.Cartoonizer.Adapters.RecyclerAdapter;
import manqaro.projectz.R;

public class CartoonizeActvity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> imageSet;
    private ImageView mainImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        initFields();
        initViews();
        setRecyclerView();
    }

    private void initViews(){
        mainImageView = (ImageView) findViewById(R.id.mainImageView);
        mRecyclerView = (RecyclerView) findViewById(R.id.effectRec);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mAdapter = new RecyclerAdapter(imageSet,this,mainImageView);
    }

    private void initFields(){
        imageSet = new ArrayList<>();
        fillImageSet();
    }

    private void setRecyclerView(){
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void fillImageSet(){
        imageSet.add("#AD5858");
        imageSet.add("#752525");
        imageSet.add("#C96D20");
        imageSet.add("#5F44A8");
        imageSet.add("#710022");
        imageSet.add("#AD5858");
        imageSet.add("#752525");
        imageSet.add("#C96D20");
        imageSet.add("#5F44A8");
    }
}
