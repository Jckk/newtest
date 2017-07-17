package Retrefit;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import Util.Url;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/6/1.
 */
//Retrofit的初始化
public class RetrofitHelper {
    private static final int CONNECT_TIME_OUT = 60;
    private static final int WRITE_TIME_OUT = 60;
    private static final int READ_TIME_OUT = 60;
    private Context context;
    OkHttpClient client = new OkHttpClient();
   // GsonConverterFactory factory = GsonConverterFactory.create(new GsonBuilder().create());
    private static RetrofitHelper instance = null;
    private Retrofit mRetrofit = null;
    //实例化retrofithelper
    public static RetrofitHelper getInstance(Context context){
        if (instance == null){
            instance = new RetrofitHelper(context);
        }
        return instance;
    }
    private RetrofitHelper(Context mContext){
        context = mContext;
        init();
    }

    private void init() {
        resetApp();
    }

    private void resetApp() {
        OkHttpClient.Builder clientBuilder=client.newBuilder()
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT,TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT,TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true);
        client=clientBuilder.build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Url.MAIN_URL)//通用的url
                .client(client)
               // .addConverterFactory(factory)
                .addConverterFactory(GsonConverterFactory.create())//Gson转换工厂
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//与Rxjava结合
                .build();
    }
    public RetrefitApi getServer(){
        return mRetrofit.create(RetrefitApi.class);//实例化接口类refitapi
    }

    static Interceptor interceptor=new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request=chain.request();
            Response response=chain.proceed(request);
            Log.i("info", "请求体"+request.url());
            return response;
        }
    };
}
