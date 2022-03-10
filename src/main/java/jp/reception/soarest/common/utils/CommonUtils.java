package jp.reception.soarest.common.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

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

}
