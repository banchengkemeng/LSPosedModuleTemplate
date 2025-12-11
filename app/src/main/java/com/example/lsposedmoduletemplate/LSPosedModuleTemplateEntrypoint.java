package com.example.lsposedmoduletemplate;

import com.example.lsposedmoduletemplate.utils.LogUtil;
import com.example.lsposedmoduletemplate.utils.WatchUtil;

import java.util.Objects;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class LSPosedModuleTemplateEntrypoint implements IXposedHookLoadPackage {

    private final LogUtil logUtil = new LogUtil("LSPosedModuleTemplate");

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!Objects.equals(lpparam.packageName, lpparam.processName)) {
            return;
        }

        logUtil.info("Loading Package: " + lpparam.packageName);

        GlobalInstance.logUtil = logUtil;
        logUtil.info("Hook Loading Success!");

        int flag = WatchUtil.LOG_FORMAT_FLAG_DUMP_ARGS |
                WatchUtil.LOG_FORMAT_FLAG_DUMP_BACKTRACE |
                WatchUtil.LOG_FORMAT_FLAG_DUMP_RETURN;

        try {
            WatchUtil.watchClass(
                    lpparam.classLoader,
                    0,
                    "com.example.demoapp.testdemo.TestClass"
            );
//            WatchUtil.watchMethod(
//                    lpparam.classLoader,
//                    flag,
//                    false,
//                    "com.example.demoapp.testdemo.TestClass",
//                    "test"
//            );

//            WatchUtil.watchConstructor(
//                    lpparam.classLoader,
//                    flag,
//                    true,
//                    "com.example.demoapp.testdemo.TestClass",
//                    String.class
//            );
        } catch (Throwable e) {
            logUtil.error(e);
            throw e;
        }
    }
}
