package jp.reception.soarest.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import jp.reception.soarest.domain.dto.AuthSearchResultDto;
import jp.reception.soarest.domain.dto.DepartmentSearchResultDto;

/*
 * 共通 リポジトリ実装クラス
 * 
 * @author k.abe
 * @version 1.0
 */
public class CommonRepositoryImpl implements CommonRepository{
	
	@Autowired
	private CommonRepository commontRepository;
	
	@Override
	// 部署プルダウン取得
	public List<DepartmentSearchResultDto> searchDepList() {
		return commontRepository.searchDepList();
	}

	@Override
	// 権限プルダウン取得
	public List<AuthSearchResultDto> searchAuthList() {
		return commontRepository.searchAuthList();
	}
}
