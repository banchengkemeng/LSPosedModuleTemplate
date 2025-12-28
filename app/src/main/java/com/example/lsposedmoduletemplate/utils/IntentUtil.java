package com.example.lsposedmoduletemplate.utils;

import android.content.Intent;

public class IntentUtil {
    public static final String ACTION_ERROR = "ACTION_ERROR";
    public static final String ERR_MSG_KEY = "err_msg";

    public static Intent errorIntent(String errorMessage) {
        Intent intent = new Intent(ACTION_ERROR);
        intent.putExtra(ERR_MSG_KEY, errorMessage);
        return intent;
    }

    public static Intent successIntent(String action) {
        return new Intent(action);
    }
}
