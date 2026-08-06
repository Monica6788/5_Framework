package project.model;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/plan")
public class PlanController {
	private final PlanService planService;
	
	public PlanController(PlanService planService) {
		this.planService = planService;
	}
	
	@GetMapping("/list")
	public String planList(Model model) {
//		System.out.println("컨트롤러");
		
		List<PlanDTO> planList = planService.getPlanList();
		
		model.addAttribute("planList", planList);
		
		return "plan/list";
	}

}
