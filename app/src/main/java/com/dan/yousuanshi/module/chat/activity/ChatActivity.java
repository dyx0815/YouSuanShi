package com.dan.yousuanshi.module.chat.activity;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.common.Constant;
import com.dan.yousuanshi.dao.DataBaseHelper;
import com.dan.yousuanshi.event.ReceiveMessageEvent;
import com.dan.yousuanshi.event.SendMessageEvent;
import com.dan.yousuanshi.module.addressbook.activity.FriendInfoActivity;
import com.dan.yousuanshi.module.addressbook.bean.MyFriendBean;
import com.dan.yousuanshi.module.chat.adapter.ChatAdapter;
import com.dan.yousuanshi.module.chat.adapter.EmjoyAdapter;
import com.dan.yousuanshi.module.chat.bean.ChatBean;
import com.dan.yousuanshi.module.chat.bean.ChatEmoji;
import com.dan.yousuanshi.module.chat.bean.ChatUserInfoBean;
import com.dan.yousuanshi.module.chat.bean.EmojiBean;
import com.dan.yousuanshi.module.chat.bean.EmojyViewPagerAdapter;
import com.dan.yousuanshi.module.chat.bean.LocationAddress;
import com.dan.yousuanshi.module.chat.bean.MessageId;
import com.dan.yousuanshi.module.chat.bean.PicBean;
import com.dan.yousuanshi.module.chat.bean.QiniuTokenBean;
import com.dan.yousuanshi.module.chat.bean.RemoveChatMessageCount;
import com.dan.yousuanshi.module.chat.presenter.ChatActivityPresenter;
import com.dan.yousuanshi.module.chat.utils.AudioRecoderUtils;
import com.dan.yousuanshi.module.chat.utils.MediaFile;
import com.dan.yousuanshi.module.chat.view.ChatActivityView;
import com.dan.yousuanshi.module.mine.activity.MyCollectActivity;
import com.dan.yousuanshi.utils.DateUtil;
import com.dan.yousuanshi.utils.DpToPxUtils;
import com.dan.yousuanshi.utils.FileItem;
import com.dan.yousuanshi.utils.FileUtils;
import com.dan.yousuanshi.utils.MD5Utils;
import com.dan.yousuanshi.utils.MyHanlder;
import com.dan.yousuanshi.utils.SoftKeyBoardListener;
import com.dan.yousuanshi.utils.StatusBarUtil;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UploadManagerUtil;
import com.dan.yousuanshi.utils.UserUtils;
import com.dan.yousuanshi.utils.popupwindow.ChatPopupWindow;
import com.dan.yousuanshi.utils.popupwindow.PopUpMenuBean;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vincent.videocompressor.VideoCompress;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatActivity extends BaseActivity<ChatActivityPresenter> implements View.OnTouchListener, ChatActivityView {


    @BindView(R.id.rl_all)
    RelativeLayout rlAll;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_ac_more)
    ImageView ivAcMore;
    @BindView(R.id.ll_ac_more)
    LinearLayout llAcMore;
    @BindView(R.id.rl_ac_title)
    RelativeLayout rlAcTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_voice)
    ImageView ivVoice;
    @BindView(R.id.ll_voice)
    LinearLayout llVoice;
    @BindView(R.id.iv_keyboard)
    ImageView ivKeyboard;
    @BindView(R.id.ll_keyboard)
    LinearLayout llKeyboard;
    @BindView(R.id.et_input_text)
    EditText etInputText;
    @BindView(R.id.tv_send_voice)
    TextView tvSendVoice;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.ll_more)
    LinearLayout llMore;
    @BindView(R.id.iv_face)
    ImageView ivFace;
    @BindView(R.id.ll_face)
    LinearLayout llFace;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.ll_send)
    LinearLayout llSend;
    @BindView(R.id.ll_menu)
    LinearLayout llMenu;
    @BindView(R.id.ib_file)
    ImageButton ibFile;
    @BindView(R.id.ib_album)
    ImageButton ibAlbum;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.ll_emoji)
    LinearLayout llEmoji;

    private List<ChatBean> data = new ArrayList<>();
    private List<ChatBean> allData = new ArrayList<>();
    private ChatAdapter adapter;
    private ChatBean pUser;
    private int uId;
    private float scrollX;
    private float scrollY;
    private Dialog dialog;
    private AudioRecoderUtils audioRecoderUtils;
    private ImageView ivVoiceDialog;
    private ImageView ivVoiceLevel;
    private ImageView ivVoiceRollBack;
    private TextView tvRemind;
    private boolean isCancelVoice = false;
    private long voiceTime;
    private List<LocalMedia> picList;
    private LinearLayoutManager linearLayoutManager;
    private int type = 2;
    private int page = 1;
    private int pageSize = 20;
    private ChatBean voiceChat;
    private ChatBean picChat;
    private ChatBean fileChat;
    private ArrayList<PopUpMenuBean> popUpMenuBeans;
    int position = -1;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Nullable
    @Override
    protected ChatActivityPresenter initPresenter() {
        return new ChatActivityPresenter();
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        showPop();
        pUser = (ChatBean) intent.getSerializableExtra("user");
        type = pUser.getType();
        tvTitle.setText(pUser.getName());
        uId = UserUtils.getInstance().getUserBean().getUser_id();
        EventBus.getDefault().register(this);
        audioRecoderUtils = new AudioRecoderUtils();
        initVoiceDialog();
        initRecyclerView();
        initListener();
        srlRefresh.setEnableLoadMore(false);
        initViewPager(ChatEmoji.emojiList, 3, 7);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getColor(R.color.color_EDEDED), 0);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
