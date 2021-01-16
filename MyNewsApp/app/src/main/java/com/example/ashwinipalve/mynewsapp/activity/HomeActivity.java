package com.example.ashwinipalve.mynewsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.alexvasilkov.foldablelayout.FoldableListLayout;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ashwinipalve.mynewsapp.R;
import com.example.ashwinipalve.mynewsapp.adapter.HomeNewsAdapter;
import com.example.ashwinipalve.mynewsapp.pojo.HomeNews;
import com.example.ashwinipalve.mynewsapp.pojo.HomePojoResponse;
import com.example.ashwinipalve.mynewsapp.support.EndPoint;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    ArrayList<HomeNews> homeNewsPojos = new ArrayList<>();
    @BindView(R.id.foldable_list)
     FoldableListLayout mNewsList;
    HomeNewsAdapter homeNewsAdapter;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
     getSupportActionBar().hide();
        ButterKnife.bind(this);
       // homeNewsAdapter = new HomeNewsAdapter(homeNewsPojos, this);
       // mNewsList.setAdapter(homeNewsAdapter);


      /*  HomePojoResponse pojo = new HomePojoResponse("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-HKO8qwCgBNGEdbxoW7z51BIDRd8ni1gbaJORgO8MbWX9DHRB");
        HomePojoResponse pojo1=new HomePojoResponse("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQKzfLd87SIls2APD7csxZhVBOb-B_VNGfnXlVJ6ufmAjPsCOmB");
        homeNewsPojos.add(pojo);
        homeNewsPojos.add(pojo1);*/
       // homeNewsAdapter.notifyDataSetChanged();


        init();
        MakeImageCall();


    }


    private void init()
    {
        queue= Volley.newRequestQueue(this);
    }


    public void MakeImageCall()
    {
        String url=EndPoint.BASE_URL+"?id";
        Log.e("url",url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>(){



                    @Override
                    public void onResponse(JSONObject response) {


                       Log.d("urlr", response.toString());

                        Gson gson=new Gson();
                       HomePojoResponse  pojo1=gson.fromJson(response.toString(),HomePojoResponse.class);



                        final HomeNews[] array=pojo1.getData();
                        for(HomeNews item:array)
                        {
                          // String ImgId=item.getId();

                          homeNewsPojos.add(item);





                            Log.d("info",item.getId());
                            Log.d("details ",item.getUrl());



                        }
                        homeNewsAdapter = new HomeNewsAdapter(homeNewsPojos,HomeActivity.this);
                        mNewsList.setAdapter(homeNewsAdapter);
                        homeNewsAdapter.notifyDataSetChanged();






                    }


                },new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("Details", error.toString());
                    }
                });



        queue.add(jsObjRequest);



    }
}

/*
myVideoView = (VideoView) findViewById(R.id.surface_view);


        try
        {

            myVideoView.setVideoURI(Uri.parse("http://www.MY_DOMAIN_NAME.com/videos/video1.mp4"));
            myVideoView.setMediaController(new MediaController(this));
            myVideoView.requestFocus();
            myVideoView.start();
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), "No Media found", Toast.LENGTH_LONG).show();
        }



  VideoView v;
 MediaController mediaController;
 ProgressDialog progressDialog;
then

public void playvideo(String videopath) {
    Log.e("entered", "playvide");
    Log.e("path is", "" + videopath);
    try {
        progressDialog = ProgressDialog.show(VideoPlay.this, "",
                "Buffering video...", false);
        progressDialog.setCancelable(true);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        mediaController = new MediaController(VideoPlay.this);

        Uri video = Uri.parse(videopath);
        v.setMediaController(mediaController);
        v.setVideoURI(video);

        v.setOnPreparedListener(new OnPreparedListener() {

            public void onPrepared(MediaPlayer mp) {
                progressDialog.dismiss();
                v.start();
            }
        });

    } catch (Exception e) {
        progressDialog.dismiss();
        System.out.println("Video Play Error :" + e.getMessage());
    }

}




 */
























  /*
    *  private void makeCall()
    {
        String url="http://narmware.com/kp/api/frame.php";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Details", response.toString());


                        Gson gson=new Gson();
                        ServiceDemoPojo1 pojo1=gson.fromJson(response.toString(),ServiceDemoPojo1.class);
                        arrayList=new ArrayList<>();

                        final ServiceDemoPojo[] array=pojo1.getData();
                        for(ServiceDemoPojo item:array)
                        {
                          arrayList.add(item);

                          Log.d("Details",item.getFrame_id());
                          Log.d("Details",item.getFrame_title());
                        }

                 ServiceDemoAdapter adapter=new ServiceDemoAdapter(ServiceDemoActivity.this,arrayList);
                        listView=findViewById(R.id.list_item);
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
                            {
                                Toast.makeText(ServiceDemoActivity.this,"Id"+arrayList.get(position).getFrame_id(),Toast.LENGTH_SHORT).show();


                            }
                        });


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("Details", error.toString());
                    }
                });

        queue.add(jsObjRequest);
    }
}
*/