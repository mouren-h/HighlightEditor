package com.junnian.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Locale;

import tiiehenry.code.Lexer;
import tiiehenry.code.language.Language;

import tiiehenry.code.view.CodeEditor;
import tiiehenry.code.view.ColorScheme;
import tiiehenry.code.view.ColorSchemeLight;
import tiiehenry.code.view.listener.OnKeyShortcutListener;
import tiiehenry.code.view.listener.OnRowChangedListener;
import tiiehenry.code.view.listener.OnSelectionChangedListener;

/**
 * 对 glbjk.jar 中 CodeEditor 的简单封装。
 * 用户端直接把 HighlightEditor 当作普通 Android View 添加即可。
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

    private void initEditor() {
        setWordWrap(false);
        setShowLineNumbers(true);
        setAutoIndent(true);
        setAutoComplete(true);
        setTabSpaces(4);
        setTextSize(14);
        setColorScheme(new ColorSchemeLight());
    }

    /** 设置代码内容。 */
    public HighlightEditor setCode(CharSequence code) {
        setText(code == null ? "" : code);
        return this;
    }

    /** 获取完整代码。 */
    public String getCode() {
        return getString();
    }

    /** 设置语法高亮语言，参数使用 LANG_* 常量。 */
    public HighlightEditor setCodeLanguage(String language) {
        if (language == null) return this;

        // 原版 CodeEditor.setlanguage() 使用字符串引用比较（==），
        // 因此必须传入 intern 后的标准字符串，避免动态字符串无法匹配语言。
        String normalized = language.trim().toLowerCase(Locale.ROOT).intern();
        setlanguage(normalized);
        respan();
        return this;
    }

    /** 在指定字符偏移处插入文本。 */
    public HighlightEditor insertText(int index, String text) {
        insert(index, text == null ? "" : text);
        return this;
    }

    /** 替换指定范围的文本，end 为结束位置（不包含）。 */
    public HighlightEditor replaceRange(int start, int end, String text) {
        replaceText(start, end, text == null ? "" : text);
        return this;
    }

    /** 跳转到指定行，行号从 1 开始。 */
    public HighlightEditor goToLine(int line) {
        gotoLine(line);
        return this;
    }

    /** 设置光标位置。 */
    public HighlightEditor setCursor(int position) {
        setSelection(position);
        return this;
    }

    /** 设置选区。 */
    public HighlightEditor selectRange(int start, int end) {
        setSelection(start, end);
        return this;
    }

    /** 全选代码。 */
    public HighlightEditor selectAllCode() {
        selectAll();
        return this;
    }

    /** 开启/关闭行号。 */
    public HighlightEditor showLineNumbers(boolean show) {
        setShowLineNumbers(show);
        return this;
    }

    /** 开启/关闭自动补全。 */
    public HighlightEditor autoComplete(boolean enabled) {
        setAutoComplete(enabled);
        return this;
    }

    /** 开启/关闭自动缩进。 */
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

    /** 设置文字大小，单位为 sp。 */
    public HighlightEditor setEditorTextSize(int sizeSp) {
        setTextSize(sizeSp);
        return this;
    }

    /** 设置普通字体。 */
    public HighlightEditor setEditorTypeface(Typeface typeface) {
        if (typeface != null) setTypeface(typeface);
        return this;
    }

    /** 设置粗体字体。 */
    public HighlightEditor setEditorBoldTypeface(Typeface typeface) {
        setBoldTypeface(typeface);
        return this;
    }

    /** 设置斜体字体。 */
    public HighlightEditor setEditorItalicTypeface(Typeface typeface) {
        setItalicTypeface(typeface);
        return this;
    }

    /** 从字体文件加载字体（具体格式由 Android Typeface 支持情况决定）。 */
    public HighlightEditor loadFont(File fontFile) {
        if (fontFile != null && fontFile.exists()) initFont(fontFile);
        return this;
    }

    /** 设置语法配色方案。 */
    public HighlightEditor setEditorColorScheme(ColorScheme scheme) {
        if (scheme != null) setColorScheme(scheme);
        return this;
    }

    /** 添加代码补全候选词。 */
    public HighlightEditor addCompletionNames(String... names) {
        if (names == null || names.length == 0) return this;

        // 原版 addNames() 直接对 Language.getNames() 的结果取 length，
        // 但原版 JavaLanguage 等语言的 names 初始值可能为 null，导致 NPE。
        Language language = Lexer.getLanguage();
        if (language == null) return this;

        LinkedHashSet<String> merged = new LinkedHashSet<>();
        String[] oldNames = language.getNames();
        if (oldNames != null) {
            for (String name : oldNames) {
                if (name != null && !name.isEmpty()) merged.add(name);
            }
        }
        for (String name : names) {
            if (name != null && !name.isEmpty()) merged.add(name);
        }

        language.setNames(merged.toArray(new String[0]));
        respan();
        invalidate();
        return this;
    }

    /** 撤销。 */
    public HighlightEditor undoCode() {
        undo();
        return this;
    }

    /** 重做。 */
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

    /** 监听选区变化。 */
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
