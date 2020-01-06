package com.dan.yousuanshi.module.shared.presenter;

import com.dan.yousuanshi.base.BasePresenter;
import com.dan.yousuanshi.http.RequestListener;
import com.dan.yousuanshi.module.shared.SharedRequest;
import com.dan.yousuanshi.module.shared.bean.TemplateBean;
import com.dan.yousuanshi.module.shared.view.TemplateManagerActivityView;

import java.util.List;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

public class TemplateManagerActivityPresenter extends BasePresenter<TemplateManagerActivityView> {
    public void getTemplate(int companyId){
        addToRxLife(SharedRequest.getTemplate(companyId,new RequestListener<List<TemplateBean>>() {
            @Override
            public void onStart() {
                if(isAttach()){
                    getBaseView().showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(int code, List<TemplateBean> data) {
                if(isAttach()){
                    getBaseView().getTemplateSuccess(code,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if(isAttach()){
                    getBaseView().getTemplateFailed(code,msg);
                }
            }

            @Override
            public void onError(ExceptionHandle handle) {

            }

            @Override
            public void onFinish() {
                if(isAttach()){
                    getBaseView().dismissLoadingDialog();
                }
            }
        }));
    }
}
