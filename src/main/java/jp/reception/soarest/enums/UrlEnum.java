package jp.reception.soarest.enums;

/*
 * URLEnum
 * 
 * 全画面のURL、return値、画面IDを返却する。
 * getMsgにてメッセージ文言をプロパティファイルから取得する。
 * getUrl:URLを返却 getPass:ビューのパスを返却 getId:画面IDを返却
 * 
 * @author k.abe
 * @version 1.0
 */
public enum UrlEnum {
    /* URL、ビューのパス、画面IDの順番 */
    // ログイン画面
    LOGIN("/", "login/login", "RECEP_A_01"),
    // 管理画面TOP
    TOP("/top", "top/top", "RECEP_B_01"),
    // アカウント情報一覧
    ACCOUNT_LIST("/account_list", "account/account_list", "RECEP_C_01"),
    // アカウント情報一覧 検索結果
    ACCOUNT_SEARCH("/account_search", "", ""),
    // アカウント情報登録
    ACCOUNT_REGISTER("/account_register", "account/account_reg", "RECEP_C_02"),
    // アカウント情報登録確認
    ACCOUNT_REGISTER_CONFIRM("/account_register_conf", "account/account_reg_conf", "RECEP_C_03"),
    // アカウント情報登録完了
    ACCOUNT_REGISTER_COMPLETE("/account_register_comp", "account/account_reg_comp", "RECEP_C_04"),
    // アカウント情報変更
    ACCOUNT_UPDATE("/account_update", "account/account_upd", "RECEP_C_05"),
    // アカウント情報変更確認
    ACCOUNT_UPDATE_CONFIRM("/account_update_conf", "account/account_reg_conf", "RECEP_C_06"),
    // アカウント情報変更完了
    ACCOUNT_UPDATE_COMPLETE("/account_update_comp", "account/account_reg_comp", "RECEP_C_07"),
    // アカウント情報削除確認
    ACCOUNT_DELETE_CONFIRM("/account_delete_conf", "account/account_del_conf", "RECEP_C_08"),
    // アカウント情報削除完了
    ACCOUNT_DELETE_COMPLETE("/account_delete_comp", "account/account_del_comp", "RECEP_C_09"),
    // 打ち合わせ情報一覧
    MEETING_LIST("/mtg_list", "meeting/mtg_list", "RECEP_D_01"),
    // 打ち合わせ情報登録
    MEETING_REGISTER("/mtg_register", "meeting/mtg_reg", "RECEP_D_02"),
    // 打ち合わせ情報登録確認
    MEETING_REGISTER_CONFIRM("/mtg_register_conf", "meeting/mtg_reg_conf", "RECEP_D_03"),
    // 打ち合わせ情報登録完了
    MEETING_REGISTER_COMPLETE("/mtg_register_comp", "meeting/mtg_reg_comp", "RECEP_D_04"),
    // 打ち合わせ情報変更
    MEETING_UPDATE("/mtg_upd", "meeting/mtg_upd", "RECEP_D_05"),
    // 打ち合わせ情報変更確認
    MEETING_UPDATE_CONFIRM("/mtg_update_conf", "meeting/mtg_upd_conf", "RECEP_D_06"),
    // 打ち合わせ情報変更完了
    MEETING_UPDATE_COMPLETE("/mtg_update_comp", "meeting/mtg_reg_comp", "RECEP_D_07"),
    // 打ち合わせ情報削除確認
    MEETING_DELETE_CONFIRM("/mtg_delete_conf", "meeting/mtg_del_conf", "RECEP_D_08"),
    // 打ち合わせ情報削除完了
    MEETING_DELETE_COMPLETE("/mtg_delete_comp", "meeting/mtg_del_comp", "RECEP_D_09"),
    // 打ち合わせ情報一覧 コメント表示
    MEETING_COMMENT("/comment", "meeting/comment", ""),
    // システムエラー
    SYSTEM_ERROR("/syserr", "error", "RECEP_E_01");
    
    // URL
    private final String url;
    // ビューのパス
    private final String pass;
    // 画面ID
    private final String id;

    // コンストラクタ
    UrlEnum(String url, String pass, String id) {
        this.url = url;
        this.pass = pass;
        this.id = id;
    }
    // URLを返却
    public String getUrl() {
        return this.url;
    }
    // ビューのパスを返却
    public String getPass() {
        return pass;
    }
    // 画面IDを返却
    public String getId() {
        return id;
    }
}
