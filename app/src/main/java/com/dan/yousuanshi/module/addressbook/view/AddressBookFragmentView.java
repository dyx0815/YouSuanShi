package com.dan.yousuanshi.module.addressbook.view;

import com.dan.yousuanshi.base.BaseView;
import com.dan.yousuanshi.module.addressbook.bean.AddressBookBean;

public interface AddressBookFragmentView extends BaseView {
    void addressBookSuccess(int code, AddressBookBean addressBookBean);
    void addressBookFailed(int code,String msg);
}