//        allData.clear();
        data.clear();
        List<ChatBean> historyMessage = null;
        if (type == Constant.CHAT_ONE_TYPE) {
            if (!DataBaseHelper.tabbleIsExist(this, DataBaseHelper.getChatRecordTableName(pUser.getPid(), uId))) {
                DataBaseHelper.createChatTable(this, pUser.getPid(), uId);
            }
            //获取历史信息
            historyMessage = DataBaseHelper.queryChatRecord(this, pUser.getPid(), uId,pUser.getName());
        } else if (type == Constant.CHAT_GROUP_TYPE) {
            if (!DataBaseHelper.tabbleIsExist(this, DataBaseHelper.getGroupChatRecordTableName(pUser.getPid(), uId))) {
                DataBaseHelper.createGroupChatTable(this, pUser.getPid(), uId);
            }
            //获取历史信息
            historyMessage = DataBaseHelper.queryGroupChatRecord(this, pUser.getPid(), uId);
        }
        data.addAll(historyMessage);
//        loadRecord();
        adapter.notifyDataSetChanged();
        if (position == -1) {
            position = data.size() - 1;
        }
        linearLayoutManager.scrollToPositionWithOffset(position, 0);
    }

    private void initVoiceDialog() {
        dialog = new Dialog(this, R.style.voice_Dialog);
        dialog.setContentView(LayoutInflater.from(this).inflate(R.layout.dialog_send_voice, null));
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        ivVoiceLevel = dialog.findViewById(R.id.iv_voice_level);
        ivVoiceDialog = dialog.findViewById(R.id.iv_voice_dialog);
        ivVoiceRollBack = dialog.findViewById(R.id.iv_voice_rollback);
        tvRemind = dialog.findViewById(R.id.tv_remind);
    }

    private void initRecyclerView() {
        adapter = new ChatAdapter(this, data, popUpMenuBeans, onListItemClickLitener);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initListener() {
        srlRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                int position = pageSize - 1;
//                page+=1;
//                if(allData.size()-(page*pageSize)<position){
//                    position = allData.size()-(page*pageSize);
//                }
//                Log.e("ChatActivity","消息count"+allData.size());
//                Log.e("ChatActivity","消息item"+position);
////                loadRecord();
//                linearLayoutManager.scrollToPositionWithOffset(position,0);
//                adapter.notifyDataSetChanged();
                srlRefresh.finishRefresh();
            }
        });
        //语音按钮事件
        tvSendVoice.setOnTouchListener(this);
        //录音监听事件
        audioRecoderUtils.setOnAudioStatusUpdateListener(new AudioRecoderUtils.OnAudioStatusUpdateListener() {
            @Override
            public void onUpdate(double db, long time) {
                int level = (int) (3000 + 6000 * db / 100);
                Log.e("ChatActivity", level + "");
                ivVoiceLevel.getDrawable().setLevel(level);
                voiceTime = time / 1000;
                if (time / 1000 == 60) {
                    audioRecoderUtils.stopRecord();
                    dialog.dismiss();
                    ToastUtils.showToast(ChatActivity.this, "语音最多60秒哦~");
                }
            }

            @Override
            public void onStop(String filePath) {
                Log.e("ChatAdapter", "录音保存在：" + filePath);
                voiceChat = new ChatBean(pUser.getPid(), voiceTime + "", UserUtils.getInstance().getUserBean().getUser_id(), type
                        , Constant.CHAT_VOICE, 1, UserUtils.getInstance().getUserBean().getUser_portrait()
                        , new ChatUserInfoBean(UserUtils.getInstance().getUserBean().getNick_name(), UserUtils.getInstance().getUserBean().getUser_portrait())
                        , DateUtil.dateToString(new Date()), new ChatUserInfoBean(pUser.getName(), pUser.getUserIconUrl()), filePath);
                data.add(voiceChat);
                adapter.notifyItemInserted(data.size() - 1);
                linearLayoutManager.scrollToPositionWithOffset(data.size() - 1, 0);
                presenter.getQiniuToken(voiceChat, addChatMessageToDataBase(voiceChat, "0"), data.size() - 1);
            }
        });
        //点击空白处隐藏输入法事件
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    scrollX = event.getX();
                    scrollY = event.getY();
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (v.getId() != 0 && Math.abs(scrollX - event.getX()) <= 5 && Math.abs(scrollY - event.getY()) <= 5) {
                        //recyclerView空白处点击事件
                        llMenu.setVisibility(View.GONE);
                        llEmoji.setVisibility(View.GONE);
                        hideInput(ChatActivity.this, etInputText);
                    }
                }
                return false;
            }
        });
        //输入法监听
        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                linearLayoutManager.scrollToPositionWithOffset(data.size() - 1, 0);
                ViewGroup.LayoutParams layoutParams = llMenu.getLayoutParams();
                layoutParams.height = height;
                llMenu.setLayoutParams(layoutParams);
                llMenu.setVisibility(View.GONE);
                llEmoji.setLayoutParams(layoutParams);
                llEmoji.setVisibility(View.GONE);
            }

            @Override
            public void keyBoardHide(int height) {
//                linearLayoutManager.scrollToPositionWithOffset(data.size() - 1, 0);
            }
        });
        //item点击事件
        adapter.setOnItemClick(new ChatAdapter.OnItemClick() {
            @Override
            public void OnItemClick() {
                llMenu.setVisibility(View.GONE);
                llEmoji.setVisibility(View.GONE);
                hideInput(ChatActivity.this, etInputText);
            }

            @Override
            public void onFileClick(FileItem fileItem, String path) {
                if (fileItem.getFileType() == 1) {//文本文件
                    startActivity(FileUtils.getTextFileIntent(path));
                } else if (fileItem.getFileType() == 2) {//doc
                    startActivity(FileUtils.getWordFileIntent(path));
                } else if (fileItem.getFileType() == 3) {//xls
                    startActivity(FileUtils.getExcelFileIntent(path));
                } else if (fileItem.getFileType() == 4) {//ppt
                    startActivity(FileUtils.getPptFileIntent(path));
                }
            }

            @Override
            public void onLocationClick(LocationAddress locationAddress) {
                Intent intent = new Intent(ChatActivity.this, MyLocationActivity.class);
                intent.putExtra("locationAddress", locationAddress);
                startActivity(intent);
            }

            @Override
            public void onCardClick(MyFriendBean myFriendBean) {
                Intent intent = new Intent(ChatActivity.this, FriendInfoActivity.class);
                intent.putExtra("data", myFriendBean.getUser_id());
                startActivity(intent);
            }
        });
        //消息输入框输入监听
        etInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!StringUtils.isEmpty(etInputText.getText().toString())) {
                    llSend.setVisibility(View.VISIBLE);
                    llMore.setVisibility(View.GONE);
                } else {
                    llSend.setVisibility(View.GONE);
                    llMore.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //图片点击事件
        adapter.setonPicClick(new ChatAdapter.ChatPicClick() {
            @Override
            public void onPicClick(String path) {
                List<LocalMedia> medias = new ArrayList<>();
                LocalMedia localMedia = new LocalMedia();
                localMedia.setPath(path);
                medias.add(localMedia);
                if (MediaFile.isVideoFileType(path)) {
                    PictureSelector.create(ChatActivity.this).externalPictureVideo(path);
                } else {
                    PictureSelector.create(ChatActivity.this).themeStyle(R.style.picture_style_look).openExternalPreview(0, medias);
                }
            }
        });
    }

    @OnClick({R.id.ll_back, R.id.ll_ac_more, R.id.ll_voice
            , R.id.ll_keyboard, R.id.ll_more, R.id.ll_face
            , R.id.btn_chat_send, R.id.ib_file, R.id.ib_album
            , R.id.ib_camera, R.id.ib_address, R.id.ib_business_card
    ,R.id.ib_collection})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back://左上角返回
                finish();
                break;
            case R.id.ll_ac_more://右上角省略号
                if (pUser.getFileype() < 100 && pUser.getType() == Constant.CHAT_ONE_TYPE) {//单聊
                    Intent intent = new Intent(this, ChatSetActivity.class);
                    intent.putExtra("data", pUser);
                    startActivityForResult(intent, 7);
                } else if (pUser.getFileype() < 100 && pUser.getType() == Constant.CHAT_GROUP_TYPE) {//群聊
                    Intent intent = new Intent(this, GroupChatSetActivity.class);
                    intent.putExtra("data", pUser);
                    startActivityForResult(intent, 7);
                }
                break;
            case R.id.ll_voice://切换发送语音
                llVoice.setVisibility(View.GONE);
                etInputText.setVisibility(View.GONE);
                llMenu.setVisibility(View.GONE);
                llEmoji.setVisibility(View.GONE);
                llKeyboard.setVisibility(View.VISIBLE);
                tvSendVoice.setVisibility(View.VISIBLE);
                hideInput(this, etInputText);
                break;
            case R.id.ll_keyboard://切换发送文本
                llKeyboard.setVisibility(View.GONE);
                tvSendVoice.setVisibility(View.GONE);
                llVoice.setVisibility(View.VISIBLE);
                etInputText.setVisibility(View.VISIBLE);
                etInputText.setFocusable(true);
                etInputText.setFocusableInTouchMode(true);
                showInput(this, etInputText);
                break;
            case R.id.ll_more://右下角更多选项
                if (llMenu.getVisibility() == View.VISIBLE) {
                    llMenu.setVisibility(View.GONE);
                    MyHanlder.getInstance().postDelayed(() -> showInput(this, etInputText), 50);
                } else {
                    hideInput(this, etInputText);
                    llEmoji.setVisibility(View.GONE);
                    MyHanlder.getInstance().postDelayed(() -> llMenu.setVisibility(View.VISIBLE), 100);
                    MyHanlder.getInstance().postDelayed(() -> linearLayoutManager.scrollToPositionWithOffset(this.data.size() - 1, 0), 100);
                }
                break;
            case R.id.ll_face://表情
                if (llEmoji.getVisibility() == View.VISIBLE) {
                    llEmoji.setVisibility(View.GONE);
                    MyHanlder.getInstance().postDelayed(() -> showInput(this, etInputText), 50);
                } else {
                    hideInput(this, etInputText);
                    llMenu.setVisibility(View.GONE);
                    MyHanlder.getInstance().postDelayed(() -> llEmoji.setVisibility(View.VISIBLE), 100);
                    MyHanlder.getInstance().postDelayed(() -> linearLayoutManager.scrollToPositionWithOffset(this.data.size() - 1, 0), 100);
                }
                break;
            case R.id.btn_chat_send://发送文本按钮
                ChatBean chat = new ChatBean(pUser.getPid(), etInputText.getText().toString(), UserUtils.getInstance().getUserBean().getUser_id(), type
                        , Constant.CHAT_TEXT, 1, UserUtils.getInstance().getUserBean().getUser_portrait()
                        , new ChatUserInfoBean(UserUtils.getInstance().getUserBean().getNick_name(), UserUtils.getInstance().getUserBean().getUser_portrait())
                        , DateUtil.dateToString(new Date()), new ChatUserInfoBean(pUser.getName(), pUser.getUserIconUrl()));
                data.add(chat);
                adapter.notifyItemInserted(data.size() - 1);
                linearLayoutManager.scrollToPositionWithOffset(data.size() - 1, 0);
                sendMessage(chat, addChatMessageToDataBase(chat, "0"), data.size() - 1);
                etInputText.setText("");
                break;
            case R.id.ib_file:
                startActivityForResult(new Intent(this, MyFileActivity.class), 1);
                break;
            case R.id.ib_album:
                // 进入相册 以下是例子：用不到的api可以不写
                if (FileUtils.isFolderExists(getFilesDir().getAbsolutePath() + "/download/pic/")) {
                    PictureSelector.create(ChatActivity.this)
                            .openGallery(PictureMimeType.ofAll())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                            .theme(R.style.picture_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                            .maxSelectNum(9)// 最大图片选择数量 int
                            .minSelectNum(0)// 最小选择数量 int
                            .imageSpanCount(4)// 每行显示个数 int
                            .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                            .previewImage(true)// 是否可预览图片 true or false
                            .previewVideo(true)// 是否可预览视频 true or false
                            .enablePreviewAudio(true) // 是否可播放音频 true or false
                            .isCamera(false)// 是否显示拍照按钮 true or false
                            .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                            .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                            .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                            .setOutputCameraPath(getFilesDir().getAbsolutePath() + "/download/pic/")// 自定义拍照保存路径,可不填
                            .enableCrop(false)// 是否裁剪 true or false
                            .compress(true)// 是否压缩 true or false
                            .glideOverride(100, 100)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                            .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                            .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                            .isGif(false)// 是否显示gif图片 true or false
                            .compressSavePath(getFilesDir().getAbsolutePath() + "/download/pic/")//压缩图片保存地址
                            .openClickSound(false)// 是否开启点击声音 true or false
                            .selectionMedia(null)// 是否传入已选图片 List<LocalMedia> list
                            .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                            .cropCompressQuality(90)// 裁剪压缩质量 默认90 int
                            .minimumCompressSize(100)// 小于100kb的图片不压缩
                            .synOrAsy(true)//同步true或异步false 压缩 默认同步
                            .cropWH(16, 9)// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                            .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                            .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                            .videoQuality(1)// 视频录制质量 0 or 1 int
                            .videoMaxSecond(60)// 显示多少秒以内的视频or音频也可适用 int
                            .videoMinSecond(1)// 显示多少秒以内的视频or音频也可适用 int
                            .recordVideoSecond(60)//视频秒数录制 默认60s int
                            .isDragFrame(false)// 是否可拖动裁剪框(固定)
                            .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                }
                break;
            case R.id.ib_camera://相机
                PictureSelector.create(ChatActivity.this)
                        .openCamera(PictureMimeType.ofImage())
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.ib_address://位置
                startActivityForResult(new Intent(this, MyLocationActivity.class), 5);
                break;
            case R.id.ib_business_card://名片
                startActivityForResult(new Intent(this, SharedBusinessCardActivity.class), 6);
                break;
            case R.id.ib_collection://收藏
                startActivity(new Intent(this, MyCollectActivity.class));
                break;
        }
    }

    private void hideInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void showInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }


    //收到消息
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 2)
    public void receiveMessage(ReceiveMessageEvent receiveMessageEvent) {
        if (receiveMessageEvent.getChatBean().getPid() == pUser.getPid() && receiveMessageEvent.getChatBean().getType() == pUser.getType()) {
            if(data.size() == 0 || DateUtil.diffMinute(data.get(data.size()-1).getTime(),receiveMessageEvent.getChatBean().getTime())>=3){
                receiveMessageEvent.getChatBean().setIsTime(true);
            }
            data.add(receiveMessageEvent.getChatBean());
            adapter.notifyItemInserted(data.size() - 1);
            linearLayoutManager.scrollToPositionWithOffset(data.size() - 1, 0);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideInput(this, etInputText);
        RemoveChatMessageCount messageCount = new RemoveChatMessageCount();
        pUser.setMessageCount(0);
        messageCount.setChatBean(pUser);
        EventBus.getDefault().post(messageCount);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 将聊天记录添加到本地数据库
     */
    private long addChatMessageToDataBase(ChatBean chatBean, String msgId) {
        ContentValues values = new ContentValues();
        values.put("message_type", chatBean.getFileype());
        values.put("message_txt", chatBean.getStxt());
        values.put("send_time", chatBean.getTime());
        values.put("is_read", chatBean.getIsRead());
        values.put("send_flag", chatBean.getFlag());
        if (chatBean.getPath() != null) {
            values.put("path", chatBean.getPath());
        }
        values.put("msgid", msgId);
        values.put("message_temp",chatBean.getTemp());
        if (type == Constant.CHAT_ONE_TYPE) {
            if (!DataBaseHelper.tabbleIsExist(getActivity(), DataBaseHelper.getChatRecordTableName(chatBean.getPid(), uId))) {
                DataBaseHelper.createChatTable(getActivity(), chatBean.getPid(), uId);
            }
            return DataBaseHelper.insertChatRecord(getActivity(), chatBean.getPid(), uId, values);
        } else if (type == Constant.CHAT_GROUP_TYPE) {
            if (!DataBaseHelper.tabbleIsExist(getActivity(), DataBaseHelper.getGroupChatRecordTableName(chatBean.getPid(), uId))) {
                DataBaseHelper.createGroupChatTable(getActivity(), chatBean.getPid(), uId);
            }
            return DataBaseHelper.insertGroupChatRecord(getActivity(), chatBean.getPid(), uId, values);
        }
        return 0;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.tv_send_voice) {
            float startY = 0;
            float endY = 0;
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startY = event.getY();
                    tvSendVoice.setBackground(getResources().getDrawable(R.drawable.shape_chat_input_long_click));
                    tvSendVoice.setText("松开结束");
                    changeSendVoice(false);
                    dialog.show();
                    audioRecoderUtils.startRecord();
                    break;
                case MotionEvent.ACTION_UP:
                    endY = event.getY();
                    tvSendVoice.setBackground(getResources().getDrawable(R.drawable.shape_chat_input));
                    dialog.dismiss();
                    if (isCancelVoice) {//取消发送
                        audioRecoderUtils.cancelRecord();
                    } else {
                        if (voiceTime < 1) {
                            ToastUtils.showToast(ChatActivity.this, "时间太短了~");
                            audioRecoderUtils.cancelRecord();
                        } else {
                            audioRecoderUtils.stopRecord();
                        }
                    }
                    tvSendVoice.setText("按住说话");
                    break;
                case MotionEvent.ACTION_MOVE:
                    float moveY = event.getY();
                    int instance = (int) Math.abs((moveY - startY));
                    Log.e("ChatActivity", "取消语音发送:" + instance);
                    if (instance > 100) {
                        changeSendVoice(true);
                    } else {
                        changeSendVoice(false);
                    }
                    break;
                default:
                    break;
            }

            return true;
        }
        return false;
    }

    private void changeSendVoice(boolean cancel) {
        if (cancel) {//取消发送
            ivVoiceRollBack.setVisibility(View.VISIBLE);
            ivVoiceDialog.setVisibility(View.INVISIBLE);
            ivVoiceLevel.setVisibility(View.GONE);
            tvRemind.setText("松开手指，取消发送");
        } else {
            ivVoiceRollBack.setVisibility(View.GONE);
            ivVoiceDialog.setVisibility(View.VISIBLE);
            ivVoiceLevel.setVisibility(View.VISIBLE);
            tvRemind.setText("手指上滑，取消发送");
        }
        isCancelVoice = cancel;
    }

    @Override
    public void getQiniuTokenSuccess(int code, QiniuTokenBean qiniuTokenBean, final ChatBean chatBean, long msgId, int position) {
        if (code == 0) {
            ToastUtils.showToast(this, "请求七牛Token成功");
            UploadManager uploadManager = UploadManagerUtil.getInstance();
            if (chatBean.getFileype() == Constant.CHAT_VOICE) {
                uploadManager.put(chatBean.getPath(), Constant.QINIU_VOICE+MD5Utils.getMd5Code(""+uId + System.currentTimeMillis() + pUser.getPid()) + "." + FileUtils.getExtensionName(chatBean.getPath()), qiniuTokenBean.getToken(), new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {
                        if (info.isOK()) {
                            Log.e("qiniu", "上传成功!!!!!!!");
                            Log.e("qiniu", "key:" + key);
                            voiceChat.setTemp(key);
                            sendMessage(voiceChat, msgId, position);
                        } else {
                            Log.e("qiniu", "上传失败!!!!!!!");
                            Log.e("qiniu", "json:" + response + "\t" + info.error);
                        }
                    }
                }, null);
            } else if (chatBean.getFileype() == Constant.CHAT_PIC) {
                String path = chatBean.getPath();
                uploadManager.put(path, Constant.QINIU_PIC + MD5Utils.getMd5Code(""+uId + System.currentTimeMillis() + pUser.getPid()) + "." + FileUtils.getExtensionName(chatBean.getPath()), qiniuTokenBean.getToken(), new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {
                        if (info.isOK()) {
                            Log.e("qiniu", "上传成功!!!!!!!");
                            Log.e("qiniu", "key:" + key);
                            chatBean.setTemp(key);
                            sendMessage(chatBean, msgId, position);
                        } else {
                            Log.e("qiniu", "上传失败!!!!!!!");
                            Log.e("qiniu", "json:" + response + "\t" + info.error);
                        }
                    }
                }, null);
            }else if(chatBean.getFileype() == Constant.CHAT_VIDEO){
                String path = chatBean.getPath();
                uploadManager.put(path, Constant.QINIU_VIDEO + MD5Utils.getMd5Code(""+uId + System.currentTimeMillis() + pUser.getPid()) + "." + FileUtils.getExtensionName(chatBean.getPath()), qiniuTokenBean.getToken(), new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {
                        if (info.isOK()) {
                            Log.e("qiniu", "上传成功!!!!!!!");
                            Log.e("qiniu", "key:" + key);
                            chatBean.setTemp(key);
                            sendMessage(chatBean, msgId, position);
                        } else {
                            Log.e("qiniu", "上传失败!!!!!!!");
                            Log.e("qiniu", "json:" + response + "\t" + info.error);
                        }
                    }
                }, null);
            } else if (chatBean.getFileype() == Constant.CHAT_FILE) {
                Log.e("qiniu", "发送文件");
                FileItem fileItem = new Gson().fromJson(chatBean.getStxt(), FileItem.class);
                uploadManager.put(fileItem.getFilePath(), Constant.QINIU_FILE + MD5Utils.getMd5Code(""+uId + fileItem.getFileName())+"."+FileUtils.getExtensionName(fileItem.getFilePath()), qiniuTokenBean.getToken(), new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {
                        if (info.isOK()) {
                            Log.e("qiniu", "上传成功!!!!!!!");
                            Log.e("qiniu", "key:" + key);
                            chatBean.setTemp(key);
                            sendMessage(chatBean, msgId, position);
                        } else {
                            Log.e("qiniu", "上传失败!!!!!!!");
                            Log.e("qiniu", "json:" + response + "\t" + info.error);
                        }
                    }
                }, null);
            } else if (chatBean.getFileype() == Constant.CHAT_LOCATION) {
                Log.e("qiniu", "发送位置");
                uploadManager.put(chatBean.getPath(), Constant.QINIU_LOCATION +MD5Utils.getMd5Code(""+ uId + "location" + System.currentTimeMillis()), qiniuTokenBean.getToken(), new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {
                        if (info.isOK()) {
                            Log.e("qiniu", "上传成功!!!!!!!");
                            Log.e("qiniu", "key:" + key);
                            chatBean.setTemp(key);
                            sendMessage(chatBean, msgId, position);
                        } else {
                            Log.e("qiniu", "上传失败!!!!!!!");
                            Log.e("qiniu", "json:" + response + "\t" + info.error);
                        }
                    }
                }, null);
            }
        }
    }

    @Override
    public void getQiniuTokenFailed(int code, String message, long id, int position) {
        ToastUtils.showToast(this, "code:" + code + "\t" + message);
        this.data.get(position).setMsgid(Constant.SEND_ERROR_MSG_ID);
        adapter.notifyItemChanged(position);
        if (this.data.get(position).getType() == Constant.CHAT_ONE_TYPE) {
            updateChatRecordMsgId(id, Constant.SEND_ERROR_MSG_ID);
        } else if (this.data.get(position).getType() == Constant.CHAT_GROUP_TYPE) {
            updateGroupChatRecordMsgId(id, Constant.SEND_ERROR_MSG_ID);
        }
    }

    @Override
    public void sendMessageSuccess(int code, MessageId data, long id, int position) {
        this.data.get(position).setMsgid(data.getMsgid());
        if (type == Constant.CHAT_ONE_TYPE) {
            updateChatRecordMsgId(id, data.getMsgid());
        } else if (type == Constant.CHAT_GROUP_TYPE) {
            updateGroupChatRecordMsgId(id, data.getMsgid());
        }
        adapter.notifyItemChanged(position);
    }

    /**
     * 更改聊天记录消息id
     *
     * @param id
     * @param msgId 消息id为-1为发送失败
     */
    private void updateChatRecordMsgId(long id, String msgId) {
        ContentValues values = new ContentValues();
        values.put("msgid", msgId);
        DataBaseHelper.updateChatRecord(getActivity(), pUser.getPid(), uId, values, id);
    }

    /**
     * 更改聊天记录消息id
     *
     * @param id
     * @param msgId 消息id为-1为发送失败
     */
    private void updateGroupChatRecordMsgId(long id, String msgId) {
        ContentValues values = new ContentValues();
        values.put("msgid", msgId);
        DataBaseHelper.updateGroupChatRecord(getActivity(), pUser.getPid(), uId, values, id);
    }

    @Override
    public void sendMessageFailed(int code, String msg, long id, int position) {
        Log.e("Socket", "ChatActivity发送消息失败 code:" + code + "\t" + msg);
        if (type == Constant.CHAT_ONE_TYPE) {
            updateChatRecordMsgId(id, Constant.SEND_ERROR_MSG_ID);
        } else if (type == Constant.CHAT_GROUP_TYPE) {
            updateGroupChatRecordMsgId(id, Constant.SEND_ERROR_MSG_ID);
        }
        data.get(position).setMsgid(Constant.SEND_ERROR_MSG_ID);
        adapter.notifyItemChanged(position);
    }

    @Override
    public void addCollectSuccess(int code, List list) {
        ToastUtils.showCenterToast(this,"收藏成功");
    }

    @Override
    public void addCollectFailed(int code, String msg) {
        ToastUtils.showToast(this,"收藏失败！"+code+msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureConfig.CHOOSE_REQUEST) {
            // 图片、视频、音频选择结果回调
            picList = PictureSelector.obtainMultipleResult(data);
            // 例如 LocalMedia 里面返回三种path
            // 1.media.getPath(); 为原图path
            // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
            // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
            // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
//            adapter.setList(selectList);
            for (LocalMedia item : picList) {
                ChatBean chatBean = new ChatBean();
                if (item.isCompressed()) {
                    chatBean.setPath(item.getCompressPath());
                } else {
                    chatBean.setPath(item.getPath());
                }
                picChat = new ChatBean(pUser.getPid(), new PicBean(item.getWidth(), item.getHeight()).toJson(), UserUtils.getInstance().getUserBean().getUser_id(), type
                        , Constant.CHAT_PIC, 1, UserUtils.getInstance().getUserBean().getUser_portrait()
                        , new ChatUserInfoBean(UserUtils.getInstance().getUserBean().getNick_name(), UserUtils.getInstance().getUserBean().getUser_portrait())
                        , DateUtil.dateToString(new Date()), new ChatUserInfoBean(pUser.getName(), pUser.getUserIconUrl()), chatBean.getPath());
                //如果是视频 压缩
                if (MediaFile.isVideoFileType(chatBean.getPath())) {
                    if (FileUtils.isFolderExists(getApplicationContext().getFilesDir().getAbsolutePath() + "/compress/video/")) {
                        String path = getApplicationContext().getFilesDir().getAbsolutePath()//文件名
                                + "/compress/video/" + MD5Utils.getMd5Code(uId + System.currentTimeMillis() + "compress")
                                + "." + FileUtils.getExtensionName(chatBean.getPath());
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                VideoCompress.compressVideoLow(chatBean.getPath(), path, new VideoCompress.CompressListener() {
                                    @Override
                                    public void onStart() {
                                        ToastUtils.showToast(ChatActivity.this, "视频开始压缩");
                                        Log.e("ChatActivity", "视频压缩开始：" + DateUtil.dateToString(new Date()));
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                showLoadingDialog();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onSuccess() {
                                        Log.e("ChatActivity", "视频压缩成功：" + DateUtil.dateToString(new Date()));
                                        picChat.setPath(path);
                                        picChat.setStxt(FileUtils.getMp4Time(path)+"");//视频秒数
                                        picChat.setFileype(Constant.CHAT_VIDEO);
                                        ChatActivity.this.data.add(picChat);
                                        adapter.notifyItemInserted(ChatActivity.this.data.size() - 1);
                                        linearLayoutManager.scrollToPositionWithOffset(ChatActivity.this.data.size() - 1, 0);
                                        presenter.getQiniuToken(picChat, addChatMessageToDataBase(picChat, "0"), ChatActivity.this.data.size() - 1);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                llMenu.setVisibility(View.GONE);
                                                llEmoji.setVisibility(View.GONE);
                                                linearLayoutManager.scrollToPositionWithOffset(ChatActivity.this.data.size() - 1, 0);
                                            }
                                        });
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                dismissLoadingDialog();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onFail() {
                                        Log.e("ChatActivity", "视频压缩错误");
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                dismissLoadingDialog();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onProgress(float percent) {
                                        Log.e("ChatActivity", "进度：" + percent);
                                    }
                                });
                            }
                        }.start();
                    }
                } else {
                    ChatActivity.this.data.add(picChat);
                    adapter.notifyItemInserted(ChatActivity.this.data.size() - 1);
                    linearLayoutManager.scrollToPositionWithOffset(ChatActivity.this.data.size() - 1, 0);
                    presenter.getQiniuToken(picChat, addChatMessageToDataBase(picChat, "0"), ChatActivity.this.data.size() - 1);
                    llMenu.setVisibility(View.GONE);
                    llEmoji.setVisibility(View.GONE);
                    linearLayoutManager.scrollToPositionWithOffset(this.data.size() - 1, 0);
                }
            }
        }
        if (resultCode == 3) {//文件
            List<FileItem> file = data.getParcelableArrayListExtra("fileData");
            for (int i = 0; i < file.size(); i++) {
                fileChat = new ChatBean(pUser.getPid(), new Gson().toJson(file.get(i)), UserUtils.getInstance().getUserBean().getUser_id(), type
                        , Constant.CHAT_FILE, 1, UserUtils.getInstance().getUserBean().getUser_portrait()
                        , new ChatUserInfoBean(UserUtils.getInstance().getUserBean().getNick_name(), UserUtils.getInstance().getUserBean().getUser_portrait())
                        , DateUtil.dateToString(new Date()), new ChatUserInfoBean(pUser.getName(), pUser.getUserIconUrl()), file.get(i).getFilePath());
                this.data.add(fileChat);
                adapter.notifyItemInserted(this.data.size() - 1);
                linearLayoutManager.scrollToPositionWithOffset(this.data.size() - 1, 0);
                presenter.getQiniuToken(fileChat, addChatMessageToDataBase(fileChat, "0"), this.data.size() - 1);
            }
        }
        if (resultCode == 4) {//位置
            String locationPath = data.getStringExtra("path");
            LocationAddress locationAddress = data.getParcelableExtra("locationAddress");
            ChatBean locationChat = new ChatBean(pUser.getPid(), new Gson().toJson(locationAddress), UserUtils.getInstance().getUserBean().getUser_id(), type
                    , Constant.CHAT_LOCATION, 1, UserUtils.getInstance().getUserBean().getUser_portrait()
                    , new ChatUserInfoBean(UserUtils.getInstance().getUserBean().getNick_name(), UserUtils.getInstance().getUserBean().getUser_portrait())
                    , DateUtil.dateToString(new Date()), new ChatUserInfoBean(pUser.getName(), pUser.getUserIconUrl()), locationPath);
            this.data.add(locationChat);
            adapter.notifyItemInserted(this.data.size() - 1);
            linearLayoutManager.scrollToPositionWithOffset(this.data.size() - 1, 0);
            presenter.getQiniuToken(locationChat, addChatMessageToDataBase(locationChat, "0"), this.data.size() - 1);
        }
        if (requestCode == 6 && resultCode == 6) {//名片
            MyFriendBean myFriendBean = data.getParcelableExtra("data");
            ChatBean cardBean = new ChatBean(pUser.getPid(), new Gson().toJson(myFriendBean), UserUtils.getInstance().getUserBean().getUser_id(), type
                    , Constant.CHAT_CARD, 1, UserUtils.getInstance().getUserBean().getUser_portrait()
                    , new ChatUserInfoBean(UserUtils.getInstance().getUserBean().getNick_name(), UserUtils.getInstance().getUserBean().getUser_portrait())
                    , DateUtil.dateToString(new Date()), new ChatUserInfoBean(pUser.getName(), pUser.getUserIconUrl()));
            this.data.add(cardBean);
            adapter.notifyItemInserted(this.data.size() - 1);
            linearLayoutManager.scrollToPositionWithOffset(this.data.size() - 1, 0);
            sendMessage(cardBean, addChatMessageToDataBase(cardBean, "0"), this.data.size() - 1);
        }
        if (requestCode == 7 && resultCode == 7) {
            position = data.getIntExtra("data", -1);
        }
    }

    /**
     * 给服务器发送消息
     *
     * @param chatBean
     * @param id       本地数据库消息id
     * @param position list中消息下标
     */
    private void sendMessage(ChatBean chatBean, long id, int position) {
        Map<String, String> map = new HashMap<>();
        map.put("pid", String.valueOf(chatBean.getPid()));
        map.put("stxt", chatBean.getStxt());
        map.put("mid", String.valueOf(uId));
        map.put("type", String.valueOf(chatBean.getType()));
        if (!StringUtils.isEmpty(chatBean.getTemp())) {
            map.put("temp", chatBean.getTemp());
        }
        map.put("fileype", String.valueOf(chatBean.getFileype()));
        map.put("userinfo", chatBean.getUserinfo().toJson());
        if (type == Constant.CHAT_GROUP_TYPE) {
            ChatUserInfoBean groupInfo = new ChatUserInfoBean(pUser.getName(), pUser.getUserIconUrl(), pUser.getGroupType());
            chatBean.setGroupinfo(groupInfo);
            chatBean.setGroupType(pUser.getGroupType());
            map.put("groupinfo", groupInfo.toJson());
        }
        SendMessageEvent sendMessageEvent = new SendMessageEvent(chatBean);
        EventBus.getDefault().post(sendMessageEvent);
        presenter.sendMessage(map, id, position);
    }

