package com.wolf.android.tools;

import java.io.IOException;

import SevenZip.SevenZipHelper;

/**
 * <p>Description: 压缩写入文件适配器，在写入压缩文件时被调用</p>
 * Created by wzd on 2016/10/31.
 */
public class SevenZipCompressAdapter implements ICompress {

    @Override
    public byte[] compress(byte[] bytes) throws IOException {
        return SevenZipHelper.Compress(bytes);
    }
}
