package com.lq.khttpdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.lq.khttp.KHttp;
import com.lq.khttp.transformer.HttpSchedulersTransformer;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        KHttp.init(this, "https://www.baidu.com/");


        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Disposable disposable = KHttp.get("/", new HashMap<String, Object>())
                        .compose(new HttpSchedulersTransformer<ResponseBody>())
                        .subscribe(new Consumer<ResponseBody>() {
                            @Override
                            public void accept(ResponseBody responseBody) throws Exception {
                                Log.d(TAG, "accept: "+responseBody.string());
                            }
                        });
            }
        });
    }
}