//    private void loadRecord(){
//        if(allData.size()>20){
//            for(int i = allData.size()-((page-1)*pageSize)-1; i>=allData.size()-(page*pageSize);i--){
//                if(i>=0){
//                    data.add(0,allData.get(i));
//                }
//            }
//        }else{
//            if(data.size()<allData.size()){
//                data.addAll(allData);
//            }
//        }
//    }

    /**
     * @param datas   所有数据
     * @param rowNum  行数
     * @param spanNum 列数
     */
    private void initViewPager(List<EmojiBean> datas, int rowNum, int spanNum) {
        //1.根据数据的多少来分页，每页的数据为rw
        int singlePageDatasNum = rowNum * spanNum;//每个单页包含的数据量：2*4=8；
        int pageNum = datas.size() / singlePageDatasNum;//算出有几页菜单：20%8 = 3;
        if (datas.size() % singlePageDatasNum > 0) pageNum++;//如果取模大于0，就还要多一页出来，放剩下的不满项
        ArrayList<RecyclerView> mList = new ArrayList<>();
        for (int i = 0; i < pageNum; i++) {
            RecyclerView recyclerView = new RecyclerView(this);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanNum);
            recyclerView.setLayoutManager(gridLayoutManager);
            int fromIndex = i * singlePageDatasNum;
            int toIndex = (i + 1) * singlePageDatasNum;
            if (toIndex > datas.size()) toIndex = datas.size();
            //a.截取每个页面包含数据
            List<EmojiBean> emojiBeanList = new ArrayList<>(datas.subList(fromIndex, toIndex));
            //b.设置每个页面的适配器数据
            EmjoyAdapter emjoyAdapter = new EmjoyAdapter(this, emojiBeanList);
            emjoyAdapter.setOnItemClick(new EmjoyAdapter.OnItemClick() {
                @Override
                public void onItemClick(EmojiBean emojiBean) {
                    String text = etInputText.getText().toString();
                    etInputText.setFocusable(true);
                    etInputText.setFocusableInTouchMode(true);
                    etInputText.requestFocus();//获取焦点 光标出现
                    if (emojiBean.getText().equals("[删除]")) {
                        etInputText.dispatchKeyEvent(new KeyEvent(
                                KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                    } else {
                        text = etInputText.getText().toString() + emojiBean.getText();
                        etInputText.setText(ChatEmoji.getEmotionContent(ChatActivity.this, text));
                        Log.e("Emjoy", etInputText.getText().toString());
                    }
                    etInputText.setSelection(etInputText.getText().length());
                }
            });
            recyclerView.setAdapter(emjoyAdapter);
            mList.add(recyclerView);
        }
        //2.ViewPager的适配器
        EmojyViewPagerAdapter menuViewPagerAdapter = new EmojyViewPagerAdapter(mList);
        viewPager.setAdapter(menuViewPagerAdapter);
        //3.动态设置ViewPager的高度，并加载所有页面
        int height = DpToPxUtils.dip2px(this, 80);//这里的80为MainMenuAdapter中布局文件高度
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, datas.size() <= spanNum ? height : height * rowNum);
        viewPager.setLayoutParams(layoutParams);
        viewPager.setOffscreenPageLimit(pageNum - 1);
    }

    /**
     * 右上角加号菜单
     */
    public void showPop() {
        popUpMenuBeans = new ArrayList<>();
    }

    private ChatPopupWindow.OnListItemClickLitener onListItemClickLitener = new ChatPopupWindow.OnListItemClickLitener() {
        @Override
        public void onListItemClick(String text, int chatPosition, TextView textView) {
            if ("撤回".equals(text)) {//撤回

            } else if ("翻译".equals(text)) {//翻译

            } else if ("删除".equals(text)) {//删除
                data.remove(chatPosition);
                adapter.notifyItemRemoved(chatPosition);
                DataBaseHelper.deleteChatRecord(ChatActivity.this, pUser.getPid(), uId, data.get(chatPosition).getId());
            } else if ("转发".equals(text)) {//转发
                Intent intent = new Intent(ChatActivity.this, ChooseActivity.class);
                intent.putExtra("chatData", data.get(chatPosition));
                startActivity(intent);
            } else if ("复制".equals(text)) {//复制
                //获取剪切班管理者
                ClipboardManager cbs = (ClipboardManager) ChatActivity.this.getSystemService(CLIPBOARD_SERVICE);
                //获取选中的起始位置
                int selectionStart = textView.getSelectionStart();
                int selectionEnd = textView.getSelectionEnd();
                //截取选中的文本
                String txt = textView.getText().toString();
                String substring = txt.substring(selectionStart, selectionEnd);
                //将选中的文本放到剪切板
                cbs.setPrimaryClip(ClipData.newPlainText(null, substring));
            } else if ("转换文字".equals(text)) {//转换文字

            } else if ("强提醒".equals(text)) {//强提醒

            } else if ("收藏".equals(text)) {//收藏
                ChatBean chatBean = data.get(chatPosition);
                Map<String,Object> map = new HashMap<>();
                map.put("collectionType",chatBean.getFileype());
                if(chatBean.getFileype() == Constant.CHAT_VOICE || chatBean.getFileype() == Constant.CHAT_PIC){
                    map.put("collectionSize",chatBean.getStxt());
                    map.put("content",chatBean.getTemp());
                    map.put("collectionSuffix",FileUtils.getExtensionName(chatBean.getPath()));
                }else if(chatBean.getFileype() == Constant.CHAT_VIDEO){
                    map.put("collectionSize",chatBean.getStxt());
                    map.put("content",chatBean.getTemp());
                    map.put("collectionSuffix",FileUtils.getExtensionName(chatBean.getPath()));
                }else if(chatBean.getFileype() == Constant.CHAT_FILE){
                    FileItem fileItem = new Gson().fromJson(chatBean.getStxt(), FileItem.class);
                    fileItem.setUrl(chatBean.getTemp());
                    map.put("collectionSize",fileItem.getSize());
                    map.put("content",new Gson().toJson(fileItem));
                    map.put("collectionSuffix",FileUtils.getExtensionName(chatBean.getPath()));
                }else if(chatBean.getFileype() == Constant.CHAT_LOCATION){
                    LocationAddress locationAddress = new Gson().fromJson(chatBean.getStxt(), LocationAddress.class);
                    map.put("collectionSize","0");
                    map.put("content",new Gson().toJson(locationAddress));
                    map.put("collectionSuffix","0");
                }else{
                    map.put("content",chatBean.getStxt());
                    map.put("collectionSize","0");
                    map.put("collectionSuffix","0");
                }
                if(chatBean.getType() == Constant.CHAT_GROUP_TYPE){
                    map.put("sendNickName",chatBean.getName());
                }else{
                    map.put("sendNickName",pUser.getName());
                }
                presenter.addCollect(map);
            }
        }
    };
}
