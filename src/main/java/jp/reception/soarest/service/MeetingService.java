package jp.reception.soarest.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import jp.reception.soarest.domain.dto.MeetingDeleteDto;
import jp.reception.soarest.domain.dto.MeetingRegisterDto;
import jp.reception.soarest.domain.dto.MeetingSearchDto;
import jp.reception.soarest.domain.dto.MeetingSearchResultDto;
import jp.reception.soarest.form.MeetingDeleteForm;
import jp.reception.soarest.form.MeetingRegisterForm;
import jp.reception.soarest.form.MeetingSearchForm;
import jp.reception.soarest.form.MeetingUpdateForm;

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
    void init(Model model);

    /*
     * 打ち合わせ情報一覧 検索
     * 
     * @param form 打ち合わせ情報一覧 フォームクラス 
     * @param searchDto 打ち合わせ情報一覧 検索用DTO
     * @param model モデル
     * @return 検索結果
     */
    List<MeetingSearchResultDto> searchMtgList(MeetingSearchForm form, 
        MeetingSearchDto searchDto, Model model);
    
    /*
     * 打ち合わせ情報登録 登録
     * 
     * @param form 打ち合わせ情報登録 フォームクラス 
     * @param registerDto 打ち合わせ情報登録 登録用DTO
     * @param model モデル
     * @return 検索結果
     */
    int registerMtg(MeetingRegisterForm form, 
    		MeetingRegisterDto registerDto, Model model, String staffID);
    
    /*
     * 打ち合わせ情報削除 削除
     * 
     * @param form 打ち合わせ情報削除 フォームクラス 
     * @param searchDto 打ち合わせ情報削除 削除用DTO
     * @param model モデル
     * @return 検索結果
     */
    int deletehMtg(MeetingDeleteForm form, MeetingDeleteDto deleteDto, Model model);
    
    /*
     * 打ち合わせ情報削除 削除対象の最終アップデート時間を取得
     * @param form 打ち合わせ情報削除 フォームクラス 
     * @param model モデル
     */
    void getLastDate(MeetingDeleteForm form, Model model);
    
    /*
     * 打ち合わせ情報削除 削除対象のデータをチェック
     * 
     * @param form 打ち合わせ情報削除 フォームクラス 
     * @param model モデル
     */
    int checkData(MeetingDeleteForm form, Model model);
    
    /*
     * 打ち合わせ情報一覧 入力チェック
     * 
     * @param form 打ち合わせ情報一覧 フォームクラス 
     * @param model モデル
     * @return 入力チェック結果
     */
    boolean inputCheck(MeetingSearchForm form, Model model);
    
    /*
     * 打ち合わせ情報更新 入力チェック
     * 
     * @param form 打ち合わせ情報一覧 フォームクラス 
     * @param model モデル
     * @return 入力チェック結果
     */
    boolean inputCheck(MeetingUpdateForm form, Model model);

    /*
     * 打ち合わせ情報登録 入力チェック
     * 
     * @param form 打ち合わせ情報登録 フォームクラス 
     * @param model モデル
     * @return 入力チェック結果
     */
    boolean inputCheck(MeetingRegisterForm form, BindingResult result, 
    		Model model, List<String> errorList);
    
    /*
     * 打ち合わせ情報一覧 入力値保持
     * 
     * @param form 打ち合わせ情報一覧 フォームクラス 
     * @param model モデル
     */
    void saveWord(MeetingSearchForm form, Model model);
    
    
    /*
     * 打ち合わせ登録 入力値保持
     * 
     * @param form 打ち合わせ情報登録 フォームクラス 
     * @param model モデル
     */
    void saveWord(MeetingRegisterForm form, Model model);
    
    /*
     * 打ち合わせ情報更新 入力値保持
     * 
     * @param form 打ち合わせ情報一覧 フォームクラス 
     * @param model モデル
     */
    void saveWord(MeetingUpdateForm form, Model model);
    
    /*
     * 打ち合わせ情報削除 入力値保持
     * 
     * @param form 打ち合わせ情報一覧 フォームクラス 
     * @param model モデル
     */
    void saveWord(MeetingDeleteForm form, Model model);
    
}
