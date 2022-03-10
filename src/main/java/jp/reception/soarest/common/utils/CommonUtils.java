package jp.reception.soarest.common.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
    // ロガー
    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);
    
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
            digest = MessageDigest.getInstance("SHA-256");
            byte[] toByte = digest.digest(pass.getBytes());
            hs = String.format("%040x", new BigInteger(1, toByte));
        } catch (NoSuchAlgorithmException e) {
            // エラーログに出力
            logger.error(e.getMessage());
        }
        return hs;
    }

}
