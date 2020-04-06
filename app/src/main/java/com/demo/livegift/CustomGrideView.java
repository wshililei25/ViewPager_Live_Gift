package com.demo.livegift;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

/**
 * 每一页礼物自定义布局
 */
public class CustomGrideView implements AdapterView.OnItemClickListener {

    private String TAG = CustomGrideView.class.getSimpleName();
    private List<GiftEntity> gift;
    private int mType = 0;
    private Context ctx;
    private GridView layout_grideaa;
    private CustomGridViewAdapter grideViewAdapter;

    public CustomGrideView(Context ctx, List<GiftEntity> gift, int type) {
        this.ctx = ctx;
        this.gift = gift;
        this.mType = type;
    }

    public int getType() {
        return mType;
    }

    public View getViews() {
        View view = View.inflate(ctx, R.layout.layout_custom_grideview, null);
        layout_grideaa = (GridView) view.findViewById(R.id.gv_grideviews);
        List<GiftEntity> newGift = getNewGiftList(mType);
        grideViewAdapter = new CustomGridViewAdapter(ctx, newGift, -1);
        layout_grideaa.setAdapter(grideViewAdapter);
        layout_grideaa.setOnItemClickListener(this);
        return view;
    }

    private List<GiftEntity> newGiftList = new ArrayList<>();

    /*
     * 读取每页礼物
     * */
    private List<GiftEntity> getNewGiftList(int type) {
        int maxLength = 0;
        if (type * 8 + 8 > gift.size()) {
            maxLength = gift.size();
        } else {
            maxLength = type * 8 + 8;
        }
        for (int i = type * 8; i < maxLength; i++) {
            newGiftList.add(new GiftEntity(
                    gift.get(i).getId(), gift.get(i).getName(), gift.get(i).getType(), gift.get(i).getPrice(), gift.get(i).getPic())
            );
        }
        return newGiftList;
    }

    private int selectId = -1;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Log.d("XiLei", "selectId111=" + selectId);
        Log.d("XiLei", "position111=" + position);

        if (selectId != position) {
            GiftEntity giftBean = (GiftEntity) parent.getItemAtPosition(position);
            onGiftSelectCallBack.effectGiftId(giftBean.getId(), giftBean.getType(), giftBean.getName(), mType);
            selectId = position;
            grideViewAdapter.setIndex(selectId);
            grideViewAdapter.notifyDataSetChanged();
        } else {
            onGiftSelectCallBack.effectGiftId("", "", "", mType);
            selectId = -1;
            grideViewAdapter.setIndex(selectId);
            grideViewAdapter.notifyDataSetChanged();
        }
    }

    public void clearAdapter() {
        selectId = -1;
        grideViewAdapter.setIndex(selectId);
        grideViewAdapter.notifyDataSetChanged();
    }

    public interface EffectGiftCallBack {
        void effectGiftId(String giftId, String giftType, String giftName, int type);
    }

    public EffectGiftCallBack onGiftSelectCallBack;

    public void setOnGiftSelectCallBack(EffectGiftCallBack onGiftSelectCallBack) {
        this.onGiftSelectCallBack = onGiftSelectCallBack;
    }
}
