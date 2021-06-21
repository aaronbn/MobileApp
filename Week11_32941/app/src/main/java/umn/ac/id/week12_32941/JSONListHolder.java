package umn.ac.id.week12_32941;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JSONListHolder {

    @GET("posts")
    Call<List<Post>> getPost();
}
