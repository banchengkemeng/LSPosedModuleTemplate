package com.example.lsposedmoduletemplate.utils;

import androidx.annotation.NonNull;

import com.example.lsposedmoduletemplate.GlobalInstance;
import com.example.lsposedmoduletemplate.exceptions.InvalidMethodSignatureFormatException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import de.robv.android.xposed.XposedHelpers;

/**
 * 类/方法 调用监控类
 */

public class WatchUtil {

    public static final int LOG_FORMAT_FLAG_DUMP_ARGS = 1 << 2;
    public static final int LOG_FORMAT_FLAG_DUMP_RETURN = 1 << 1;
    public static final int LOG_FORMAT_FLAG_DUMP_BACKTRACE = 1;

    private static final LogUtil logUtil = GlobalInstance.logUtil;

    public static void watchMethod(String classMethodName, int logFormatFlag, ClassLoader classLoader) throws Exception {
        MethodSignature methodSignature = MethodSignature.fromString(classMethodName);

        logUtil.debug("MethodSignature: " + methodSignature);

        String className = methodSignature.getClassName();
        Class<?> clazz = findClass(className, classLoader);

        if (clazz == null) {
            throw new ClassNotFoundException(className);
        }

        String methodName = methodSignature.getMethodName();
        Boolean isOverload = methodSignature.getOverload();
        logUtil.debug(isOverload ? "true" : "false");
        if (isOverload) {
            String[] paramTypes = methodSignature.getParamTypes();
            ArrayList<Class<?>> paramsClazzList = new ArrayList<>();
            for (String paramType : paramTypes) {
                Class<?> paramClazz = findClass(paramType, classLoader);
                if (paramClazz == null) {
                    throw new ClassNotFoundException(paramType);
                }
                paramsClazzList.add(paramClazz);
            }
            Method method = clazz.getDeclaredMethod(
                    methodName,
                    paramsClazzList.toArray(new Class<?>[0])
            );
            logUtil.debug("Method: " + method.getName());
        }
    }

    private static Class<?> findClass(String className, ClassLoader classLoader) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            return XposedHelpers.findClassIfExists(className, classLoader);
        }
    }

    private static boolean shouldDumpArgs(int logFormatFlag) {
        return (logFormatFlag & LOG_FORMAT_FLAG_DUMP_ARGS) != 0;
    }

    private static boolean shouldDumpReturn(int logFormatFlag) {
        return (logFormatFlag & LOG_FORMAT_FLAG_DUMP_RETURN) != 0;
    }

    private static boolean shouldDumpBACKTRACE(int logFormatFlag) {
        return (logFormatFlag & LOG_FORMAT_FLAG_DUMP_BACKTRACE) != 0;
    }

    private static class MethodSignature {
        private final String className;
        private final String methodName;
        private final String[] paramTypes;
        private final Boolean isOverload;

        public MethodSignature(
                String className,
                String methodName,
                String[] paramTypes,
                Boolean isOverload) {
            this.className = className;
            this.methodName = methodName;
            this.paramTypes = paramTypes;
            this.isOverload = isOverload;
        }

        public static MethodSignature fromString(String methodSignatureStr)
                throws InvalidMethodSignatureFormatException {
            try {
                int paramStart = methodSignatureStr.indexOf('(');
                int paramEnd = methodSignatureStr.indexOf(')');

                // 有左括号就必须要有右括号
                if (paramStart != -1 && paramEnd == -1) {
                    throw new RuntimeException();
                }

                String classMethodName = methodSignatureStr.substring(
                        0,
                        paramStart == -1 ? methodSignatureStr.length() : paramStart
                );
                int lastDot = classMethodName.lastIndexOf('.');
                if (lastDot == -1) {
                    throw new RuntimeException();
                }

                String className = classMethodName.substring(0, lastDot);
                String methodName = classMethodName.substring(lastDot + 1);

                if (paramStart == -1) {
                    return new MethodSignature(
                            className,
                            methodName,
                            null,
                            false
                    );
                }

                if (paramStart + 1 == paramEnd) {
                    return new MethodSignature(
                            className,
                            methodName,
                            null,
                            true
                    );
                }

                String paramsTypesStr = methodSignatureStr.substring(paramStart + 1, paramEnd);
                String[] paramTypes = paramsTypesStr.isBlank()
                        ? null
                        : Arrays.stream(paramsTypesStr.split(","))
                        .map(String::trim)
                        .toArray(String[]::new);

                return new MethodSignature(
                        className,
                        methodName,
                        paramTypes,
                        true
                );
            } catch (RuntimeException e) {
                throw new InvalidMethodSignatureFormatException(methodSignatureStr, e);
            }
        }

        public String getClassName() {
            return className;
        }

        public String getMethodName() {
            return methodName;
        }

        public String[] getParamTypes() {
            return paramTypes;
        }

        public Boolean getOverload() {
            return isOverload;
        }

        @NonNull
        @Override
        public String toString() {
            StringBuilder signatureStr = new StringBuilder();
            signatureStr.append(String.format("%s.%s", className, methodName));
            if (isOverload) {
                String paramTypesStr = Arrays.toString(paramTypes);
                signatureStr.append(String.format(
                        "(%s)",
                        paramTypes == null
                                ? ""
                                : paramTypesStr.substring(1, paramTypesStr.length() - 1)
                ));
            }
            return signatureStr.toString();
        }
    }
}
