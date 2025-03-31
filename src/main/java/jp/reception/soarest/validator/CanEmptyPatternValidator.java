package jp.reception.soarest.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.thymeleaf.util.StringUtils;

/* 
 * Empty許可の文字列パターン制限 バリデーション
 * 
 * @author k.hagiwara
 * @version 1.0
 */

public class CanEmptyPatternValidator implements ConstraintValidator<CanEmptyPattern, String>{
	
	// 文字列パターン(正規表現)
	String regexp;
	
	@Override
    public void initialize(CanEmptyPattern annotation) {
		this.regexp = annotation.regexp();
    }
	
	@Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
		// Null,空白出ないときは文字列パターン制限を破るとエラー
        return (StringUtils.isEmpty(value) || value.matches(regexp) );
	}
}



