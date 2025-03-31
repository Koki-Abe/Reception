package jp.reception.soarest.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.thymeleaf.util.StringUtils;

/* 
 * Empty許可の文字数制限 バリデーション
 * 
 * @author k.hagiwara
 * @version 1.0
 */

public class CanEmptySizeValidator implements ConstraintValidator<CanEmptySize, String>{
	
	// 最低文字数,最大文字数
	int min,max;
	
	@Override
    public void initialize(CanEmptySize annotation) {
		this.min = annotation.min();
		this.max = annotation.max();
    }
	
	@Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
		// Null,空白出ないときは文字数制限を破るとエラー
        return (StringUtils.isEmpty(value) || (value.length() >= min && value.length() <= max) );
	}
}



