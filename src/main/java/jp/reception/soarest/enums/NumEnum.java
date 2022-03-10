package jp.reception.soarest.enums;
/*
 * 共通数値Enum
 * 
 * 共通で使用する数値を定義する
 * 
 * @author k.abe
 * @version 1.0
 */
public enum NumEnum {
    /* 数値 */
    ZERO(0),
    PULLDOWN(999);

    NumEnum(int commonNum) {
        this.commonNum = commonNum;
    }

    // 共通数値
    private final int commonNum;

    // 数値を取得
    public int getNum() {
        return this.commonNum;
    }

}
