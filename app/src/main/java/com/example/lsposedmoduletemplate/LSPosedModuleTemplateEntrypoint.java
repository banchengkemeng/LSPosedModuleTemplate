package com.example.lsposedmoduletemplate;

import com.example.lsposedmoduletemplate.utils.LogUtil;

import java.util.Objects;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class LSPosedModuleTemplateEntrypoint implements IXposedHookLoadPackage, IXposedHookZygoteInit {

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        GlobalInstance.modulePath = startupParam.modulePath;
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!Objects.equals(lpparam.packageName, lpparam.processName)) {
            return;
        }

        // 打开模块本身APP时不进行Hook
        String modulePackageName = Objects.requireNonNull(this.getClass().getPackage()).getName();
        if (Objects.equals(lpparam.packageName, modulePackageName)) {
            return;
        }

        LogUtil.info("Module Package Name: " + modulePackageName);
        LogUtil.info("Loading Package: " + lpparam.packageName);

        LogUtil.info("Hook Loading Success!");
    }
}
