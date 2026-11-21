<div align="center">

# HighlightEditor

**面向 Android 的 Java 代码编辑器封装组件**

基于 `glbjk.jar` 中的 `CodeEditor` 进行继承封装，提供更直接的控件使用方式，并整合代码语言设置、语法高亮、自动补全、字体与颜色配置等常用接口。

[![JitPack](https://jitpack.io/v/com.github.mouren-h/HighlightEditor.svg)](https://jitpack.io/#com.github.mouren-h/HighlightEditor)
![Android](https://img.shields.io/badge/Platform-Android-3DDC84?logo=android&logoColor=white)
![Language](https://img.shields.io/badge/Language-Java-orange?logo=java)
![Min SDK](https://img.shields.io/badge/Min%20SDK-26-blue)

</div>

---

## 📖 项目简介

`HighlightEditor` 是一个 Android 代码编辑器控件封装库。

项目通过继承原始编辑器的 `tiiehenry.code.view.CodeEditor`，将原有编辑能力封装为 `mr.x.widget.HighlightEditor`，方便在 Android XML 布局或 Java 代码中直接使用。

主要特点：

- **语法高亮**：可选择内置语言解析器，并配置编辑器配色方案。
- **自动补全**：支持开启自动补全和添加自定义候选词。
- **代码编辑**：提供设置、获取、插入、替换代码，以及撤销、重做等封装方法。
- **编辑器配置**：支持字体大小、缩放、字体样式、背景色、前景色、行号、自动缩进和自动换行等设置。
- **原生 API 继承**：继承 `CodeEditor`，原始父类的公开方法也可以直接使用。

> 本项目是对原始编辑器的封装，不是重新实现一套代码编辑引擎。实际编辑与语法解析能力以所依赖的 `glbjk.jar` 为准。

## ✨ 功能一览

| 功能 | 说明 |
| --- | --- |
| 多语言选择 | Java、JavaScript、Python、XML、C、C++、C#、PHP、Lua、Ruby、Objective-C |
| 语法高亮 | 通过语言解析器与配色方案实现 |
| 自动补全 | 开启补全并添加自定义候选词 |
| 行号显示 | 显示或隐藏行号 |
| 字体设置 | 设置字体大小、普通字体、粗体和斜体 |
| 缩放 | 调整编辑器缩放比例 |
| 代码操作 | 获取、设置、插入、替换代码 |
| 撤销与重做 | 撤销编辑及恢复撤销 |
| 选区与光标 | 设置光标、选择文本、全选、跳转行 |
| 编辑器颜色 | 设置背景色、普通文字颜色或整体配色方案 |
| 编辑辅助 | 自动缩进、Tab 空格数、自动换行 |
| 事件监听 | 监听选区变化、光标行变化和键盘快捷键 |

## 📦 安装

### 方式一：通过 JitPack 引入

在项目根目录的 `settings.gradle` 或 `settings.gradle.kts` 中添加 JitPack 仓库。

**Groovy**
```gradle
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

**Kotlin DSL**
```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

然后在应用模块的 `build.gradle` 或 `build.gradle.kts` 中添加依赖。

**Groovy**
```gradle
dependencies {
    implementation 'com.github.mouren-h:HighlightEditor:1.0'
}
```

**Kotlin DSL**
```kotlin
dependencies {
    implementation("com.github.mouren-h:HighlightEditor:1.0")
}
```

> 如果 JitPack 构建版本或 Tag 与示例不同，请将 `1.0` 替换为仓库中实际发布且构建成功的版本号。

### 方式二：本地 Android Library 集成

如果你是从源码构建本项目，请将原始依赖文件放在库模块的 `libs` 目录：

```text
HighlightEditor/
├── libs/
│   └── glbjk.jar
├── src/
│   └── main/
│       ├── java/
│       │   └── mr/x/widget/
│       │       └── HighlightEditor.java
│       └── AndroidManifest.xml
└── build.gradle.kts
```

在库模块的 `build.gradle.kts` 中声明：

```kotlin
dependencies {
    api(files("libs/glbjk.jar"))
}
```

使用 `api` 可以将父类所在依赖暴露给使用该库的模块。发布前请检查最终 AAR 中是否包含所需的原始类，并在干净的示例工程中验证依赖。

## 🚀 快速开始

### 1. 在 XML 中添加控件

```xml
<mr.x.widget.HighlightEditor
    android:id="@+id/codeEditor"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

### 2. 在 Java 中使用

```java
import mr.x.widget.HighlightEditor;

HighlightEditor editor = findViewById(R.id.codeEditor);

// 选择 Java 语言解析器
editor.setCodeLanguage(HighlightEditor.LANG_JAVA);

// 设置初始代码
editor.setCode(
    "public class Main {\n" +
    "    public static void main(String[] args) {\n" +
    "        System.out.println(\"Hello World\");\n" +
    "    }\n" +
    "}"
);

// 设置字体大小（单位：px）
editor.setEditorTextSize(22);

// 显示行号
editor.showLineNumbers(true);

// 开启自动缩进和自动补全
editor.autoIndent(true);
editor.autoComplete(true);

// 添加自定义补全候选词
editor.addCompletionNames(
    "public",
    "private",
    "protected",
    "static",
    "final",
    "void",
    "class",
    "return",
    "String",
    "int",
    "boolean",
    "System",
    "println"
);
```

## 🌐 支持的语言

通过 `setCodeLanguage()` 选择语言：

| 常量 | 语言 |
| --- | --- |
| `HighlightEditor.LANG_JAVA` | Java |
| `HighlightEditor.LANG_JAVASCRIPT` | JavaScript |
| `HighlightEditor.LANG_PYTHON` | Python |
| `HighlightEditor.LANG_XML` | XML |
| `HighlightEditor.LANG_C` | C |
| `HighlightEditor.LANG_CPP` | C++ |
| `HighlightEditor.LANG_CSHARP` | C# |
| `HighlightEditor.LANG_PHP` | PHP |
| `HighlightEditor.LANG_LUA` | Lua |
| `HighlightEditor.LANG_RUBY` | Ruby |
| `HighlightEditor.LANG_OBJECTIVE_C` | Objective-C |

示例：

```java
editor.setCodeLanguage(HighlightEditor.LANG_PYTHON);
```

## 🎨 主题与颜色

### 设置编辑器背景色

```java
import android.graphics.Color;

editor.setEditorBackgroundColor(
    Color.parseColor("#1E1E1E")
);
```

### 设置普通代码文字颜色

```java
editor.setEditorForegroundColor(Color.WHITE);
```

### 使用内置浅色配色方案

```java
import tiiehenry.code.view.ColorSchemeLight;

editor.setEditorColorScheme(new ColorSchemeLight());
```

`setEditorColorScheme()` 用于设置整体配色方案；`setEditorBackgroundColor()` 和 `setEditorForegroundColor()` 用于单独修改背景色和普通文字颜色。语法高亮中各类 token 的颜色取决于当前配色方案。

## 🔧 常用 API

以下是 `HighlightEditor` 封装类中提供的常用方法：

| 方法 | 说明 |
| --- | --- |
| `setCode(CharSequence code)` | 设置代码内容 |
| `getCode()` | 获取完整代码 |
| `setCodeLanguage(String language)` | 设置代码语言 |
| `insertText(int index, String text)` | 在指定位置插入文本 |
| `replaceRange(int start, int end, String text)` | 替换指定范围文本 |
| `goToLine(int line)` | 跳转到指定行 |
| `setCursor(int position)` | 设置光标位置 |
| `selectRange(int start, int end)` | 选择文本范围 |
| `selectAllCode()` | 全选代码 |
| `showLineNumbers(boolean show)` | 显示或隐藏行号 |
| `autoComplete(boolean enabled)` | 开启或关闭自动补全 |
| `addCompletionNames(String... names)` | 添加补全候选词 |
| `autoIndent(boolean enabled)` | 开启或关闭自动缩进 |
| `setIndentSpaces(int spaces)` | 设置 Tab 对应的空格数 |
| `wordWrap(boolean enabled)` | 设置是否自动换行 |
| `setEditorTextSize(int sizePx)` | 设置字体大小，单位 px |
| `setEditorZoom(float zoom)` | 设置编辑器缩放比例 |
| `getEditorZoom()` | 获取编辑器缩放比例 |
| `setEditorTypeface(Typeface typeface)` | 设置普通字体 |
| `setEditorBoldTypeface(Typeface typeface)` | 设置粗体字体 |
| `setEditorItalicTypeface(Typeface typeface)` | 设置斜体字体 |
| `loadFont(File fontFile)` | 从字体文件加载字体 |
| `setEditorColorScheme(ColorScheme scheme)` | 设置配色方案 |
| `setEditorBackgroundColor(int color)` | 设置编辑器背景色 |
| `setEditorForegroundColor(int color)` | 设置普通文字颜色 |
| `undoCode()` | 撤销 |
| `redoCode()` | 重做 |
| `copyCode()` | 复制选中内容 |
| `cutCode()` | 剪切选中内容 |
| `pasteCode()` | 粘贴剪贴板内容 |
| `onSelectionChanged(listener)` | 监听选区变化 |
| `onRowChanged(listener)` | 监听光标所在行变化 |
| `onKeyShortcut(listener)` | 监听键盘快捷键 |

由于 `HighlightEditor` 继承自 `tiiehenry.code.view.CodeEditor`，父类的其他可访问公开方法也可以直接调用。具体参数和行为请以当前依赖版本中的类定义为准。

## 🔤 字体大小与缩放说明

```java
editor.setEditorTextSize(22); // 字体大小，单位 px
editor.setEditorZoom(1.0f);   // 缩放比例
```

- `setEditorTextSize()` 设置代码字体像素大小。
- `setEditorZoom()` 设置编辑器缩放比例。
- 不要将 `100.0f` 当作 100% 传给缩放方法；缩放值应使用原始控件支持的比例范围。

## ⚠️ 注意事项

1. **语言设置与补全**：建议先调用 `setCodeLanguage()`，再开启自动补全并添加候选词。
2. **补全候选词**：`addCompletionNames()` 会把传入的候选词合并到当前语言的名称列表中；请在设置语言后调用。
3. **字体单位**：`setEditorTextSize()` 的参数单位是 px，不是 sp。
4. **原始依赖**：本项目依赖 `glbjk.jar` 中的编辑器实现。若从源码构建，请确保 JAR 位于 `libs/glbjk.jar`，并正确配置 Gradle 依赖。
5. **多编辑器语言状态**：原始编辑器的语言处理涉及共享状态；同一页面同时使用不同语言的多个编辑器时，建议进行实际测试。
6. **构建验证**：发布前请在本地执行构建，并确认 JitPack 对应 Tag 的构建日志成功。

## 🧱 项目信息

| 项目 | 内容 |
| --- | --- |
| 项目名称 | HighlightEditor |
| 控件类 | `mr.x.widget.HighlightEditor` |
| 父类 | `tiiehenry.code.view.CodeEditor` |
| 原始依赖 | `glbjk.jar` |
| Namespace | `mr.x.widget` |
| 最低 Android SDK | 26 |
| Java 版本 | Java 8 |

## 📄 License

请根据仓库中实际添加的 `LICENSE` 文件查看和遵守项目许可证，以及原始 `glbjk.jar` 的授权条件。封装代码与原始依赖的许可证可能不同。

---

<div align="center">

**HighlightEditor · Android Code Editing Component**

如果这个项目对你有帮助，欢迎 Star ⭐

</div>
