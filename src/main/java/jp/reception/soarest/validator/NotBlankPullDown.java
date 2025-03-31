package jp.reception.soarest.validator;


import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/* 
 * プルダウンの選択を確認 バリデータ
 * 
 * @author k.hagiwara
 * @version 1.0
 */

@Documented
@Constraint(validatedBy = {NotBlankPullDownValidator.class})//バリデータクラスを指定
//@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE})//クラスレベルで制約する
@Target({FIELD}) // 項目に対してバリデーションをかける場合はFIELDを選ぶ
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlankPullDown {
	
	// エラーメッセージ
	String message() default "バリデーションエラー";
	
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}



