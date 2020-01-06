package com.dan.yousuanshi.module.chat.activity;

import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.common.Constant;
import com.dan.yousuanshi.module.chat.adapter.GroupFileAdapter;
import com.dan.yousuanshi.module.chat.bean.ChatBean;
import com.dan.yousuanshi.module.chat.bean.ChatUserInfoBean;
import com.dan.yousuanshi.module.chat.bean.GroupFileBean;
import com.dan.yousuanshi.module.chat.bean.QiniuTokenBean;
import com.dan.yousuanshi.module.chat.presenter.GroupFileActivityPresenter;
import com.dan.yousuanshi.module.chat.view.GroupFileActivityView;
import com.dan.yousuanshi.utils.DateUtil;
import com.dan.yousuanshi.utils.FileItem;
import com.dan.yousuanshi.utils.FileUtils;
import com.dan.yousuanshi.utils.ListUtil;
import com.dan.yousuanshi.utils.MD5Utils;
import com.dan.yousuanshi.utils.PinyinUtil;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UploadManagerUtil;
import com.dan.yousuanshi.utils.UserUtils;
import com.dan.yousuanshi.utils.popupwindow.BackgroundDarkPopupWindow;
import com.google.gson.Gson;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import per.goweii.rxhttp.download.DownloadInfo;
import per.goweii.rxhttp.download.RxDownload;
import per.goweii.rxhttp.request.base.BaseBean;

public class GroupFileActivity extends BaseActivity<GroupFileActivityPresenter> implements GroupFileActivityView, OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.tv_sort)
    TextView tvSort;
    //    @BindView(R.id.iv_time_sort)
