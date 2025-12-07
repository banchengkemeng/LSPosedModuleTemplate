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