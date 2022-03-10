package jp.reception.soarest.enums;

/**
 * @author k.abe
 *
 */
public enum UrlEnum {
	/* URL、ビューのパス、画面IDの順番 */
	// ログイン画面
	LOGIN("", "login/login", "RECEP_A_01"),
	// 管理画面TOP
	TOP("/top", "top/top", "RECEP_B_01"),
	// アカウント情報一覧
	ACCOUNT_LIST("/account_list", "account/account_list", "RECEP_C_01"),
	// アカウント情報一覧 検索結果
	ACCOUNT_SEARCH("/account_search", "", ""),
	// アカウント情報登録
	ACCOUNT_REGISTER("/account_reg", "account/account_reg", "RECEP_C_02"),
	// アカウント情報登録確認
	ACCOUNT_REGISTER_CONFIRM("/account_reg_conf", "", ""),
	// アカウント情報登録完了
	ACCOUNT_REGISTER_COMPLETE("/account_reg_comp", "", ""),
	// アカウント情報変更
	ACCOUNT_UPDATE("/account_upd", "", ""),
	// アカウント情報変更確認
	ACCOUNT_UPDATE_CONFIRM("/account_upd_conf", "", ""),
	// アカウント情報変更完了
	ACCOUNT_UPDATE_COMPLETE("/account_upd_comp", "", ""),
    // アカウント情報削除確認
	ACCOUNT_DELETE_CONFIRM("/account_del_conf", "", ""),
	// アカウント情報削除完了
	ACCOUNT_DELETE_COMPLETE("/account_del_comp", "", ""),
	// 打ち合わせ情報一覧
	MEETING_LIST("/mtg_list", "", ""),
	// 打ち合わせ情報登録
	MEETING_REGISTER("/mtg_reg", "", ""),
	// 打ち合わせ情報登録確認
	MEETING_REGISTER_CONFIRM("/mtg_reg_conf", "", ""),
	// 打ち合わせ情報登録完了
	MEETING_REGISTER_COMPLETE("/mtg_reg_comp", "", ""),
	// 打ち合わせ情報変更
	MEETING_UPDATE("/mtg_upd", "", ""),
	// 打ち合わせ情報変更確認
	MEETING_UPDATE_CONFIRM("/mtg_upd_conf", "", ""),
	// 打ち合わせ情報変更完了
	MEETING_UPDATE_COMPLETE("/mtg_upd_comp", "", ""),
	// 打ち合わせ情報削除確認
	MEETING_DELETE_CONFIRM("/mtg_del_conf", "", ""),
	// 打ち合わせ情報削除完了
	MEETING_DELETE_COMPLETE("/mtg_del_comp", "", ""),
	// システムエラー
	SYSTEM_ERROR("/syserr", "", "");
	
	// URL
	public final String url;
	// ビューのパス
	public final String pass;
	// 画面ID
	public final String id;


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
