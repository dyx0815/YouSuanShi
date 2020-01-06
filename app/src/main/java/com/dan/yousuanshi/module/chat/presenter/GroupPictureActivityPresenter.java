package com.dan.yousuanshi.module.chat.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.chat.bean.GroupFileBean;
import com.dan.yousuanshi.module.chat.http.ChatRequest;
import com.dan.yousuanshi.module.chat.view.GroupPictureActivityView;

import java.util.HashMap;
import java.util.Map;

import per.goweii.rxhttp.request.base.BaseBean;
import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class GroupPictureActivityPresenter extends BasePresenter<GroupPictureActivityView> {
    public void getGroupPicture(int groupId,int page,int pageSize){
        Map<String,Object> map = new HashMap<>();
        map.put("fileType",4);
        map.put("groupId",groupId);
        map.put("page",page);
        map.put("pageSize",pageSize);
        addToRxLife(ChatRequest.getGroupFile(map, new RequestListener<GroupFileBean>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int code, GroupFileBean data) {
                if(isAttach()){
                    getBaseView().getGroupPictureSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().getGroupPictureFailed(code,msg);
                }
            }

            @Override
            public void onError(ExceptionHandle handle) {

            }

            @Override
            public void onFinish() {
            }
        }));
    }

    public void deleteGroupFile(Map<String,Object> map){
        addToRxLife(ChatRequest.deleteGroupFile(map, new RequestListener<BaseBean>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int code, BaseBean data) {
                if(isAttach()){
                    getBaseView().deleteGroupFileSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().deleteGroupFileFailed(code,msg);
                }
            }

            @Override
            public void onError(ExceptionHandle handle) {

            }

            @Override
            public void onFinish() {
            }
        }));
    }
}
