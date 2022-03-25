package jp.reception.soarest.common.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import jp.reception.soarest.domain.dto.AuthSearchResultDto;
import jp.reception.soarest.domain.dto.DepartmentSearchResultDto;
import jp.reception.soarest.domain.dto.MeetingRoomSearchResultDto;
import jp.reception.soarest.domain.dto.PurposeSearchResultDto;
import jp.reception.soarest.domain.dto.StaffSearchResultDto;
import jp.reception.soarest.enums.CharEnum;
import jp.reception.soarest.enums.NumEnum;

/*
 * 共通 ユーティリティクラス
 * 
 * @author k.abe
 * @version 1.0
 */
public class CommonUtils {
    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpSession session;

    // ハッシュ化方式
    private final static String WAY_OF_HASH = "SHA-256";
    
    // ハッシュフォーマット
    private final static String HASH_FORMAT = "%040x";

    // 部署リスト
    private final static String DEP_LIST = "depList";

    // 権限リスト
    private final static String AUTH_LIST = "authList";

    // 担当者リスト
    private final static String STAFF_LIST = "staffList";

    // 会議室リスト
    private final static String ROOM_LIST = "roomList";

    // 目的リスト
    private final static String PURPOSE_LIST = "purposeList";

    /*
     * パスワードをハッシュ化した値を返却
     * 
     * @param pass パスワード
     * @return hs ハッシュ値
     */
    public static String makeHash(String pass) throws NoSuchAlgorithmException {

        // MessageDigestでのハッシュ化
        MessageDigest digest;
        
        String hs = new String();
        try {
            digest = MessageDigest.getInstance(WAY_OF_HASH);
            byte[] toByte = digest.digest(pass.getBytes());
            hs = String.format(HASH_FORMAT, new BigInteger(1, toByte));
        } catch (NoSuchAlgorithmException e) {
            // 例外をスローする
            throw new NoSuchAlgorithmException(e);
        }
        return hs;
    }

    /*
     * システム日時取得(yyyy/MM/dd HH:mm:ss)
     * 
     * @return sysDate システム日時
     */
    public static String getSysdate() {
        // システム日時を設定
        LocalDateTime nowDate = LocalDateTime.now();
        DateTimeFormatter java8Format = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String sysDate = nowDate.format(java8Format);
        
        return sysDate;
    }
    
    /*
     * システム日時取得(yyyy/MM/dd)
     * 
     * @return sysDate システム日時
     */
    public static String getSysDay() {
        // システム日時を設定
        LocalDateTime nowDate = LocalDateTime.now();
        DateTimeFormatter java8Format = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String sysDate = nowDate.format(java8Format);
        
        return sysDate;
    }

    /*
     * システム日時取得(HH:mm)
     * 
     * @return sysDate システム日時
     */
    public static String getSysTime() {
        // システム日時を設定
        LocalDateTime nowDate = LocalDateTime.now();
        DateTimeFormatter java8Format = DateTimeFormatter.ofPattern("HH:mm");
        String sysDate = nowDate.format(java8Format);
        
        return sysDate;
    }

    /*
     * エラーログ出力
     * 
     * @param logger ロガー
     * @param ex 例外クラス
     * @param errMsg エラーメッセージ
     */
    public static void outputErrLog(Logger logger, Exception ex, String errMsg) {

        // エラー内容を出力
        logger.error(errMsg);
        logger.error(ex.getMessage());
        ex.printStackTrace();
    }

    /*
     * プルダウン成形
     * 
     * @param model モデル
     * @param list 呼び元のリスト
     * @param obj リストの型
     */
    @SuppressWarnings("unchecked")
    public static <E> void makePulldown(Model model, List<E> list, E obj) {
        
        // 部署プルダウンの場合
        if (obj instanceof DepartmentSearchResultDto) {
            // リストの作成
            List<E> depList = new ArrayList<>();
            depList.addAll(list);

            // 部署プルダウンの初めにブランクを設定
            DepartmentSearchResultDto dep = new DepartmentSearchResultDto();
            dep.setDepId(NumEnum.PULLDOWN.getNum());
            dep.setDepName(CharEnum.BLANK.getChar());

            // 最初にブランクを表示させるため、要素の最初に挿入
            depList.add(0, (E)dep);

            model.addAttribute(DEP_LIST, depList);
        // 権限プルダウンの場合
        } else if (obj instanceof AuthSearchResultDto) {
            List<E> authList = new ArrayList<>();
            authList.addAll(list);
            
            // 権限プルダウンの初めにブランクを設定
            AuthSearchResultDto auth = new AuthSearchResultDto();
            auth.setAuthId(NumEnum.PULLDOWN.getNum());
            auth.setAuthName(CharEnum.BLANK.getChar());

            // 最初にブランクを表示させるため、要素の最初に挿入
            authList.add(0, (E)auth);
            
            model.addAttribute(AUTH_LIST, authList);
        // 主担当、副担当プルダウンの場合
        } else if(obj instanceof StaffSearchResultDto) {
            List<E> staffList = new ArrayList<>();
            staffList.addAll(list);
            
            // 主担当、副担当プルダウンの初めにブランクを設定
            StaffSearchResultDto staff = new StaffSearchResultDto();
            staff.setStaffId("");
            staff.setStaffName(CharEnum.BLANK.getChar());

            // 最初にブランクを表示させるため、要素の最初に挿入
            staffList.add(0, (E)staff);
            
            model.addAttribute(STAFF_LIST, staffList);
        // 会議室プルダウンの場合
        } else if(obj instanceof MeetingRoomSearchResultDto) {
            List<E> roomList = new ArrayList<>();
            roomList.addAll(list);
            
            // 会議室プルダウンの初めにブランクを設定
            MeetingRoomSearchResultDto room = new MeetingRoomSearchResultDto();
            room.setRoomId(NumEnum.PULLDOWN.getNum());
            room.setRoomName(CharEnum.BLANK.getChar());

            // 最初にブランクを表示させるため、要素の最初に挿入
            roomList.add(0, (E)room);
            
            // 会議室プルダウンの最後にその他(9999)を設定
            room = new MeetingRoomSearchResultDto();
            room.setRoomId(9999);
            room.setRoomName("その他");
            roomList.add(roomList.size(), (E)room);
            
            model.addAttribute(ROOM_LIST, roomList);
        // 目的プルダウンの場合
        } else if(obj instanceof PurposeSearchResultDto) {
            List<E> purposeList = new ArrayList<>();
            purposeList.addAll(list);
                
            // 目的プルダウンの初めにブランクを設定
            PurposeSearchResultDto purpose = new PurposeSearchResultDto();
            purpose.setMtgId(NumEnum.PULLDOWN.getNum());
            purpose.setMtgName(CharEnum.BLANK.getChar());

            // 最初にブランクを表示させるため、要素の最初に挿入
            purposeList.add(0, (E)purpose);
                
            model.addAttribute(PURPOSE_LIST, purposeList);
        }
    }

}
