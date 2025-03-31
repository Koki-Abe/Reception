package jp.reception.soarest.validator;


import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/* 
 * Empty許可の文字数制限 バリデータ
 * 
 * @author k.hagiwara
 * @version 1.0
 */

@Documented
@Constraint(validatedBy = {CanEmptySizeValidator.class})//バリデータクラスを指定
//@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE})//クラスレベルで制約する
@Target({FIELD}) // 項目に対してバリデーションをかける場合はFIELDを選ぶ
@Retention(RetentionPolicy.RUNTIME)
public @interface CanEmptySize {
	
	int min(); // 最低文字数
	int max();// 最大文字数
	
	// エラーメッセージ
	String message() default "バリデーションエラー";
	
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}



