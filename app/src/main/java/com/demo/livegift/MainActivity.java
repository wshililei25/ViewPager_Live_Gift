package com.demo.livegift;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private List<GiftEntity> giftList = new ArrayList<>();
    private int[] imgs = {R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e
            , R.mipmap.f, R.mipmap.g, R.mipmap.h, R.mipmap.i, R.mipmap.j
            , R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e
            , R.mipmap.f, R.mipmap.g, R.mipmap.h, R.mipmap.i, R.mipmap.j
            , R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e
            , R.mipmap.f, R.mipmap.g, R.mipmap.h, R.mipmap.i, R.mipmap.j};
    private PopupWindow popWindow;
    private Button btnGift;
    private ViewPager vpGiftContainer;
    private Button btnGiftSend;
    private CirclePageIndicator cpiGiftIndicator;
    private Button btnContinueClick;
    private MyCountDownTimer mc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGift = (Button) findViewById(R.id.btn_gift);
        btnGift.setOnClickListener(this);
        //模拟礼物数据
        for (int i = 1; i <= 30; i++) {
            GiftEntity gift = new GiftEntity(i + "", "礼物" + i, (i % 2 == 0 ? "1" : "2"), i + "", imgs[i - 1]);
            giftList.add(gift);
        }
        //计时器控制连续送礼物的状态
        mc = new MyCountDownTimer(3100, 100);
        Log.i("info", "giftList=" + giftList);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_gift:
                showGifViewWindow(giftList);
                break;
            case R.id.btn_gift_send:
                if ("2".equals(mGiftType)) {
                    btnContinueClick.setVisibility(View.VISIBLE);
                    mc.cancel();
                    mc.start();
                    btnGiftSend.setVisibility(View.GONE);
                }
                break;
            case R.id.btn_continue_click:
                mc.cancel();
                mc.start();
                Toast.makeText(this, "送" + mGiftName + "第" + (clickTimes) + "个", Toast.LENGTH_SHORT).show();
                clickTimes = clickTimes + 1;
                break;
        }
    }

    private String mGiftId, mGiftType, mGiftName;
    private List<View> imageViewList = new ArrayList();
    private List<CustomGrideView> grideList = new ArrayList<CustomGrideView>();

    private void initGrideView(int i) {
        CustomGrideView mGifGrideView = new CustomGrideView(this, giftList, i);
        grideList.add(mGifGrideView);
        mGifGrideView.setOnGiftSelectCallBack(new CustomGrideView.EffectGiftCallBack() {
            @Override
            public void effectGiftId(String giftId, String giftType, String giftName, int type) {
                mGiftId = giftId;
                mGiftType = giftType;
                mGiftName = giftName;
                for (CustomGrideView mGride : grideList) {
                    Log.d("XiLei", "mGride.getType()=" + mGride.getType());
                    Log.d("XiLei", "type=" + type);
                    if (mGride != null && mGride.getType() != type) {
                        mGride.clearAdapter();
                    }
                }
                resetState(giftId, giftName);
                mc.onFinish();
            }
        });
        imageViewList.add(mGifGrideView.getViews());
    }

    private void showGifViewWindow(List<GiftEntity> giftList) {
        if (popWindow == null) {
            initGiftPopwindow(giftList);
            if (giftList != null && giftList.size() != 0) {
                for (int i = 0; i < (giftList.size() - 1) / 8 + 1; i++) {
                    initGrideView(i);
                }
                GiftPageAdapter giftPageAdapter = new GiftPageAdapter();
                vpGiftContainer.setAdapter(giftPageAdapter);
                cpiGiftIndicator.setViewPager(vpGiftContainer);
            }
        }
        popWindow.showAtLocation(btnGift, Gravity.BOTTOM, 0, 0);
        resetState(mGiftId, mGiftName);
    }

    void resetState(String sendGiftId, String sendGiftName) {
        if (TextUtils.isEmpty(sendGiftId) || TextUtils.isEmpty(sendGiftId)) {//不可点击
            btnGiftSend.setClickable(false);
            btnGiftSend.setBackgroundColor(Color.GRAY);
        } else {
            btnGiftSend.setClickable(true);
            btnGiftSend.setBackgroundColor(Color.GREEN);
        }
    }

    class GiftPageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return imageViewList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View iv = imageViewList.get(position);
            // 1. 向ViewPager中添加一个view对象
            container.addView(iv);
            // 2. 返回当前添加的view对象
            return iv;
        }
    }

    void initGiftPopwindow(final List<GiftEntity> giftList) {
        View popView = View.inflate(this, R.layout.window_gift, null);
        vpGiftContainer = (ViewPager) popView.findViewById(R.id.vp_gift_container);
        cpiGiftIndicator = (CirclePageIndicator) popView.findViewById(R.id.cpi_gift_indicator);

        btnContinueClick = (Button) popView.findViewById(R.id.btn_continue_click);
        btnContinueClick.setOnClickListener(this);
        btnGiftSend = (Button) popView.findViewById(R.id.btn_gift_send);
        btnGiftSend.setOnClickListener(this);

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = getResources().getDimensionPixelOffset(R.dimen.pop_gift_height);
        popWindow = PopwindowUtil.getPopWindow(this, popView, width, height, R.color.half_transparent);
        //popWindow消失监听方法
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //改变图标状态
                mc.onFinish();
            }
        });
    }

    /**
     * 计时器控制连续送礼物的状态
     */
    private int clickTimes = 2;
    private Boolean isContinteFinish = false;

    class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            clickTimes = 2;
            if (isContinteFinish) {
                isContinteFinish = false;
                if (btnContinueClick != null && btnGiftSend != null) {
                    btnContinueClick.setVisibility(View.GONE);
                    btnGiftSend.setVisibility(View.VISIBLE);
                }
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            isContinteFinish = true;
            if (btnContinueClick != null) {
                btnContinueClick.setText("连送" + "\n" + millisUntilFinished / 100);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mc.onFinish();
    }
}
