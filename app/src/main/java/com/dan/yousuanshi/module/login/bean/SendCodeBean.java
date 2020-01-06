package com.dan.yousuanshi.module.login.bean;

import per.goweii.rxhttp.request.base.BaseBean;

public class SendCodeBean extends BaseBean {
    /**
     * 短信验证码id
     */
    private String smsId;

    public String getSmsId() {
        return smsId;
    }

    public void setSmsId(String smsId) {
        this.smsId = smsId;
    }
}
