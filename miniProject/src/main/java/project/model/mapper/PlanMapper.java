package project.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import project.model.Plan2DTO;
import project.model.Plan3DTO;
import project.model.PlanDTO;

@Mapper
public interface PlanMapper {
	
	List<PlanDTO> findAll();
	PlanDTO findById(int planId);
	
	int insert1(PlanDTO plan);
	int insert2(Plan2DTO plan);
	int insert3(Plan3DTO plan);
	
	int update1(PlanDTO plan);
	int update2(Plan2DTO plan);
	int update3(Plan3DTO plan);
	
	int deleteById(int id);
	
}
