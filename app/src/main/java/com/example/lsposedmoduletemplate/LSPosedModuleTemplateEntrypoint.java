package com.example.lsposedmoduletemplate;

import com.example.lsposedmoduletemplate.utils.LogUtil;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class LSPosedModuleTemplateEntrypoint implements IXposedHookLoadPackage {

    private final LogUtil logUtil = new LogUtil("LSPosedModuleTemplate");

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        logUtil.info("Hook Loading Success!");
    }
}
