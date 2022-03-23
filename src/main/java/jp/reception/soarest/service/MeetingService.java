package jp.reception.soarest.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jp.reception.soarest.domain.dto.MeetingSearchDto;
import jp.reception.soarest.domain.dto.MeetingSearchResultDto;
import jp.reception.soarest.form.MeetingSearchForm;

/*
 * 打ち合わせ情報一覧 サービスインターフェース
 * 
 * @author k.abe
 * @version 1.0
 */
@Service
public interface MeetingService {

    /*
     * 打ち合わせ情報一覧 初期処理
     * 
     * @param model モデル
     */
    void init(Model model) throws SQLException;

    /*
     * 打ち合わせ情報一覧 検索
     * 
     * @param form 打ち合わせ情報一覧 フォームクラス 
     * @param searchDto 打ち合わせ情報一覧 検索用DTO
     * @param model モデル
     * @return 検索結果
     */
    List<MeetingSearchResultDto> searchMtgList(MeetingSearchForm form, 
        MeetingSearchDto searchDto, Model model) throws SQLException;

    /*
     * 打ち合わせ情報一覧 入力チェック
     * 
     * @param form 打ち合わせ情報一覧 フォームクラス 
     * @param model モデル
     * @return 入力チェック結果
     */
    boolean inputCheck(MeetingSearchForm form, Model model);

    /*
     * 打ち合わせ情報一覧 入力値保持
     * 
     * @param form 打ち合わせ情報一覧 フォームクラス 
     * @param model モデル
     */
    void saveWord(MeetingSearchForm form, Model model);

}
