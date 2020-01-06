package com.dan.yousuanshi.module.mine.activity;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MvpPresenter;
import com.dan.yousuanshi.utils.DataCleanManager;
import com.dan.yousuanshi.utils.FileUtils;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.Utils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class ClearActivity extends BaseActivity {

    @BindView(R.id.tv_size)
    TextView tvSize;
    @BindView(R.id.tv_clear)
    TextView tvClear;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_clear;
    }

    @Nullable
    @Override
    protected MvpPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        try {
            caculateCacheSize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.ll_back, R.id.tv_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_clear:
                try {
                    DataCleanManager.cleanApplicationData(Utils.getAppContext());
                    File file = new File(getFilesDir().getAbsolutePath());
                    DataCleanManager.deleteDirectory(file);
                    ToastUtils.showToast(this,"清理成功");
                    finish();
                }catch (Exception e){
                    ToastUtils.showToast(this,"清理失败");
                }
                break;
        }
    }

    /**
     * 计算缓存的大小
     */
    private void caculateCacheSize() {
        long fileSize = 0;
        String cacheSize = "0KB";
        File filesDir = getActivity().getFilesDir();
        File cacheDir = getActivity().getCacheDir();

        fileSize += FileUtils.getDirSize(filesDir);
        fileSize += FileUtils.getDirSize(cacheDir);
        if (fileSize > 0){
            cacheSize = FileUtils.formatFileSize(fileSize);
            tvClear.setEnabled(true);
            tvClear.setText("立即清理");
        }
        tvSize.setText(cacheSize);
    }


}
