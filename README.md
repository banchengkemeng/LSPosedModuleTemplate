# LSPosed Module Template
LSPosed模块 项目模板

## 自定义
### Project Name
1. settings.gradle.kts: rootProject.name

### SDK Path
1. local.properties: sdk.dir

### Class Name
1. app/build.gradle.kts: android.namespace
2. app/build.gradle.kts: android.defaultConfig.applicationId

### Entrypoint Class Name
1. app/src/main/assets/xposed_init
2. directory name
3. com/example/lsposedmoduletemplate/LSPosedModuleTemplateEntrypoint.java filename

### App Name
1. app/src/main/res/values/strings.xml: app_name

## 工具类
### 1. utils.LogUtil
日志工具类
> 用于打印日志, 会同时输出到 Logcat 和 XposedBridge
### 2. utils.WatchUtil
监控工具类
1. 监控 构造器/函数 的调用 watchConstructor/watchMethod
2. 监控 类中所有函数/构造器 的调用 watchClass
```text
Log格式如下: 
Signature: int com.example.demoapp.testdemo.TestClass.test(java.lang.String, int, boolean)
Args: (t3a1, 222, false)
Return: 222
Backtrace: java.lang.Throwable
    at com.example.lsposedmoduletemplate.utils.WatchMethodHook.afterHookedMethod(WatchUtil.java:110)
    at wn.kePklpCSbRNRz.OfbLQ.XposedBridge$LegacyApiSupport.handleAfter(Unknown Source:33)
    at J.callback(Unknown Source:294)
    at LSPHooker_.test(Unknown Source:25)
    at com.example.demoapp.MainActivity.lambda$onCreate$0(MainActivity.java:54)
    at com.example.demoapp.MainActivity$$ExternalSyntheticLambda0.onClick(D8$$SyntheticClass:0)
    at android.view.View.performClick(View.java:7448)
    at android.view.View.performClickInternal(View.java:7425)
    at android.view.View.access$3600(View.java:810)
    at android.view.View$PerformClick.run(View.java:28305)
    at android.os.Handler.handleCallback(Handler.java:938)
    at android.os.Handler.dispatchMessage(Handler.java:99)
    at android.os.Looper.loop(Looper.java:223)
    at android.app.ActivityThread.main(ActivityThread.java:7700)
    at java.lang.reflect.Method.invoke(Native Method)
    at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:592)
    at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:947)
```

