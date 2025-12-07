package com.example.lsposedmoduletemplate;

import com.example.lsposedmoduletemplate.utils.LogUtil;
import com.example.lsposedmoduletemplate.utils.WatchUtil;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class LSPosedModuleTemplateEntrypoint implements IXposedHookLoadPackage {

    private final LogUtil logUtil = new LogUtil("LSPosedModuleTemplate");

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        GlobalInstance.logUtil = logUtil;
        logUtil.info("Hook Loading Success!");

        try {
            XposedHelpers.findAndHookMethod(
                    "com.huluxia.ui.account.AuthActivity",
                    lpparam.classLoader,
                    "onCreate",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            logUtil.info("Test");
                            super.beforeHookedMethod(param);
                        }
                    }
            );
        } catch (Exception e) {
            logUtil.error(e);
            throw e;
        }


//        XposedHelpers.findAndHookMethod(
//                "android.app.Application",
//                lpparam.classLoader,
//                "onCreate",
//                new XC_MethodHook() {
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        logUtil.info(param.toString());
//                        try {
//                            WatchUtil.watchMethod(
//                                    "com.huluxia.ui.account.AuthActivity.onCreate(android.os.Bundle)",
//                                    WatchUtil.LOG_FORMAT_FLAG_DUMP_ARGS |
//                                            WatchUtil.LOG_FORMAT_FLAG_DUMP_RETURN |
//                                            WatchUtil.LOG_FORMAT_FLAG_DUMP_BACKTRACE,
//                                    lpparam.classLoader
//                            );
//                        } catch (Exception e) {
//                            logUtil.error(e);
//                            throw e;
//                        }
//
//                        super.afterHookedMethod(param);
//                    }
//                }
//        );
    }
}
