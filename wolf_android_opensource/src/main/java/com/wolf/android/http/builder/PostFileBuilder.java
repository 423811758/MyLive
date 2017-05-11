package com.wolf.android.http.builder;

import java.io.File;

import okhttp3.MediaType;

import com.wolf.android.http.request.RequestCall;
import com.wolf.android.http.request.PostFileRequest;
import com.wolf.android.http.request.RequestCall;

/**
 * Created by zhy on 15/12/14.
 */
public class PostFileBuilder extends OkHttpRequestBuilder<PostFileBuilder>
{
    private File file;
    private MediaType mediaType;


    public OkHttpRequestBuilder file(File file)
    {
        this.file = file;
        return this;
    }

    public OkHttpRequestBuilder mediaType(MediaType mediaType)
    {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build()
    {
        return new PostFileRequest(url, tag, params, headers, file, mediaType,id).build();
    }


}
