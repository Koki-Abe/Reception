package jp.reception.soarest.domain.dto;

import lombok.Data;

/*
 * アカウント情報一覧 検索用DTO
 * 
 * author k.abe
 * version 1.0
 */
@Data
public class AccountSearchDto {
    // ユーザーID
    private String userId;

    // ユーザー名
    private String userName;

    // 部署ID
    private int depId;

    // 権限ID
    private int authId;

    // 最終ログイン日(開始)
    private String loginDateStart;

    // 最終ログイン日(終了)
    private String loginDateEnd;
}
