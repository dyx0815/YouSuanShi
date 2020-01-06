package com.dan.yousuanshi.dao.bean;

public class OftenModelBean {
    private int modelId;
    private int clickCount;

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public OftenModelBean(int modelId, int clickCount) {
        this.modelId = modelId;
        this.clickCount = clickCount;
    }
}
