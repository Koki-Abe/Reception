package jp.reception.soarest.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jp.reception.soarest.domain.dto.AccountSearchDto;
import jp.reception.soarest.domain.dto.AccountSearchResultDto;
import jp.reception.soarest.form.AccountSearchForm;

@Service
public interface AccountService {

//	// マッパー
//	@Autowired
//	private TestMapperRepository testMapper;
//	
//	
//	// テスト検索
//	public List<TestEntity> findRoom() {
//		return testMapper.findRoom();
//	}
	
//	public List<TestEntity> findRoom();
	
	public List<AccountSearchResultDto> searchAccountList(AccountSearchForm form, 
        AccountSearchDto searchDto, Model model);

}
