package jp.reception.soarest.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.reception.soarest.domain.dto.AuthSearchResultDto;
import jp.reception.soarest.domain.dto.DepartmentSearchResultDto;

/*
 * 共通 リポジトリインターフェース
 * 
 * 
 */
@Mapper
public interface CommonRepository {
	
	// 部署プルダウン取得
	List<DepartmentSearchResultDto> searchDepList();
	
	// ロールプルダウン取得
	List<AuthSearchResultDto> searchAuthList();

}
