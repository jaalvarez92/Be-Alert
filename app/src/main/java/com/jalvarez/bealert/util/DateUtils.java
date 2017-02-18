package com.jalvarez.bealert.util;

import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by jalvarez on 2/17/17.
 * This is a file created for the project BeAlert
 * <p>
 * Javier Alvarez Gonzalez
 * Android Developer
 * javierag0292@gmail.com
 * San Jose, Costa Rica
 */

public class DateUtils {

    public Date getDateFromDatePicker(DatePicker datePicker){
        Calendar calendar = new GregorianCalendar();
        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
        return calendar.getTime();
    }
}
