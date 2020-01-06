package com.dan.yousuanshi.module.chat.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MvpPresenter;
import com.dan.yousuanshi.module.chat.adapter.MyFileAdapter;
import com.dan.yousuanshi.utils.FileItem;
import com.dan.yousuanshi.utils.FileManager;
import com.dan.yousuanshi.utils.FileUtils;
import com.dan.yousuanshi.utils.LoadingDialog;
import com.dan.yousuanshi.utils.MyHanlder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyFileActivity extends BaseActivity {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.btn_sure)
    Button btnSure;
    @BindView(R.id.ll_file_null_error)
    LinearLayout llFileNullError;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private int REQUESTCODE_FROM_ACTIVITY = 1000;
    private MyFileAdapter adapter;
    private List<FileItem> filesByType = new ArrayList<>();
    private LoadingDialog mLoadingDialog = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_file;
    }

    @Nullable
    @Override
    protected MvpPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        adapter = new MyFileAdapter(this, filesByType);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClick(new MyFileAdapter.OnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                FileItem fileItem = filesByType.get(position);
                if (fileItem.getFileType() == 1) {//文本文件
                    startActivity(FileUtils.getTextFileIntent(fileItem.getFilePath()));
                } else if (fileItem.getFileType() == 2) {//doc
                    startActivity(FileUtils.getWordFileIntent(fileItem.getFilePath()));
                } else if (fileItem.getFileType() == 3) {//xls
                    startActivity(FileUtils.getExcelFileIntent(fileItem.getFilePath()));
                } else if (fileItem.getFileType() == 4) {//ppt
                    startActivity(FileUtils.getPptFileIntent(fileItem.getFilePath()));
                }
            }
        });
    }


    @Override
    protected void loadData() {
        showLoadingDialog();
        MyHanlder.getInstance().postDelayed(()->{
            filesByType.addAll(FileManager.getInstance(this).getFilesByType(FileUtils.TYPE_DOC));
            dismissLoadingDialog();
            adapter.notifyDataSetChanged();
            if(filesByType.size() == 0){
                btnSure.setVisibility(View.GONE);
            }
        },100);
    }


    @OnClick({R.id.ll_back, R.id.btn_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.btn_sure:
                Intent intent = new Intent(this,ChatActivity.class);
                intent.putParcelableArrayListExtra("fileData", (ArrayList<? extends Parcelable>) adapter.getCheckedFile());
                setResult(3,intent);
                finish();
                break;
        }
    }

}
