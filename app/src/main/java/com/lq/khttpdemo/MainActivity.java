package com.lq.khttpdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lq.khttp.model.ApiResult;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ApiResult<Person> apiResult = new ApiResult<>();
        apiResult.setCode(0);
        apiResult.setMsg("成功");

        Person person = new Person("张三",18);
        apiResult.setData(person);

        Gson gson = new Gson();

        List<ApiResult<Person>> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            list.add(apiResult);
        }

        String json = gson.toJson(list);

        Log.d(TAG, "onCreate: ============"+json);



        List<ApiResult> apiResult2 =   gson.fromJson(json,new TypeToken<List<ApiResult>>(){}.getType());

        for (ApiResult result : apiResult2) {
            Log.d(TAG, "onCreate: ============"+result.toString());
        }

    }
}
