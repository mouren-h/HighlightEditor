package mr.x.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import java.io.File;

import tiiehenry.code.view.CodeEditor;
import tiiehenry.code.view.ColorScheme;
import tiiehenry.code.view.ColorSchemeLight;
import tiiehenry.code.view.listener.OnKeyShortcutListener;
import tiiehenry.code.view.listener.OnRowChangedListener;
import tiiehenry.code.view.listener.OnSelectionChangedListener;

/**
 * glbjk.jar CodeEditor 的 XML/Java 封装。
 *
 * XML 属性统一使用 editor 前缀，避免与 Android 系统属性冲突。
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
        initDefaults();
    }

    public HighlightEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDefaults();
        readAttributes(context, attrs);
    }

    public HighlightEditor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefaults();
        readAttributes(context, attrs);
    }

    private void initDefaults() {
        setWordWrap(false);
        setShowLineNumbers(true);
        setAutoIndent(true);
        setAutoComplete(true);
        setTabSpaces(4);
        setTextSize(14);
        setColorScheme(new ColorSchemeLight());
    }

    private void readAttributes(Context context, AttributeSet attrs) {
        if (attrs == null) return;

        TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.HighlightEditor
        );

        try {
            String language = a.getString(
                    R.styleable.HighlightEditor_editorLanguage
            );
            if (language != null && language.length() > 0) {
                setCodeLanguage(language);
            }

            setWordWrap(a.getBoolean(
                    R.styleable.HighlightEditor_editorWordWrap, false));

            setShowLineNumbers(a.getBoolean(
                    R.styleable.HighlightEditor_editorShowLineNumbers, true));

            setAutoIndent(a.getBoolean(
                    R.styleable.HighlightEditor_editorAutoIndent, true));

            setAutoComplete(a.getBoolean(
                    R.styleable.HighlightEditor_editorAutoComplete, true));

            setTabSpaces(a.getInt(
                    R.styleable.HighlightEditor_editorTabSpaces, 4));

            setAutoIndentWidth(a.getInt(
                    R.styleable.HighlightEditor_editorAutoIndentWidth, 1));

            setHighlightCurrentRow(a.getBoolean(
                    R.styleable.HighlightEditor_editorHighlightCurrentRow, false));

            setNonPrintingCharVisibility(a.getBoolean(
                    R.styleable.HighlightEditor_editorNonPrintingCharVisibility, false));

            setLongPressCaps(a.getBoolean(
                    R.styleable.HighlightEditor_editorLongPressCaps, false));

            setZoom(a.getFloat(
                    R.styleable.HighlightEditor_editorZoom, 1.0f));

            setChirality(a.getBoolean(
                    R.styleable.HighlightEditor_editorChirality, false));

            // glbjk.jar 的 caretWidth 是公开字段，不提供 setter。
            caretWidth = a.getDimensionPixelSize(
                    R.styleable.HighlightEditor_editorCaretWidth, caretWidth);

            // isShowRegion 同样是原库公开字段。
            isShowRegion = a.getBoolean(
                    R.styleable.HighlightEditor_editorShowRegion, isShowRegion);

            // 原库 setTextSize(int) 接收像素值；dimension 会把 XML 的 sp/dp 转成 px。
            if (a.hasValue(R.styleable.HighlightEditor_editorTextSize)) {
                setTextSize(a.getDimensionPixelSize(
                        R.styleable.HighlightEditor_editorTextSize, 14));
            }

            int typefaceRes = a.getResourceId(
                    R.styleable.HighlightEditor_editorTypeface, 0);
            if (typefaceRes != 0) {
                setTypeface(context.getResources().getFont(typefaceRes));
            }

            int boldTypefaceRes = a.getResourceId(
                    R.styleable.HighlightEditor_editorBoldTypeface, 0);
            if (boldTypefaceRes != 0) {
                setBoldTypeface(context.getResources().getFont(boldTypefaceRes));
            }

            int italicTypefaceRes = a.getResourceId(
                    R.styleable.HighlightEditor_editorItalicTypeface, 0);
            if (italicTypefaceRes != 0) {
                setItalicTypeface(context.getResources().getFont(italicTypefaceRes));
            }
        } finally {
            a.recycle();
        }
    }

    public HighlightEditor setCode(CharSequence code) {
        setText(code == null ? "" : code);
        return this;
    }

    public String getCode() {
        return getString();
    }

    public HighlightEditor setCodeLanguage(String language) {
        if (language != null) {
            setlanguage(language.toLowerCase(java.util.Locale.ROOT));
            respan();
        }
        return this;
    }

    public HighlightEditor insertText(int index, String text) {
        insert(index, text == null ? "" : text);
        return this;
    }

    public HighlightEditor replaceRange(int start, int end, String text) {
        replaceText(start, end, text == null ? "" : text);
        return this;
    }

    public HighlightEditor goToLine(int line) {
        gotoLine(line);
        return this;
    }

    public HighlightEditor setCursor(int position) {
        setSelection(position);
        return this;
    }

    public HighlightEditor selectRange(int start, int end) {
        setSelection(start, end);
        return this;
    }

    public HighlightEditor selectAllCode() {
        selectAll();
        return this;
    }

    public HighlightEditor showLineNumbers(boolean show) {
        setShowLineNumbers(show);
        return this;
    }

    public HighlightEditor autoComplete(boolean enabled) {
        setAutoComplete(enabled);
        return this;
    }

    public HighlightEditor autoIndent(boolean enabled) {
        setAutoIndent(enabled);
        return this;
    }

    public HighlightEditor setIndentSpaces(int spaces) {
        setTabSpaces(Math.max(0, spaces));
        return this;
    }

    public HighlightEditor wordWrap(boolean enabled) {
        setWordWrap(enabled);
        return this;
    }

    /** 参数为像素值；XML 的 editorTextSize 可直接使用 sp。 */
    public HighlightEditor setEditorTextSize(int sizePx) {
        setTextSize(sizePx);
        return this;
    }

    public HighlightEditor setEditorTypeface(Typeface typeface) {
        if (typeface != null) setTypeface(typeface);
        return this;
    }

    public HighlightEditor setEditorBoldTypeface(Typeface typeface) {
        if (typeface != null) setBoldTypeface(typeface);
        return this;
    }

    public HighlightEditor setEditorItalicTypeface(Typeface typeface) {
        if (typeface != null) setItalicTypeface(typeface);
        return this;
    }

    public HighlightEditor loadFont(File fontFile) {
        if (fontFile != null && fontFile.exists()) initFont(fontFile);
        return this;
    }

    public HighlightEditor setEditorColorScheme(ColorScheme scheme) {
        if (scheme != null) setColorScheme(scheme);
        return this;
    }

    public HighlightEditor addCompletionNames(String... names) {
        if (names != null) addNames(names);
        return this;
    }

    public HighlightEditor undoCode() {
        undo();
        return this;
    }

    public HighlightEditor redoCode() {
        redo();
        return this;
    }

    public HighlightEditor copyCode() {
        copy();
        return this;
    }

    public HighlightEditor cutCode() {
        cut();
        return this;
    }

    public HighlightEditor pasteCode() {
        paste();
        return this;
    }

    public HighlightEditor onSelectionChanged(OnSelectionChangedListener listener) {
        setOnSelectionChangedListener(listener);
        return this;
    }

    public HighlightEditor onRowChanged(OnRowChangedListener listener) {
        setRowListener(listener);
        return this;
    }

    public HighlightEditor onKeyShortcut(OnKeyShortcutListener listener) {
        setOnKeyShortcutListener(listener);
        return this;
    }
}
