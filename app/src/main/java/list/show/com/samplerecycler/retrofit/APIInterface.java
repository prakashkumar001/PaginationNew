package list.show.com.samplerecycler.retrofit;


import org.json.JSONObject;

import java.util.List;

import list.show.com.samplerecycler.Product;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface APIInterface {
    String BASE_URL = "http://www.boolfox.com";


    @GET
    Call<List<Product>> getAllProductList(@Url String url);



    /*@GET("/api/users?")
    Call<UserList> doGetUserList(@Query("page") String page);

    @FormUrlEncoded
    @POST("/api/users?")
    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);*/
}