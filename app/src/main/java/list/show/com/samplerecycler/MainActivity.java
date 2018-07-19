package list.show.com.samplerecycler;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import list.show.com.samplerecycler.retrofit.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.util.logging.Logger.global;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    MoviesAdapter adapter;
    List<String> data;
    int mLoadedItems=0;
    Button submit;
    int currentPage=0;
    List<Product> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data=new ArrayList<>();
        productList=new ArrayList<>();
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        progressBar=(ProgressBar) findViewById(R.id.item_progress_bar);
        submit=(Button)findViewById(R.id.submit);
        adapter=new MoviesAdapter(MainActivity.this,productList,"list");
          linear();
        addDataToList();
        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                addDataToList();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grid();
                adapter=new MoviesAdapter(MainActivity.this,productList,"grid");
                recyclerView.setAdapter(adapter);
            }
        });
    }

    public void linear()
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        //linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    public void grid()
    {
        GridLayoutManager linearLayoutManager = new GridLayoutManager(MainActivity.this,2);
        //linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void addDataToList() {
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /*for (int i = 0; i <= 30; i++) {
                    data.add("SampleText : " + mLoadedItems);
                    mLoadedItems++;
                }*/
                load();
                progressBar.setVisibility(View.GONE);
            }
        }, 1500);

    }

    private void load(){
        //Creating a retrofit object
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        //creating the api interface
        APIInterface api = retrofit.create(APIInterface.class);

        //now making the call object
        //Here we are using the api method that we created inside the api interface
        final String page=APIInterface.BASE_URL+"/rest/index.php/htc?p_id="+currentPage;
        Call<List<Product>> call = api.getAllProductList(page);
        call.enqueue(new Callback<List<Product>>() {


            @Override
            public void onResponse(Call<List<Product>> call, retrofit2.Response<List<Product>> response) {




                    if(response.isSuccessful()){

                        productList.addAll(response.body());
                       // linear();
                        //adapter=new MoviesAdapter(MainActivity.this,productList,"list");
                        adapter.notifyDataSetChanged();

                        currentPage=currentPage+1;



                    }else{
                        Log.e(" Response Error "," Response Error "+String.valueOf(response.code()));
                    }

                    //should call the custom method adapter.notifyDataChanged here to get the correct loading status

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("Response Error "," Response Error "+t.getMessage());
            }
        });
    }

}
