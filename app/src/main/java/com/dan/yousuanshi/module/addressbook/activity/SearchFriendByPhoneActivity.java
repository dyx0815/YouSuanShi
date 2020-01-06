package com.dan.yousuanshi.module.addressbook.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.addressbook.bean.SearchFriendBean;
import com.dan.yousuanshi.module.addressbook.presenter.SearchFriendByPhoneActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.SearchFriendByPhoneActivityView;
import com.dan.yousuanshi.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchFriendByPhoneActivity extends BaseActivity<SearchFriendByPhoneActivityPresenter> implements SearchFriendByPhoneActivityView {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.ll_clear)
    LinearLayout llClear;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_friend_by_phone;
    }

    @Nullable
    @Override
    protected SearchFriendByPhoneActivityPresenter initPresenter() {
        return new SearchFriendByPhoneActivityPresenter();
    }

    @Override
    protected void initView() {
        etSearch.setFocusable(true);
        etSearch.setFocusableInTouchMode(true);
        etSearch.requestFocus();//获取焦点 光标出现
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!StringUtils.isEmpty(etSearch.getText().toString())) {
                    llClear.setVisibility(View.VISIBLE);
                } else {
                    llClear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        initSearch();
    }

    @Override
    protected void loadData() {

    }


    @Override
    public void searchFriendSuccess(int code, SearchFriendBean searchFriendBean) {
        Intent intent = new Intent(this, FriendInfoActivity.class);
        intent.putExtra("data", searchFriendBean.getUser_id());
        startActivity(intent);
        finish();
    }

    @Override
    public void searchFriendFailed(int code, String msg) {
        Log.e("SearchFriendByPhone", "code:" + code + "\t" + msg);
        if (code == 1 && "JSON解析异常".equals(msg)) {
            tvTitle.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.ll_back, R.id.ll_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_clear:
                etSearch.setText("");
                break;
        }
    }

    private void initSearch() {
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Map<String, String> map = new HashMap<>();
                map.put("search", etSearch.getText().toString());
                presenter.searchFriend(map);
                return true;
            }
        });
    }
}
