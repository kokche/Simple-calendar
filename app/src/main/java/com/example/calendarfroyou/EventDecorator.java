package com.example.calendarfroyou;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BulletSpan;
import android.text.style.ForegroundColorSpan;

import androidx.core.content.ContextCompat;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.security.AccessController.getContext;

class EventDecorator implements DayViewDecorator {

    private  SharedPreferences preferences;
    private String mColor = "#0000FF";
    private final Set<String> dates;
    private  Context context;
    private CalendarDay calendarDay = null;

    public EventDecorator(SharedPreferences preferences , Context context) {
       this.preferences = preferences;
        this.dates = preferences.getAll().keySet();
        this.context = context;
    }
    public EventDecorator(Set<String> preferences , Context context,String mColor) {
        dates = preferences;
        this.mColor = mColor;
        this.context = context;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains( day.getYear()+"/"+day.getMonth()+"/"+day.getDay());

    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(19, Color.parseColor(mColor)));
       // view.addSpan(new BulletSpan(5,Color.parseColor(mColor)));
       // view.addSpan(new ForegroundColorSpan(Color.parseColor(mColor)));
        //view.setBackgroundDrawable(Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.oval_shape)));
    }
}
