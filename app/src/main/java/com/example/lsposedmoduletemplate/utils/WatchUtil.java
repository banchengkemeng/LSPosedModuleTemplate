package com.example.lsposedmoduletemplate.utils;

import com.example.lsposedmoduletemplate.GlobalInstance;

import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/**
 * 类/方法 调用监控类
 */

public class WatchUtil {

    public static final int LOG_FORMAT_FLAG_DUMP_ARGS = 1 << 2;
    public static final int LOG_FORMAT_FLAG_DUMP_RETURN = 1 << 1;
    public static final int LOG_FORMAT_FLAG_DUMP_BACKTRACE = 1;

    private static final LogUtil logUtil = GlobalInstance.logUtil;

    public static void watchConstructor(
            ClassLoader classLoader,
            int logFormatFlag,
            Boolean isSpecified,
            String className,
            Class<?>... paramTypes
    ) {
        Class<?> clazz = XposedHelpers.findClass(className, classLoader);

        if (isSpecified) {
            ArrayList<Object> parameterTypesAndCallback = new ArrayList<>(Arrays.asList(paramTypes));
            parameterTypesAndCallback.add(new WatchMethodHook(logFormatFlag));
            XposedHelpers.findAndHookConstructor(clazz, parameterTypesAndCallback.toArray());
            return;
        }

        for (Constructor<?> declaredConstructor : clazz.getDeclaredConstructors()) {
            declaredConstructor.setAccessible(true);
            ArrayList<Object> parameterTypesAndCallback = new ArrayList<>(Arrays.asList(
                    declaredConstructor.getParameterTypes()
            ));
            parameterTypesAndCallback.add(new WatchMethodHook(logFormatFlag));
            XposedHelpers.findAndHookConstructor(clazz, parameterTypesAndCallback.toArray());
        }
    }

    public static void watchMethod(
            ClassLoader classLoader,
            int logFormatFlag,
            Boolean isSpecified,
            String className,
            String methodName,
            Class<?>... paramTypes
    ) {
        Class<?> clazz = XposedHelpers.findClass(className, classLoader);

        if (isSpecified) {
            ArrayList<Object> parameterTypesAndCallback = new ArrayList<>(Arrays.asList(paramTypes));
            parameterTypesAndCallback.add(new WatchMethodHook(logFormatFlag));
            XposedHelpers.findAndHookMethod(clazz, methodName, parameterTypesAndCallback.toArray());
            return;
        }

        for (Method declaredMethod : clazz.getDeclaredMethods()) {
            declaredMethod.setAccessible(true);
            XposedBridge.hookMethod(declaredMethod, new WatchMethodHook(logFormatFlag));
        }
    }
}

class WatchMethodHook extends XC_MethodHook {

    private static final LogUtil logUtil = GlobalInstance.logUtil;
    private final int logFormatFlag;

    public WatchMethodHook(int logFormatFlag) {
        this.logFormatFlag = logFormatFlag;
    }

    @Override
    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
        try {
            logUtil.info("Call Before: " + buildCallMethodSignature(param));
            if (shouldDumpArgs()) {
                logUtil.info("Args: " + Arrays.toString(param.args));
            }
            super.beforeHookedMethod(param);
        } catch (Throwable e) {
            logUtil.error(e);
            throw e;
        }
    }

    @Override
    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        try {
            super.afterHookedMethod(param);
            if (!shouldDumpReturn() && !shouldDumpBACKTRACE()) {
                return;
            }
            logUtil.info("Call After: " + buildCallMethodSignature(param));
            if (shouldDumpReturn()) {
                logUtil.info("Return: " + param.getResult());
            }
            if (shouldDumpBACKTRACE()) {
                logUtil.info("Backtrace: " + "test");
            }
        } catch (Throwable e) {
            logUtil.error(e);
            throw e;
        }
    }

    private String buildCallMethodSignature(MethodHookParam param) {
        Member method = param.method;
        Class<?> clazz = method.getDeclaringClass();

        Class<?>[] parameterTypes;
        Class<?> returnType;
        boolean isConstructor = Objects.equals(clazz.getName(), method.getName());
        if (isConstructor) {
            Constructor<?> constructorBestMatch = XposedHelpers.findConstructorBestMatch(clazz, param.args);
            parameterTypes = constructorBestMatch.getParameterTypes();
            returnType = void.class;
        } else {
            Method methodBestMatch = XposedHelpers.findMethodBestMatch(
                    clazz,
                    method.getName(),
                    param.args
            );
            parameterTypes = methodBestMatch.getParameterTypes();
            returnType = methodBestMatch.getReturnType();
        }

        String paramsArrayString = Arrays.toString(
                Arrays.stream(parameterTypes)
                        .map(Class::getName)
                        .toArray()
        );
        return String.format("%s %s.%s(%s)",
                returnType.getName(),
                method.getDeclaringClass().getName(),
                method.getName(),
                paramsArrayString.length() > 2
                        ? paramsArrayString.substring(1, paramsArrayString.length() - 1)
                        : ""
        );
    }

    private String buildArgs(MethodHookParam param) {
        return "";
    }

    private boolean shouldDumpArgs() {
        return (logFormatFlag & WatchUtil.LOG_FORMAT_FLAG_DUMP_ARGS) != 0;
    }

    private boolean shouldDumpReturn() {
        return (logFormatFlag & WatchUtil.LOG_FORMAT_FLAG_DUMP_RETURN) != 0;
    }

    private boolean shouldDumpBACKTRACE() {
        return (logFormatFlag & WatchUtil.LOG_FORMAT_FLAG_DUMP_BACKTRACE) != 0;
    }
}