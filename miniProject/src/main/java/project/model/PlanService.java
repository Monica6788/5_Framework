package project.model;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PlanService {
	private final PlanDAO dao;
	
	public PlanService(PlanDAO dao) {
		this.dao = dao;
	}
	
	public List<PlanDTO> getPlanList() {
		return dao.findAll();
	}
}
