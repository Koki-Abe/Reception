package jp.reception.soarest.validator;


import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/* 
 * Empty許可の文字列パターン制限 バリデータ
 * 
 * @author k.hagiwara
 * @version 1.0
 */

@Documented
@Constraint(validatedBy = {CanEmptyPatternValidator.class})//バリデータクラスを指定
//@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE})//クラスレベルで制約する
@Target({FIELD}) // 項目に対してバリデーションをかける場合はFIELDを選ぶ
@Retention(RetentionPolicy.RUNTIME)
public @interface CanEmptyPattern {
	
	// 文字列パターン(正規表現)
	String regexp();
	
	// エラーメッセージ
	String message() default "バリデーションエラー";
	
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}



