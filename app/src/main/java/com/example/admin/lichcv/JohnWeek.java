package com.example.admin.lichcv;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Admin on 9/15/2017.
 */

public class JohnWeek {
    String title;
    String description;
    String dateFinish;
    String hourFinish;

    public JohnWeek(String title, String description, String dateFinish, String hourFinish) {
        this.title = title;
        this.description = description;
        this.dateFinish = dateFinish;
        this.hourFinish = hourFinish;
    }

    @Override
    public String toString() {
        return this.title+" - "+this.description+" - "+this.dateFinish+" - "+this.hourFinish;
    }
}

