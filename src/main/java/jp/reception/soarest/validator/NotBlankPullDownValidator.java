package jp.reception.soarest.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.reception.soarest.enums.NumEnum;

/* 
 * プルダウンの選択を確認 バリデーション
 * 
 * @author k.hagiwara
 * @version 1.0
 */

public class NotBlankPullDownValidator implements ConstraintValidator<NotBlankPullDown, Integer>{
	
	@Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
		// 空白(NumEnum.PULLDOWN.getNum())を選択時にエラー
		return value != NumEnum.PULLDOWN.getNum();
	}
}



