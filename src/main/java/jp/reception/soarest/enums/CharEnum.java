package jp.reception.soarest.enums;
/*
 * 共通文字Enum
 * 
 * 空文字やスラッシュなど、共通で使用する
 * 文字を定義する
 * 
 * @author k.abe
 * @version 1.0
 */
public enum CharEnum {
    /* 文字列 */
    BLANK(""),
    SLASH("/"),
    HYPHEN("-"),
    VALIDATION("validation"),
    REDIRECT("redirect:"),
    FORWARD("forward:"),
    START(" : START"),
    END(" : END");

    CharEnum(String commonChar) {
        this.commonChar = commonChar;
    }

    // 共通文字列
    private final String commonChar;

    // 文字を取得
    public String getChar() {
        return this.commonChar;
    }

}
