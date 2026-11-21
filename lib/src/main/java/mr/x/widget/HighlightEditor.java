package mr.x.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Locale;

import tiiehenry.code.Lexer;
import tiiehenry.code.language.Language;
import tiiehenry.code.language.c.CLanguage;
import tiiehenry.code.language.cpp.CppLanguage;
import tiiehenry.code.language.csharp.CSharpLanguage;
import tiiehenry.code.language.java.JavaLanguage;
import tiiehenry.code.language.javascript.JavaScriptLanguage;
import tiiehenry.code.language.lua.LuaLanguage;
import tiiehenry.code.language.objectivec.ObjectiveCLanguage;
import tiiehenry.code.language.php.PHPLanguage;
import tiiehenry.code.language.python.PythonLanguage;
import tiiehenry.code.language.ruby.RubyLanguage;
import tiiehenry.code.language.xml.XMLLanguage;
import tiiehenry.code.view.CodeEditor;
import tiiehenry.code.view.ColorScheme;
import tiiehenry.code.view.ColorSchemeLight;
import tiiehenry.code.view.listener.OnKeyShortcutListener;
import tiiehenry.code.view.listener.OnRowChangedListener;
import tiiehenry.code.view.listener.OnSelectionChangedListener;

/**
 * glbjk.jar 的 Android 代码编辑器封装。
 * 用户端可以将 HighlightEditor 直接作为普通 Android View 使用。
 */
public class HighlightEditor extends CodeEditor {

    public static final String LANG_JAVA = "java";
    public static final String LANG_JAVASCRIPT = "js";
    public static final String LANG_PYTHON = "python";
    public static final String LANG_XML = "xml";
    public static final String LANG_C = "c";
    public static final String LANG_CPP = "cpp";
    public static final String LANG_CSHARP = "csharp";
    public static final String LANG_PHP = "php";
    public static final String LANG_LUA = "lua";
    public static final String LANG_RUBY = "ruby";
    public static final String LANG_OBJECTIVE_C = "objectivec";

    public HighlightEditor(Context context) {
        super(context);
        initEditor();
    }

