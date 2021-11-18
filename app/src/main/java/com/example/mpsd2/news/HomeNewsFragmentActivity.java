package com.example.mpsd2.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mpsd2.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeNewsFragmentActivity extends Fragment{

    String api = "b4904842e33645bc9ec0b619dd8dc3c3";
    ArrayList<ModelClass> modelClassArrayList;
    NewsAdapterToDisplay adapter;
    String country = "my";
    private RecyclerView recyclerViewOfHome;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_home_news_fragment,null);

        recyclerViewOfHome=view.findViewById(R.id.recyclerViewForHomeFragment);
        modelClassArrayList=new ArrayList<>();
        recyclerViewOfHome.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new NewsAdapterToDisplay(getContext(),modelClassArrayList);
        recyclerViewOfHome.setAdapter(adapter);


        findNews();
        return view;

    }

    private void findNews() {
       // Call<GetNews> call = (Call<GetNews>) ApiUtilities.getApiInterface();

        ApiUtilities.getApiInterface().getNews(country,30,api).enqueue(new Callback<GetNews>() {
            @Override
            public void onResponse(Call<GetNews> call, Response<GetNews> response) {
                if(response.isSuccessful()){
                    modelClassArrayList.addAll(response.body().getArticles());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<GetNews> call, Throwable t) {

            }
        });
    }
}
