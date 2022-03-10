package jp.reception.soarest.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * ログ出力設定クラス
 * 
 */
public class OutputLog {
	
	public static <T> void  info(T t) {
		Logger logger = LoggerFactory.getLogger(t.getClass().getName());
		logger.info(t.getClass() + "メソッド呼び出しaa");
		logger.info(t.getClass().getSimpleName());
		System.out.println(t.getClass().getFields());
	}
	public static void error(String className) {
		
	}

}
