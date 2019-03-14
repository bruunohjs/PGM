package com.dev.marcellocamara.pgm.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.marcellocamara.pgm.Contract.IAdapter;
import com.dev.marcellocamara.pgm.Helper.CardHelper;
import com.dev.marcellocamara.pgm.Model.CardModel;
import com.dev.marcellocamara.pgm.R;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/***
    marcellocamara@id.uff.br
            2019
***/

public class CardsAdapter extends PagerAdapter {

    @BindView(R.id.layoutCard) protected ConstraintLayout layoutCard;

    @BindView(R.id.imageViewSelectedFlag) protected ImageView imageViewSelectedFlag;

    @BindView(R.id.textViewTitleCard) protected TextView textViewTitleCard;
    @BindView(R.id.textViewCardNumber) protected TextView textViewCardNumber;
    @BindView(R.id.textViewUserName) protected TextView textViewUserName;

    @BindString(R.string.card_number) protected String card_number;

    private IAdapter onViewPagerClick;
    private List<CardModel> cards;
    private String userName;
    private Context context;

    public CardsAdapter(List<CardModel> cards, String userName, Context context, IAdapter onViewPagerClick) {
        this.onViewPagerClick = onViewPagerClick;
        this.cards = cards;
        this.userName = userName;
        this.context = context;
    }

    @Override
    public int getCount() {
        return cards.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.adapter_cards, container, false);

        ButterKnife.bind(this, view);

        layoutCard.setBackground( CardHelper.getBackground(context, cards.get(position).getCardColor()) );
        imageViewSelectedFlag.setImageDrawable( CardHelper.getFlag(context, cards.get(position).getCardFlag()) );

        textViewTitleCard.setText(cards.get(position).getCardTitle());
        textViewCardNumber.setText( (card_number) + (cards.get(position).getFinalDigits()) );
        textViewUserName.setText(userName);

        container.addView(view);

        layoutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewPagerClick.OnItemClick(position);
            }
        });

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}