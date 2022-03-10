package jp.reception.soarest.enums;

import java.util.ResourceBundle;

/*
 * メッセージEnum
 * 
 * 全画面にて出力するメッセージIDの一覧。
 * getMsgにてメッセージ文言をプロパティファイルから取得する。
 * 
 * @author k.abe
 * @version 1.0
 */
public enum MessageEnum {
    MSG_A01_W_001,
    MSG_A01_W_002,
    MSG_A01_W_003,
    MSG_A01_W_004,
    MSG_A01_W_005,
    MSG_A01_W_006,
    MSG_A01_W_007,
    MSG_A01_W_008,
    MSG_C01_W_001,
    MSG_C01_W_002,
    MSG_C01_W_003,
    MSG_C01_W_004,
    MSG_C02_W_001,
    MSG_C02_W_002,
    MSG_C02_W_003,
    MSG_C02_W_004,
    MSG_C02_W_005,
    MSG_C02_W_006,
    MSG_C02_W_007,
    MSG_C02_W_008,
    MSG_C02_W_009,
    MSG_C02_W_010,
    MSG_C02_W_011,
    MSG_C02_W_012,
    MSG_C03_I_001,
    MSG_C04_I_001,
    MSG_C05_W_001,
    MSG_C05_W_002,
    MSG_C05_W_003,
    MSG_C05_W_004,
    MSG_C05_W_005,
    MSG_C05_W_006,
    MSG_C05_W_007,
    MSG_C05_W_008,
    MSG_C05_W_009,
    MSG_C05_W_010,
    MSG_C06_I_001,
    MSG_C07_I_001,
    MSG_C08_I_001,
    MSG_C08_W_001,
    MSG_C09_I_001,
    MSG_D01_I_001,
    MSG_D01_W_001,
    MSG_D01_W_002,
    MSG_D01_W_003,
    MSG_D01_W_004,
    MSG_D02_I_001,
    MSG_D02_W_001,
    MSG_D02_W_002,
    MSG_D02_W_003,
    MSG_D02_W_004,
    MSG_D02_W_005,
    MSG_D02_W_006,
    MSG_D02_W_007,
    MSG_D02_W_008,
    MSG_D02_W_009,
    MSG_D02_W_010,
    MSG_D02_W_011,
    MSG_D02_W_012,
    MSG_D02_W_013,
    MSG_D02_W_014,
    MSG_D03_I_001,
    MSG_D04_I_001,
    MSG_D05_I_001,
    MSG_D05_W_001,
    MSG_D05_W_002,
    MSG_D05_W_003,
    MSG_D05_W_004,
    MSG_D05_W_005,
    MSG_D05_W_006,
    MSG_D05_W_007,
    MSG_D05_W_008,
    MSG_D05_W_009,
    MSG_D05_W_010,
    MSG_D05_W_011,
    MSG_D05_W_012,
    MSG_D05_W_013,
    MSG_D05_W_014,
    MSG_D06_I_001,
    MSG_D07_I_001,
    MSG_D08_I_001,
    MSG_D09_I_001,
    MSG_E01_I_001;

    // キー名を取得
    private String key = this.name().replace("_", "-");
    
    private final String VALIDATION = "validation";

    /*
     * メッセージ取得
     * 
     * @param type VALIDATION:バリデーションのエラー文言取得
     *             INFO：エラー以外の文言取得
     * @return プロパティより取得したメッセージ
     */
    public String getMsg(String type) {
        if (VALIDATION == type) {
            ResourceBundle valiRb = ResourceBundle.getBundle("ValidationMessages");
            return valiRb.getString(key);
        } else {
            ResourceBundle infRb = ResourceBundle.getBundle("properties/message");
            return infRb.getString(key);
        }
    }
}