    public HighlightEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
        initEditor();
    }

    public HighlightEditor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initEditor();
    }

    /** 初始化编辑器默认属性。 */
    private void initEditor() {
        setWordWrap(false);                 // 默认关闭自动换行
        setShowLineNumbers(true);           // 默认显示行号
        setAutoIndent(true);                // 默认开启自动缩进
        setAutoComplete(true);              // 默认开启自动补全
        setTabSpaces(4);                    // Tab 默认对应 4 个空格
        setTextSize(14);                    // 默认代码字体大小，单位 px
        setColorScheme(new ColorSchemeLight()); // 默认使用浅色配色方案
    }

    /** 设置编辑器代码内容。 */
    public HighlightEditor setCode(CharSequence code) {
        setText(code == null ? "" : code);
        return this;
    }

    /** 获取编辑器中的完整代码。 */
    public String getCode() {
        return getString();
    }

    /**
     * 设置代码语言。
     * 重要：必须调用父类的 setLanguage(Language)，它会同时设置 Lexer
     * 和 AutoCompletePanel；不能只调用 setlanguage(String)，后者只设置 Lexer。
     */
    public HighlightEditor setCodeLanguage(String language) {
        if (language == null) {
            return this;
        }

        String normalized = language.trim().toLowerCase(Locale.ROOT);
        Language selected;

        switch (normalized) {
            case "java":
                selected = JavaLanguage.getInstance();
                break;
            case "js":
            case "javascript":
                selected = JavaScriptLanguage.getInstance();
                break;
            case "python":
                selected = PythonLanguage.getInstance();
                break;
            case "xml":
                selected = XMLLanguage.getInstance();
                break;
            case "c":
                selected = CLanguage.getInstance();
                break;
            case "cpp":
            case "c++":
                selected = CppLanguage.getInstance();
                break;
            case "csharp":
            case "c#":
                selected = CSharpLanguage.getInstance();
                break;
            case "php":
                selected = PHPLanguage.getInstance();
                break;
            case "lua":
                selected = LuaLanguage.getInstance();
                break;
            case "ruby":
                selected = RubyLanguage.getInstance();
                break;
            case "objectivec":
            case "objective-c":
                selected = ObjectiveCLanguage.getInstance();
                break;
            default:
                return this;
        }

        // 关键修复：同时同步语法高亮 Lexer 与自动补全 AutoCompletePanel。
        setLanguage(selected);
        respan();
        invalidate();
        return this;
    }

    /** 在指定字符位置插入文本。 */
    public HighlightEditor insertText(int index, String text) {
        insert(index, text == null ? "" : text);
        return this;
    }

    /** 替换指定范围的文本，end 不包含在替换范围内。 */
    public HighlightEditor replaceRange(int start, int end, String text) {
        replaceText(start, end, text == null ? "" : text);
        return this;
    }

    /** 跳转到指定行，具体行号规则遵循原版 gotoLine()。 */
    public HighlightEditor goToLine(int line) {
        gotoLine(line);
        return this;
    }

    /** 设置光标位置。 */
    public HighlightEditor setCursor(int position) {
        setSelection(position);
        return this;
    }

    /** 设置文本选区。 */
    public HighlightEditor selectRange(int start, int end) {
        setSelection(start, end);
        return this;
    }

    /** 全选代码。 */
    public HighlightEditor selectAllCode() {
        selectAll();
        return this;
    }

    /** 显示或隐藏行号。 */
    public HighlightEditor showLineNumbers(boolean show) {
        setShowLineNumbers(show);
        return this;
    }

    /** 开启或关闭自动补全。 */
    public HighlightEditor autoComplete(boolean enabled) {
        setAutoComplete(enabled);
        return this;
    }

    /** 开启或关闭自动缩进。 */
    public HighlightEditor autoIndent(boolean enabled) {
        setAutoIndent(enabled);
        return this;
    }

    /** 设置 Tab 对应的空格数。 */
    public HighlightEditor setIndentSpaces(int spaces) {
        setTabSpaces(Math.max(1, spaces));
        return this;
    }

    /** 设置是否自动换行。 */
    public HighlightEditor wordWrap(boolean enabled) {
        setWordWrap(enabled);
        return this;
    }

    /** 设置代码字体大小，原版 setTextSize(int) 使用 px。 */
    public HighlightEditor setEditorTextSize(int sizePx) {
        setTextSize(sizePx);
        return this;
    }

    /** 设置编辑器缩放比例，范围由原版控件限制。 */
    public HighlightEditor setEditorZoom(float zoom) {
        setZoom(zoom);
        return this;
    }

    /** 获取当前编辑器缩放比例。 */
    public float getEditorZoom() {
        return getZoom();
    }

    /** 设置普通代码字体。 */
    public HighlightEditor setEditorTypeface(Typeface typeface) {
        if (typeface != null) {
            setTypeface(typeface);
        }
        return this;
    }

    /** 设置粗体代码字体。 */
    public HighlightEditor setEditorBoldTypeface(Typeface typeface) {
        if (typeface != null) {
            setBoldTypeface(typeface);
        }
        return this;
    }

    /** 设置斜体代码字体。 */
    public HighlightEditor setEditorItalicTypeface(Typeface typeface) {
        if (typeface != null) {
            setItalicTypeface(typeface);
        }
        return this;
    }

    /** 从字体文件加载字体。 */
    public HighlightEditor loadFont(File fontFile) {
        if (fontFile != null && fontFile.exists() && fontFile.isFile()) {
            initFont(fontFile);
        }
        return this;
    }

    /** 设置语法高亮配色方案。 */
    public HighlightEditor setEditorColorScheme(ColorScheme scheme) {
        if (scheme != null) {
            setColorScheme(scheme);
            invalidate();
        }
        return this;
    }

    /** 设置编辑器背景颜色。 */
    public HighlightEditor setEditorBackgroundColor(int color) {
        getColorScheme().setColor(ColorScheme.Colorable.BACKGROUND, color);
        invalidate();
        return this;
    }

    /** 设置普通代码文字颜色。 */
    public HighlightEditor setEditorForegroundColor(int color) {
        getColorScheme().setColor(ColorScheme.Colorable.FOREGROUND, color);
        invalidate();
        return this;
    }

    /**
     * 添加代码补全候选词。
     * 不能调用原版 addNames()，因为原版直接读取 names.length，names 为 null 时会崩溃。
     * 这里先合并旧候选词，再写回当前语言对象。
     */
    public HighlightEditor addCompletionNames(String... names) {
        if (names == null || names.length == 0) {
            return this;
        }

        Language language = Lexer.getLanguage();
        if (language == null) {
            return this;
        }

        LinkedHashSet<String> merged = new LinkedHashSet<>();
        String[] oldNames = language.getNames();

        if (oldNames != null) {
            for (String name : oldNames) {
                if (name != null && !name.trim().isEmpty()) {
                    merged.add(name);
                }
            }
        }

        for (String name : names) {
            if (name != null && !name.trim().isEmpty()) {
                merged.add(name);
            }
        }

        language.setNames(merged.toArray(new String[0]));
        respan();
        invalidate();
        return this;
    }

    /** 撤销上一次编辑。 */
    public HighlightEditor undoCode() {
        undo();
        return this;
    }

    /** 重做上一次撤销。 */
    public HighlightEditor redoCode() {
        redo();
        return this;
    }

    /** 复制当前选区。 */
    public HighlightEditor copyCode() {
        copy();
        return this;
    }

    /** 剪切当前选区。 */
    public HighlightEditor cutCode() {
        cut();
        return this;
    }

    /** 粘贴剪贴板内容。 */
    public HighlightEditor pasteCode() {
        paste();
        return this;
    }

    /** 监听文本选区变化。 */
    public HighlightEditor onSelectionChanged(OnSelectionChangedListener listener) {
        setOnSelectionChangedListener(listener);
        return this;
    }

    /** 监听光标所在行变化。 */
    public HighlightEditor onRowChanged(OnRowChangedListener listener) {
        setRowListener(listener);
        return this;
    }

    /** 监听键盘快捷键。 */
    public HighlightEditor onKeyShortcut(OnKeyShortcutListener listener) {
        setOnKeyShortcutListener(listener);
        return this;
    }
}
