package com.dan.yousuanshi.utils;

import com.qiniu.android.common.FixedZone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;

public class UploadManagerUtil {

    private static UploadManager uploadManager = null;

    private UploadManagerUtil(){

    }

    public static UploadManager getInstance(){
        if(uploadManager == null){
            Configuration config = new Configuration.Builder()
                    .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
                    .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
                    .connectTimeout(5)           // 链接超时。默认10秒
                    .useHttps(true)               // 是否使用https上传域名
                    .responseTimeout(60)          // 服务器响应超时。默认60秒
//                .recorder(recorder)           // recorder分片上传时，已上传片记录器。默认null
//                .recorder(recorder, keyGen)   // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                    .zone(FixedZone.zone2)// 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                    .dns(null)
                    .build();
            uploadManager = new UploadManager(config,3);
        }
        return uploadManager;
    }
}
