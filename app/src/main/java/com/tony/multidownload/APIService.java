package com.tony.multidownload;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface APIService {

    @Streaming
    @GET
    Observable<ResponseBody> startDownLad(@Url String url);
}