//    ImageView ivTimeSort;
//    @BindView(R.id.tv_time_sort)
//    TextView tvTimeSort;
//    @BindView(R.id.iv_choose_time)
//    ImageView ivChooseTime;
//    @BindView(R.id.iv_name_sort)
//    ImageView ivNameSort;
//    @BindView(R.id.tv_name_sort)
//    TextView tvNameSort;
//    @BindView(R.id.iv_choose_name)
//    ImageView ivChooseName;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_add_group_file)
    ImageView ivAddGroupFile;
    @BindView(R.id.rl_group_file)
    RelativeLayout rlGroupFile;
    @BindView(R.id.tv_add_announcement)
    TextView tvAddAnnouncement;
    @BindView(R.id.ll_not_group_file)
    LinearLayout llNotGroupFile;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;

    private int groupId;
    private boolean isMaster;
    private Dialog dialog;
    private Dialog uploadDialog;
    private BackgroundDarkPopupWindow popupWindow;
    private List<GroupFileBean.DataBean> fileList = new ArrayList<>();
    private GroupFileAdapter adapter;
    private int page = 1;
    private int pageSize = 20;
    private int position;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_file;
    }

    @Nullable
    @Override
    protected GroupFileActivityPresenter initPresenter() {
        return new GroupFileActivityPresenter();
    }

    @Override
    protected void initView() {
        groupId = getIntent().getIntExtra("data", 0);
        isMaster = getIntent().getBooleanExtra("isMaster", false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GroupFileAdapter(this, fileList, isMaster);
        adapter.setOnItemClick(new GroupFileAdapter.OnItemClick() {
            @Override
            public void llMoreClick(int position) {
                GroupFileActivity.this.position = position;
                showDialog1();
            }

            @Override
            public void lookFile(GroupFileBean.DataBean dataBean) {
                if (FileUtils.isExists(getApplicationContext().getFilesDir().getAbsolutePath() + "/download/file/" + dataBean.getFile_name())) {
                    startFile(FileUtils.getDocType(dataBean.getFile_name()), getApplicationContext().getFilesDir().getAbsolutePath() + "/download/file/" + dataBean.getFile_name());
                } else {
                    downLoadFile(dataBean).start();
                }
            }
        });
        recyclerView.setAdapter(adapter);
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadMoreListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        fileList.clear();
        getFileList();
    }

    @Override
    public void getGroupFileSuccess(int code, GroupFileBean data) {
        if (data.getData().size() == 0 && page == 1) {
            rlGroupFile.setVisibility(View.GONE);
            llNotGroupFile.setVisibility(View.VISIBLE);
        } else {
            for (GroupFileBean.DataBean item : data.getData()) {
                item.setHeadLetter(PinyinUtil.getHeadChar(item.getFile_name()));
            }
            fileList.addAll(data.getData());
            ListUtil.sortGroupFile(fileList);
            adapter.notifyDataSetChanged();
            llNotGroupFile.setVisibility(View.GONE);
            rlGroupFile.setVisibility(View.VISIBLE);
        }
        if(data.getData().size()<pageSize){
            smartRefreshLayout.setEnableLoadMore(false);
        }else{
            smartRefreshLayout.setEnableLoadMore(true);
        }
        smartRefreshLayout.finishLoadMore();
        smartRefreshLayout.finishRefresh();
    }

    @Override
    public void getGroupFileFailed(int code, String msg) {
        ToastUtils.showToast(this, msg);
    }

    @Override
    public void deleteGroupFileSuccess(int code, BaseBean data, int position) {
        fileList.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyDataSetChanged();
        if(fileList.size() == 0){
            llNotGroupFile.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void deleteGroupFileFailed(int code, String msg) {
        ToastUtils.showToast(this, msg);
    }

    @Override
    public void getQiniuTokenSuccess(int code, QiniuTokenBean data,FileItem fileItem) {
        UploadManager uploadManager = UploadManagerUtil.getInstance();
        uploadManager.put(fileItem.getFilePath(), Constant.QINIU_FILE + MD5Utils.getMd5Code(""+UserUtils.getInstance().getUserBean().getUser_id()
                + fileItem.getFileName())+"."+FileUtils.getExtensionName(fileItem.getFilePath()), data.getToken(), new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                if (info.isOK()) {
                    Log.e("qiniu", "上传成功!!!!!!!");
                    Log.e("qiniu", "key:" + key);
                    Map<String,Object> map = new HashMap<>();
                    map.put("groupId",groupId);
                    map.put("fileType",3);
                    map.put("fileName",fileItem.getFileName());
                    map.put("fileSuffix",FileUtils.getExtensionName(fileItem.getFileName()));
                    map.put("fileUrl",key);
                    map.put("fileSize",fileItem.getSize());
                    presenter.addGroupFile(map);
                } else {
                    Log.e("qiniu", "上传失败!!!!!!!");
                    Log.e("qiniu", "json:" + response + "\t" + info.error);
                }
            }
        }, null);
    }

    @Override
    public void getQiniuTokenFailed(int code, String msg) {
        ToastUtils.showToast(this,msg);
    }

    @Override
    public void addGroupFileSuccess(int code, BaseBean baseBean) {
        page = 1;
        onResume();
    }

    @Override
    public void addGroupFileFailed(int code, String msg) {

    }

    @OnClick({R.id.ll_back, R.id.ll_sort, R.id.iv_add_group_file})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_sort:
                showPopupWindow();
            case R.id.iv_add_group_file:
                if (popupWindow == null || (!popupWindow.isShowing())) {
                    showUploadDialog();
                }
                break;
        }
    }

    private void showDialog1() {
        if (dialog == null) {
            dialog = new Dialog(this, R.style.friend_info_Dialog);
            dialog.setContentView(R.layout.dialog_group_file);
            WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
            layoutParams.gravity = Gravity.BOTTOM;
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
            dialog.getWindow().setAttributes(layoutParams);
            dialog.findViewById(R.id.tv_forward).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    FileItem fileItem = new FileItem();
                    fileItem.setSize(fileList.get(position).getFile_size());
                    fileItem.setFileName(fileList.get(position).getFile_name());
                    ChatBean fileChat = new ChatBean(0, new Gson().toJson(fileItem), UserUtils.getInstance().getUserBean().getUser_id(), 0
                            , Constant.CHAT_FILE, 1, UserUtils.getInstance().getUserBean().getUser_portrait()
                            , new ChatUserInfoBean(UserUtils.getInstance().getUserBean().getNick_name(), UserUtils.getInstance().getUserBean().getUser_portrait())
                            , DateUtil.dateToString(new Date()), null);
                    fileChat.setTemp(fileList.get(position).getFile_url().replace(Constant.DOWNLOAD_URL,""));
                    Intent intent = new Intent(getActivity(),ChooseActivity.class);
                    intent.putExtra("chatData",fileChat);
                    startActivity(intent);
                }
            });
            dialog.findViewById(R.id.tv_rename).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Intent intent = new Intent(getActivity(),UpdateGroupFileActivity.class);
                    intent.putExtra("fileId",fileList.get(position).getId());
                    intent.putExtra("groupId",groupId);
                    intent.putExtra("fileEnd","."+FileUtils.getExtensionName(fileList.get(position).getFile_name()));
                    startActivity(intent);
                }
            });
            dialog.findViewById(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    List<Integer> list = new ArrayList<>();
                    list.add(fileList.get(position).getId());
                    Map<String, Object> map = new HashMap<>();
                    map.put("fileId", new Gson().toJson(list));
                    map.put("groupId", groupId);
                    presenter.deleteGroupFile(map, position);
                }
            });
            dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.findViewById(R.id.rl_all).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }

    private void showUploadDialog() {
        if (uploadDialog == null) {
            uploadDialog = new Dialog(this, R.style.friend_info_Dialog);
            uploadDialog.setContentView(R.layout.dialog_upload_group_file);
            WindowManager.LayoutParams layoutParams = uploadDialog.getWindow().getAttributes();
            layoutParams.gravity = Gravity.BOTTOM;
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            uploadDialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
            uploadDialog.getWindow().setAttributes(layoutParams);
            uploadDialog.findViewById(R.id.tv_upload).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadDialog.dismiss();
                    startActivityForResult(new Intent(getActivity(),MyFileActivity.class),1);
                }
            });
            uploadDialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadDialog.dismiss();
                }
            });
            uploadDialog.findViewById(R.id.rl_all).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadDialog.dismiss();
                }
            });
        }
        uploadDialog.show();
    }

    private void showPopupWindow() {
        if (popupWindow == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_group_file_sort_window, null);
            popupWindow = new BackgroundDarkPopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setTouchable(true);//设置可以触摸
            popupWindow.setOutsideTouchable(true);//点击外面可以隐藏window
            popupWindow.drakFillView(rlGroupFile);
            TextView tvTimeSort = view.findViewById(R.id.tv_time_sort);
            TextView tvNameSort = view.findViewById(R.id.tv_name_sort);
            ImageView ivTimeSort = view.findViewById(R.id.iv_time_sort);
            ImageView ivNameSort = view.findViewById(R.id.iv_name_sort);
            ImageView ivChooseTime = view.findViewById(R.id.iv_choose_time);
            ImageView ivChooseName = view.findViewById(R.id.iv_choose_name);
            view.findViewById(R.id.ll_time_sort).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    tvSort.setText("时间排序");
                    tvTimeSort.setTextColor(getColor(R.color.color_F99E05));
                    tvNameSort.setTextColor(getColor(R.color.color_999999));
                    ivChooseTime.setVisibility(View.VISIBLE);
                    ivChooseName.setVisibility(View.GONE);
                    ivTimeSort.setImageResource(R.mipmap.icon_time_sort);
                    ivNameSort.setImageResource(R.mipmap.icon_name_sort_false);
                    ListUtil.sortGroupFile(fileList);
                    adapter.notifyDataSetChanged();
                }
            });
            view.findViewById(R.id.ll_name_sort).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    tvSort.setText("名称排序");
                    tvTimeSort.setTextColor(getColor(R.color.color_999999));
                    tvNameSort.setTextColor(getColor(R.color.color_F99E05));
                    ivChooseTime.setVisibility(View.GONE);
                    ivChooseName.setVisibility(View.VISIBLE);
                    ivTimeSort.setImageResource(R.mipmap.icon_time_sort_false);
                    ivNameSort.setImageResource(R.mipmap.icon_name_sort);
                    Collections.sort(fileList);
                    adapter.notifyDataSetChanged();
                }
            });
        }
        popupWindow.showAsDropDown(tvSort);
    }

    private RxDownload downLoadFile(GroupFileBean.DataBean dataBean) {
        DownloadInfo downloadInfo = DownloadInfo.create(dataBean.getFile_url()
                , getApplicationContext().getFilesDir().getAbsolutePath() + "/download/file/"
                , dataBean.getFile_name());
        RxDownload rxDownload = RxDownload.create(downloadInfo).setDownloadListener(new RxDownload.DownloadListener() {
            @Override
            public void onStarting(DownloadInfo info) {
                Log.e("DownLoad", "下载开始：" + info.url);
                presenter.showLoadingDialog();
            }

            @Override
            public void onDownloading(DownloadInfo info) {
                Log.e("DownLoad", "正在下载");
            }

            @Override
            public void onStopped(DownloadInfo info) {
                Log.e("DownLoad", "下载停止");
                presenter.dismissLoadingDialog();
            }

            @Override
            public void onCanceled(DownloadInfo info) {
                Log.e("DownLoad", "下载取消");
                presenter.dismissLoadingDialog();
            }

            @Override
            public void onCompletion(DownloadInfo info) {
                Log.e("DownLoad", "下载完成");
                presenter.dismissLoadingDialog();
                startFile(FileUtils.getDocType(dataBean.getFile_name()), getApplicationContext().getFilesDir().getAbsolutePath() + "/download/file/" + dataBean.getFile_name());
            }

            @Override
            public void onError(DownloadInfo info, Throwable e) {
                Log.e("DownLoad", "下载错误：" + e.toString());
                presenter.dismissLoadingDialog();
            }
        });
        return rxDownload;
    }

    private void startFile(int fileType, String path) {
        if (fileType == 1) {//文本文件
            startActivity(FileUtils.getTextFileIntent(path));
        } else if (fileType == 2) {//doc
            startActivity(FileUtils.getWordFileIntent(path));
        } else if (fileType == 3) {//xls
            startActivity(FileUtils.getExcelFileIntent(path));
        } else if (fileType == 4) {//ppt
            startActivity(FileUtils.getPptFileIntent(path));
        }
    }

    private void getFileList() {
        Map<String, Object> map = new HashMap<>();
        map.put("fileType", 3);
        map.put("groupId", groupId);
        map.put("pageSize", pageSize);
        map.put("page", page);
        presenter.getGroupFile(map);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == 3){
            List<FileItem> fileList = data.getParcelableArrayListExtra("fileData");
            for(FileItem item : fileList){
                presenter.getQiniuToken(item);
            }
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        fileList.clear();
        page = 1;
        getFileList();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        getFileList();
    }
}
