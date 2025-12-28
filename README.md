# LSPosed Module Template
LSPosed模块 项目模板

## 自定义
### Project Name
1. settings.gradle: rootProject.name

### SDK Path
1. local.properties: sdk.dir

### Class Name
1. 全局搜索 "com.example.lsposedmoduletemplate

### Entrypoint Class Name
1. app/src/main/assets/xposed_init
2. directory name
3. com/example/lsposedmoduletemplate/LSPosedModuleTemplateEntrypoint.java filename

### App Name
1. app/src/main/res/values/strings.xml: app_name

## 项目结构介绍
### 1. controller
WebServer控制器
> 该包存放WebServer接口(不强制放在该包内), 使用AndServer框架, 接口编写方式参照Sprint注解方式的Web开发
### 2. service
Android后台服务
1. HttpService 
> 使用Service方式启动AndServer
### 3. ui
模块用户界面
1. HomeFragment
   > 主页Fragment, 用来配置AndServer监听的地址和端口
2. MainActivity
    > 入口Activity
### 4. utils
工具类
#### 4.1. utils.IntentUtil
Intent工具类
> 用于快速创建逻辑一致的Intent
#### 4.2. utils.LogUtil
日志工具类
> 用于打印日志, 会同时输出到 Logcat 和 XposedBridge
#### 4.3. utils.WatchUtil
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
### 5. GlobalInstance
存放全局实例
### 6. LSPosedModuleTemplateEntrypoint
模块入口类
### 7. WebServerManager
AndServer-WebServer管理器
> 管理WebServer的创建、启动、停止、状态


