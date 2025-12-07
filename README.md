# LSPosed Module Template
LSPosed模块 项目模板

## Custom
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