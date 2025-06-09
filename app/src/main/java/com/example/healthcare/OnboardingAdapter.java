package com.example.healthcare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.viewpager.widget.PagerAdapter;

public class OnboardingAdapter extends PagerAdapter {
    private Context context;
    private int[] images = {R.drawable.onboarding1, R.drawable.onboarding2};
    private String[] titles = {"Множество специалистов в одном месте", "Запись к врачу в пару кликов\n"};
    private int[] descriptions = {R.drawable.page1, R.drawable.page2};

    public OnboardingAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_onboarding, null);

        ImageView image = view.findViewById(R.id.imageOnboarding);
        TextView title = view.findViewById(R.id.textTitle);
        ImageView image2  = view.findViewById(R.id.textDescription);

        image.setImageResource(images[position]);
        title.setText(titles[position]);
        image2.setImageResource(descriptions[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}