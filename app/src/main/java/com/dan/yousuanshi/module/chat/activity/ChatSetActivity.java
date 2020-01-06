package com.dan.yousuanshi.module.chat.activity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MyApplication;
import com.dan.yousuanshi.common.Constant;
import com.dan.yousuanshi.dao.DataBaseHelper;
import com.dan.yousuanshi.module.addressbook.activity.FriendInfoActivity;
import com.dan.yousuanshi.module.chat.bean.ChatBean;
import com.dan.yousuanshi.module.chat.presenter.ChatSetActivityPresenter;
import com.dan.yousuanshi.module.chat.view.ChatSetActivityView;
import com.dan.yousuanshi.utils.SPUtils;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatSetActivity extends BaseActivity<ChatSetActivityPresenter> implements ChatSetActivityView {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.riv_head_icon)
    RoundedImageView rivHeadIcon;
    @BindView(R.id.tv_info_name)
    TextView tvInfoName;
    @BindView(R.id.s_top)
    Switch sTop;
    @BindView(R.id.s_disturb)
    Switch sDisturb;
    @BindView(R.id.s_translation)
    Switch sTranslation;
    @BindView(R.id.ll_translation_now)
    LinearLayout llTranSlationNow;
    @BindView(R.id.ll_query_chat_record)
    LinearLayout llQueryChatRecord;
    @BindView(R.id.ll_clear_chat_record)
    LinearLayout llClearChatRecord;
    @BindView(R.id.tv_delete)
    TextView tvDelete;

    private ChatBean pUser;
    private Dialog deleteDialog;
    private Dialog clearDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat_set;
    }

    @Nullable
    @Override
    protected ChatSetActivityPresenter initPresenter() {
        return new ChatSetActivityPresenter();
    }

    @Override
    protected void initView() {
        pUser = (ChatBean) getIntent().getSerializableExtra("data");
        MyApplication.addActivity(this);
        Glide.with(this).load(pUser.getUserIconUrl()).into(rivHeadIcon);
        tvInfoName.setText(pUser.getName());
        tvName.setText(pUser.getName());
        if(pUser.getFileype()>100){//工作通知
            llTranSlationNow.setVisibility(View.GONE);
            llQueryChatRecord.setVisibility(View.GONE);
            llClearChatRecord.setVisibility(View.GONE);
            tvDelete.setVisibility(View.GONE);
        }
        if(pUser.isTop()){
            sTop.setChecked(true);
        }
        sTop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ContentValues values = new ContentValues();
                if(isChecked){
                    values.put("is_top",1);
                    pUser.setTop(true);
                }else{
                    values.put("is_top",0);
                    pUser.setTop(false);
                }
                DataBaseHelper.updateChatListById(getActivity(),UserUtils.getInstance().getUserBean().getUser_id(),pUser.getChatListId(),values);
            }
        });
        if(SPUtils.getInstance().get(Constant.IS_SEND_NOTIFICATION+Constant.CHAT_ONE_TYPE+pUser.getPid(),0) == 0){//0为打扰，1为免打扰
            sDisturb.setChecked(false);
        }else{
            sDisturb.setChecked(true);
        }
        sDisturb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    SPUtils.getInstance().save(Constant.IS_SEND_NOTIFICATION+Constant.CHAT_ONE_TYPE+pUser.getPid(),1);
                }else{
                    SPUtils.getInstance().save(Constant.IS_SEND_NOTIFICATION+Constant.CHAT_ONE_TYPE+pUser.getPid(),0);
                }
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.ll_back, R.id.ll_query_chat_record, R.id.ll_clear_chat_record,R.id.tv_delete,R.id.ll_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                MyApplication.clearActivity();
                break;
            case R.id.ll_query_chat_record:
                Intent intent = new Intent(this, SearchChatRecordActivity.class);
                intent.putExtra("data",pUser);
                startActivityForResult(intent,1);
                break;
            case R.id.ll_clear_chat_record:
                showClearDialog();
                break;
            case R.id.tv_delete:
                showDeleteDialog();
                break;
            case R.id.ll_info:
                Intent intent1 = new Intent(this, FriendInfoActivity.class);
                intent1.putExtra("data",pUser.getPid());
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void deleteFriendSuccess(int code, List list) {
        ToastUtils.showToast(this,"删除成功");
        finish();
    }

    @Override
    public void deleteFriendFailed(int code, String msg) {
        ToastUtils.showToast(this, "删除好友失败：" + code + "\t" + msg);
        Log.e("FriendInfoActivity", "删除好友失败：" + code + "\t" + msg);
    }

    private void showClearDialog() {
        if (clearDialog == null) {
            clearDialog = new Dialog(this);
            clearDialog.setContentView(R.layout.dialog_clear_new_friend);
            TextView tvTitle = clearDialog.findViewById(R.id.tv_title);
            tvTitle.setText("确认清除聊天记录？");
            TextView tvSure = clearDialog.findViewById(R.id.tv_sure);
            tvSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearDialog.dismiss();
                    DataBaseHelper.deleteChatRecordTable(getActivity(),pUser.getPid(), UserUtils.getInstance().getUserBean().getUser_id());
                    ContentValues values = new ContentValues();
                    values.put("last_message","");
                    DataBaseHelper.updateChatListById(getActivity(),UserUtils.getInstance().getUserBean().getUser_id(),pUser.getChatListId(),values);
                    ToastUtils.showToast(getActivity(),"清空成功！");
                }
            });
            TextView tvCancel = clearDialog.findViewById(R.id.tv_cancel);
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearDialog.dismiss();
                }
            });
        }
        clearDialog.show();
    }

    private void showDeleteDialog() {
        if (deleteDialog == null) {
            deleteDialog = new Dialog(this);
            deleteDialog.setContentView(R.layout.dialog_clear_new_friend);
            TextView tvTitle = deleteDialog.findViewById(R.id.tv_title);
            tvTitle.setText("是否删除此好友?");
            TextView tvSure = deleteDialog.findViewById(R.id.tv_sure);
            tvSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String, String> map = new HashMap<>();
                    map.put("friendId", String.valueOf(pUser.getPid()));
                    presenter.deleteFriend(map);
                }
            });
            TextView tvCancel = deleteDialog.findViewById(R.id.tv_cancel);
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteDialog.dismiss();
                }
            });
        }
        deleteDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1&&resultCode == 1){
            int position = data.getIntExtra("data",-1);
            Intent intent = new Intent(getActivity(),ChatActivity.class);
            intent.putExtra("data",position);
            setResult(7,intent);
            finish();
        }
    }
}
