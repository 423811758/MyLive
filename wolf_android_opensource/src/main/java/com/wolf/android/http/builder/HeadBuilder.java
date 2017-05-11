package com.wolf.android.http.builder;

import com.wolf.android.http.request.RequestCall;
import com.wolf.android.http.OkHttpUtils;
import com.wolf.android.http.request.OtherRequest;
import com.wolf.android.http.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
