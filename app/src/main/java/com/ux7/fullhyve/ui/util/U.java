package com.ux7.fullhyve.ui.util;

import com.ux7.fullhyve.R;
import com.ux7.fullhyve.services.Utility.Realtime;
import com.ux7.fullhyve.ui.enums.TaskStatus;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by hp on 06/08/18.
 */

public class U {

    public static int getTaskStatusIcon(TaskStatus status) {

        switch (status) {

            case WAITING:

                return R.drawable.ic_waiting_icon;

            case APPROVED:

                return R.drawable.ic_tick_icon;

            case INPROGRESS:

                return R.drawable.ic_progress_icon;

            case PENDINGREVISION:

                return R.drawable.ic_pending_revision_icon;

            case PENDINGEVALUATION:

                return R.drawable.ic_pending_eval_icon;

            default:

                return 0;
        }

    }

    public static String getTaskStatusString(TaskStatus status) {

        switch (status) {

            case WAITING:

                return "Waiting";

            case APPROVED:

                return "Approved";

            case INPROGRESS:

                return "In Progress";

            case PENDINGREVISION:

                return "Pending Revision";

            case PENDINGEVALUATION:

                return "Pending Evaluation";

            default:

                return "";
        }
    }

    public static String getImageUrl(String imageUrl) {

        return Realtime.URL + "file/" + imageUrl;

    }

    public static String getTime(String iso) {

        String time = "";
        int year, month, date, hour, second, minute;

        if (iso.charAt(0) == 'a') {

            Date dat = new Date(Long.parseLong(iso.substring(1)));

            year = dat.getYear();
            month = dat.getMonth();
            date = dat.getDate();
            hour = dat.getHours();
            minute = dat.getMinutes();
            second = dat.getSeconds();

        } else {

            year = Integer.parseInt(iso.substring(0, 4));
            month = Integer.parseInt(iso.substring(5, 7));
            date = Integer.parseInt(iso.substring(8, 10));
            hour = Integer.parseInt(iso.substring(11, 13));
            minute = Integer.parseInt(iso.substring(14, 16));
            second = Integer.parseInt(iso.substring(17, 19));

        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, date, hour, minute, second);
        long thenTime = calendar.getTime().getTime();

        Calendar now = Calendar.getInstance();
        long nowTime = now.getTime().getTime();

        Date d = new Date();
        int timeZoneHours = d.getTimezoneOffset()/60;
        hour = (hour + timeZoneHours) % 24;
        String ampm = (hour >= 12 ? "PM" : "AM");
        hour = hour % 12;
        hour = (hour == 0 ? 12 : hour);
        time += hour + ":" + (minute < 10 ? "0" : "") + minute + ":" + (second < 10 ? "0" : "") + second + " " + ampm;

        return time;

    }

    public static String makeTime() {

        String time = (new Date()).toLocaleString();
        int hour = time.indexOf(":");
        while (time.charAt(hour) != ' ')
            hour--;

        return time.substring(hour);

    }

}
