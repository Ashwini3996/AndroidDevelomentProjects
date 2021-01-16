package com.example.ashwinipalve.mynewsapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.alexvasilkov.foldablelayout.FoldableListLayout;
import com.example.ashwinipalve.mynewsapp.R;
import com.example.ashwinipalve.mynewsapp.activity.HomeActivity;
import com.example.ashwinipalve.mynewsapp.pojo.HomeNews;
import com.example.ashwinipalve.mynewsapp.pojo.HomePojoResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ashwini Palve on 15/05/2018.
 */

public class HomeNewsAdapter extends BaseAdapter
{
    ArrayList<HomeNews> mItemList;
    Context mContext;
    @BindView(R.id.news_img) protected ImageView mImgNews;


    public HomeNewsAdapter(ArrayList<HomeNews> mItemList, Context mContext) {
        this.mItemList = mItemList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_news_item, parent, false);

        ButterKnife.bind(this, itemView);


        Picasso.with(mContext)
                .load(mItemList.get(position).getUrl())
                .fit()
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(mImgNews);



        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(mContext,"id"+mItemList.get(position).getId(),Toast.LENGTH_LONG).show();

            }
        });


        return itemView;


    }
}
