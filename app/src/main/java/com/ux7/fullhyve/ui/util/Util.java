package com.ux7.fullhyve.ui.util;

import com.ux7.fullhyve.R;
import com.ux7.fullhyve.ui.enums.TaskStatus;

/**
 * Created by hp on 06/08/18.
 */

public class Util {

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

}
