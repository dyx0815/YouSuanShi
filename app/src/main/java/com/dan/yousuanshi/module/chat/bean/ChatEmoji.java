package com.dan.yousuanshi.module.chat.bean;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.utils.DpToPxUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatEmoji {

    public static final List<EmojiBean> emojiList = new ArrayList<>();

    static {
        emojiList.add(new EmojiBean("[微笑]", R.mipmap.icon_emoji_weixiao));
        emojiList.add(new EmojiBean("[色]", R.mipmap.icon_emoji_se));
        emojiList.add(new EmojiBean("[发呆]", R.mipmap.icon_emoji_fadai));
        emojiList.add(new EmojiBean("[得意]", R.mipmap.icon_emoji_deyi));
        emojiList.add(new EmojiBean("[流泪]", R.mipmap.icon_emoji_liulei));
        emojiList.add(new EmojiBean("[害羞]", R.mipmap.icon_emoji_haixiu));
        emojiList.add(new EmojiBean("[闭嘴]", R.mipmap.icon_emoji_bizui));
        emojiList.add(new EmojiBean("[睡觉]", R.mipmap.icon_emoji_shuijiao));
        emojiList.add(new EmojiBean("[大哭]", R.mipmap.icon_emoji_daku));
        emojiList.add(new EmojiBean("[尴尬]", R.mipmap.icon_emoji_ganga));
        emojiList.add(new EmojiBean("[怒]", R.mipmap.icon_emoji_nu));
        emojiList.add(new EmojiBean("[调皮]", R.mipmap.icon_emoji_tiaopi));
        emojiList.add(new EmojiBean("[龇牙]", R.mipmap.icon_emoji_ciya));
        emojiList.add(new EmojiBean("[惊讶]", R.mipmap.icon_emoji_jingya));
        emojiList.add(new EmojiBean("[难过]", R.mipmap.icon_emoji_nanguo));
        emojiList.add(new EmojiBean("[撇嘴]", R.mipmap.icon_emoji_piezui));
        emojiList.add(new EmojiBean("[憨笑]", R.mipmap.icon_emoji_hanxiao));
        emojiList.add(new EmojiBean("[傲慢]", R.mipmap.icon_emoji_aoman));
        emojiList.add(new EmojiBean("[白眼]", R.mipmap.icon_emoji_baiyan));
        emojiList.add(new EmojiBean("[鄙视]", R.mipmap.icon_emoji_bishi));
        emojiList.add(new EmojiBean("[删除]", R.mipmap.icon_emoji_shanchu));
        emojiList.add(new EmojiBean("[擦汗]", R.mipmap.icon_emoji_cahan));
        emojiList.add(new EmojiBean("[打哈欠]", R.mipmap.icon_emoji_dahaqian));
        emojiList.add(new EmojiBean("[流汗]", R.mipmap.icon_emoji_liuhan));
        emojiList.add(new EmojiBean("[冷汗]", R.mipmap.icon_emoji_lenghan));
        emojiList.add(new EmojiBean("[可爱]", R.mipmap.icon_emoji_keai));
        emojiList.add(new EmojiBean("[饥饿]", R.mipmap.icon_emoji_jie));
        emojiList.add(new EmojiBean("[坏笑]", R.mipmap.icon_emoji_huaixiao));
        emojiList.add(new EmojiBean("[鼓掌]", R.mipmap.icon_emoji_guzhang));
        emojiList.add(new EmojiBean("[奋斗]", R.mipmap.icon_emoji_fendou));
        emojiList.add(new EmojiBean("[大兵]", R.mipmap.icon_emoji_dabing));
        emojiList.add(new EmojiBean("[可怜]", R.mipmap.icon_emoji_kelian));
        emojiList.add(new EmojiBean("[抠鼻]", R.mipmap.icon_emoji_koubi));
        emojiList.add(new EmojiBean("[骷髅]", R.mipmap.icon_emoji_kulou));
        emojiList.add(new EmojiBean("[酷]", R.mipmap.icon_emoji_ku));
        emojiList.add(new EmojiBean("[快哭了]", R.mipmap.icon_emoji_kuaikule));
        emojiList.add(new EmojiBean("[困]", R.mipmap.icon_emoji_kun));
        emojiList.add(new EmojiBean("[疑问]", R.mipmap.icon_emoji_yiwen));
        emojiList.add(new EmojiBean("[阴险]", R.mipmap.icon_emoji_yinxian));
        emojiList.add(new EmojiBean("[嘘]", R.mipmap.icon_emoji_xu));
        emojiList.add(new EmojiBean("[吓]", R.mipmap.icon_emoji_xia));
        emojiList.add(new EmojiBean("[删除]", R.mipmap.icon_emoji_shanchu));
        emojiList.add(new EmojiBean("[偷笑]", R.mipmap.icon_emoji_touxiao));
        emojiList.add(new EmojiBean("[吐]", R.mipmap.icon_emoji_tu));
        emojiList.add(new EmojiBean("[委屈]", R.mipmap.icon_emoji_weiqu));
        emojiList.add(new EmojiBean("[糗大了]", R.mipmap.icon_emoji_qiudale));
        emojiList.add(new EmojiBean("[敲打]", R.mipmap.icon_emoji_qiaoda));
        emojiList.add(new EmojiBean("[么么哒]", R.mipmap.icon_emoji_memeda));
        emojiList.add(new EmojiBean("[左哼哼]", R.mipmap.icon_emoji_zuohengheng));
        emojiList.add(new EmojiBean("[咒骂]", R.mipmap.icon_emoji_zhouma));
        emojiList.add(new EmojiBean("[折磨]", R.mipmap.icon_emoji_zhemo));
        emojiList.add(new EmojiBean("[晕]", R.mipmap.icon_emoji_yun));
        emojiList.add(new EmojiBean("[右哼哼]", R.mipmap.icon_emoji_youhengheng));
        emojiList.add(new EmojiBean("[再见]", R.mipmap.icon_emoji_zaijian));
        emojiList.add(new EmojiBean("[抓狂]", R.mipmap.icon_emoji_zhuakuang));
        emojiList.add(new EmojiBean("[猪头]", R.mipmap.icon_emoji_zhutou));
        emojiList.add(new EmojiBean("[NO]", R.mipmap.icon_emoji_no));
        emojiList.add(new EmojiBean("[OK]", R.mipmap.icon_emoji_ok));
        emojiList.add(new EmojiBean("[爱你]", R.mipmap.icon_emoji_love_you));
        emojiList.add(new EmojiBean("[爱情]", R.mipmap.icon_emoji_love));
        emojiList.add(new EmojiBean("[爱心]", R.mipmap.icon_emoji_love_heart));
        emojiList.add(new EmojiBean("[棒棒糖]", R.mipmap.icon_emoji_bangbangtang));
        emojiList.add(new EmojiBean("[删除]", R.mipmap.icon_emoji_shanchu));
        emojiList.add(new EmojiBean("[抱抱]", R.mipmap.icon_emoji_baobao));
        emojiList.add(new EmojiBean("[抱拳]", R.mipmap.icon_emoji_baoquan));
        emojiList.add(new EmojiBean("[爆筋]", R.mipmap.icon_emoji_baojin));
        emojiList.add(new EmojiBean("[鞭炮]", R.mipmap.icon_emoji_bianpao));
        emojiList.add(new EmojiBean("[便便]", R.mipmap.icon_emoji_bianbian));
        emojiList.add(new EmojiBean("[彩带]", R.mipmap.icon_emoji_caidai));
        emojiList.add(new EmojiBean("[彩球]", R.mipmap.icon_emoji_caiqiu));
        emojiList.add(new EmojiBean("[菜刀]", R.mipmap.icon_emoji_caidao));
        emojiList.add(new EmojiBean("[差劲]", R.mipmap.icon_emoji_chajin));
        emojiList.add(new EmojiBean("[钞票]", R.mipmap.icon_emoji_chaopiao));
        emojiList.add(new EmojiBean("[车厢]", R.mipmap.icon_emoji_chexiang));
        emojiList.add(new EmojiBean("[蛋糕]", R.mipmap.icon_emoji_dangao));
        emojiList.add(new EmojiBean("[刀]", R.mipmap.icon_emoji_dao));
        emojiList.add(new EmojiBean("[灯泡]", R.mipmap.icon_emoji_dengpao));
        emojiList.add(new EmojiBean("[凋谢]", R.mipmap.icon_emoji_diaoxie));
        emojiList.add(new EmojiBean("[多云]", R.mipmap.icon_emoji_duoyun));
        emojiList.add(new EmojiBean("[发抖]", R.mipmap.icon_emoji_fadou));
        emojiList.add(new EmojiBean("[飞机]", R.mipmap.icon_emoji_feiji));
        emojiList.add(new EmojiBean("[飞吻]", R.mipmap.icon_emoji_feiwen));
        emojiList.add(new EmojiBean("[风车]", R.mipmap.icon_emoji_fengche));
        emojiList.add(new EmojiBean("[删除]", R.mipmap.icon_emoji_shanchu));
        emojiList.add(new EmojiBean("[勾引]", R.mipmap.icon_emoji_gouyin));
        emojiList.add(new EmojiBean("[红灯笼]", R.mipmap.icon_emoji_hongdenglong));
        emojiList.add(new EmojiBean("[红双喜]", R.mipmap.icon_emoji_hongshuangxi));
        emojiList.add(new EmojiBean("[挥手]", R.mipmap.icon_emoji_huishou));
        emojiList.add(new EmojiBean("[回头]", R.mipmap.icon_emoji_huitou));
        emojiList.add(new EmojiBean("[激动]", R.mipmap.icon_emoji_jidong));
        emojiList.add(new EmojiBean("[街舞]", R.mipmap.icon_emoji_jeiwu));
        emojiList.add(new EmojiBean("[惊恐]", R.mipmap.icon_emoji_jingkong));
        emojiList.add(new EmojiBean("[咖啡]", R.mipmap.icon_emoji_kafei));
        emojiList.add(new EmojiBean("[磕头]", R.mipmap.icon_emoji_ketou));
        emojiList.add(new EmojiBean("[蜡烛]", R.mipmap.icon_emoji_lazhu));
        emojiList.add(new EmojiBean("[篮球]", R.mipmap.icon_emoji_lanqiu));
        emojiList.add(new EmojiBean("[礼品袋]", R.mipmap.icon_emoji_lipindai));
        emojiList.add(new EmojiBean("[礼物]", R.mipmap.icon_emoji_liwu));
        emojiList.add(new EmojiBean("[麻将]", R.mipmap.icon_emoji_majiang));
        emojiList.add(new EmojiBean("[麦克风]", R.mipmap.icon_emoji_maikefeng));
        emojiList.add(new EmojiBean("[猫咪]", R.mipmap.icon_emoji_maomi));
        emojiList.add(new EmojiBean("[玫瑰]", R.mipmap.icon_emoji_meigui));
        emojiList.add(new EmojiBean("[米饭]", R.mipmap.icon_emoji_mifan));
        emojiList.add(new EmojiBean("[面条]", R.mipmap.icon_emoji_miantiao));
        emojiList.add(new EmojiBean("[删除]", R.mipmap.icon_emoji_shanchu));
        emojiList.add(new EmojiBean("[奶瓶]", R.mipmap.icon_emoji_naiping));
        emojiList.add(new EmojiBean("[闹钟]", R.mipmap.icon_emoji_naozhong));
        emojiList.add(new EmojiBean("[怄火]", R.mipmap.icon_emoji_ouhuo));
        emojiList.add(new EmojiBean("[皮球]", R.mipmap.icon_emoji_piqiu));
        emojiList.add(new EmojiBean("[啤酒]", R.mipmap.icon_emoji_pijiu));
        emojiList.add(new EmojiBean("[瓢虫]", R.mipmap.icon_emoji_piaochong));
        emojiList.add(new EmojiBean("[乒乓]", R.mipmap.icon_emoji_pingpang));
        emojiList.add(new EmojiBean("[汽车]", R.mipmap.icon_emoji_qiche));
        emojiList.add(new EmojiBean("[强]", R.mipmap.icon_emoji_qiang));
        emojiList.add(new EmojiBean("[青蛙]", R.mipmap.icon_emoji_qingwa));
        emojiList.add(new EmojiBean("[拳头]", R.mipmap.icon_emoji_quantou));
        emojiList.add(new EmojiBean("[弱]", R.mipmap.icon_emoji_ruo));
        emojiList.add(new EmojiBean("[沙发]", R.mipmap.icon_emoji_shafa));
        emojiList.add(new EmojiBean("[闪电]", R.mipmap.icon_emoji_shandian));
        emojiList.add(new EmojiBean("[胜利]", R.mipmap.icon_emoji_shengli));
        emojiList.add(new EmojiBean("[示爱]", R.mipmap.icon_emoji_shiai));
        emojiList.add(new EmojiBean("[手枪]", R.mipmap.icon_emoji_shouqiang));
        emojiList.add(new EmojiBean("[衰]", R.mipmap.icon_emoji_shuai));
        emojiList.add(new EmojiBean("[太阳]", R.mipmap.icon_emoji_taiyang));
        emojiList.add(new EmojiBean("[跳绳]", R.mipmap.icon_emoji_tiaosheng));
        emojiList.add(new EmojiBean("[删除]", R.mipmap.icon_emoji_shanchu));
        emojiList.add(new EmojiBean("[跳跳]", R.mipmap.icon_emoji_tiaotiao));
        emojiList.add(new EmojiBean("[握手]", R.mipmap.icon_emoji_woshou));
        emojiList.add(new EmojiBean("[西瓜]", R.mipmap.icon_emoji_xigua));
        emojiList.add(new EmojiBean("[下雨]", R.mipmap.icon_emoji_xiayu));
        emojiList.add(new EmojiBean("[献吻]", R.mipmap.icon_emoji_xianwen));
        emojiList.add(new EmojiBean("[香蕉]", R.mipmap.icon_emoji_xiangjiao));
        emojiList.add(new EmojiBean("[象棋]", R.mipmap.icon_emoji_xiangqi));
        emojiList.add(new EmojiBean("[心碎了]", R.mipmap.icon_emoji_xinsuile));
        emojiList.add(new EmojiBean("[信封]", R.mipmap.icon_emoji_xinfeng));
        emojiList.add(new EmojiBean("[熊猫]", R.mipmap.icon_emoji_xiongmao));
        emojiList.add(new EmojiBean("[药]", R.mipmap.icon_emoji_yao));
        emojiList.add(new EmojiBean("[右车头]", R.mipmap.icon_emoji_youchetou));
        emojiList.add(new EmojiBean("[右太极]", R.mipmap.icon_emoji_youtaiji));
        emojiList.add(new EmojiBean("[雨伞]", R.mipmap.icon_emoji_yusan));
        emojiList.add(new EmojiBean("[月亮]", R.mipmap.icon_emoji_yueliang));
        emojiList.add(new EmojiBean("[炸弹]", R.mipmap.icon_emoji_zhadan));
        emojiList.add(new EmojiBean("[纸巾]", R.mipmap.icon_emoji_zhijin));
        emojiList.add(new EmojiBean("[转圈]", R.mipmap.icon_emoji_zhuanquan));
        emojiList.add(new EmojiBean("[钻戒]", R.mipmap.icon_emoji_zuanjie));
        emojiList.add(new EmojiBean("[左车头]", R.mipmap.icon_emoji_zuochetou));
        emojiList.add(new EmojiBean("[删除]", R.mipmap.icon_emoji_shanchu));
        emojiList.add(new EmojiBean("[左太极]", R.mipmap.icon_emoji_zuotaiji));
        emojiList.add(new EmojiBean("[删除]", R.mipmap.icon_emoji_shanchu));
    }
    private ChatEmoji(){

    }

    public static int getRedId(String text) {
        for(EmojiBean item : emojiList){
            if(text.equals(item.getText())){
                return item.getResId();
            }
        }
        return -1;
    }

    public static SpannableString getEmotionContent(final Context context, String source) {
        SpannableString spannableString = new SpannableString(source);
        Resources res = context.getResources();
        String regexEmotion = "\\[([\u4e00-\u9fa5\\w])+\\]";
        Pattern patternEmotion = Pattern.compile(regexEmotion);
        Matcher matcherEmotion = patternEmotion.matcher(spannableString);
        while (matcherEmotion.find()) {
            // 获取匹配到的具体字符
            String key = matcherEmotion.group();
            // 匹配字符串的开始位置
            int start = matcherEmotion.start();
            // 利用表情名字获取到对应的图片
            Integer imgRes = getRedId(key);
            if (imgRes != null) {
                Bitmap bitmap = BitmapFactory.decodeResource(res, imgRes);
                Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, DpToPxUtils.dip2px(context,20), DpToPxUtils.dip2px(context,20), true);
                ImageSpan span = new ImageSpan(context, scaleBitmap);
                spannableString.setSpan(span, start, start + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return spannableString;
    }

}
