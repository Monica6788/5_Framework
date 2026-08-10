package project.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import project.model.Plan2DTO;
import project.model.Plan3DTO;
import project.model.PlanDTO;
import project.model.mapper.PlanMapper;

@RequestMapping("/plan")
@Controller
@RequiredArgsConstructor
public class PlanController {
	private final PlanMapper mapper;
	
	@GetMapping("/list")
	public String planList(Model model) {		
		List<PlanDTO> planList = mapper.findAll();
		model.addAttribute("planList", planList);
		
		return "plan/list";
	}
	
	@GetMapping("/insert")
	public String insert() {
		return "plan/insert";
	}
	
	@GetMapping("/insertForm")
	public String insertForm(@RequestParam("planType") int planType) {
		String page ="";
		switch(planType) {
		case 1 :
			page = "plan/insert1";
			break;
		case 2 :
			page = "plan/insert2";
			break;
		case 3 :
			page = "plan/insert3";
			break;
		}
		//
		
		return page;
	}

	
	@PostMapping("/insert/1")
	public String insertPlan1(@ModelAttribute PlanDTO plan) {
		int result = mapper.insert1(plan);

		if (result > 0) { System.out.println(result + "행 추가됨."); }
		
		return "redirect:/plan/list";
	}
	
	@PostMapping("/insert/2")
	public String insertPlan2(@ModelAttribute Plan2DTO plan) {
		int result = mapper.insert2(plan);

		if (result > 0) { System.out.println(result + "행 추가됨."); }
		
		return "redirect:/plan/list";
	}
	
	@PostMapping("/insert/3")
	public String insertPlan3(@ModelAttribute Plan3DTO plan) {
		int result = mapper.insert3(plan);
		
		if (result > 0) { System.out.println(result + "행 추가됨."); }
		
		return "redirect:/plan/list";
	}
	
	
	
	@GetMapping("/delete")
	public String deleteById(int planId) {
		int result = mapper.deleteById(planId);
		
		if (result > 0) { System.out.println(result + "행 삭제됨."); }
		
		return "redirect:/plan/list";
	}
	
	@GetMapping("/update")
	public String update(int planId, Model model) {
		PlanDTO plan = mapper.findById(planId);
		model.addAttribute("p", plan);
		
		String page = "";
		int planType = mapper.findById(planId).getPlanType();
		
		switch(planType) {
		case 1 :
			page = "plan/update1";
			break;
		case 2 :
			page = "plan/update2";
			break;
		case 3 :
			page = "plan/update3";
			break;
		}
		
		return page;
	} //
	
	@PostMapping("/update/1")
	public String updatePlan1(@ModelAttribute PlanDTO plan) {
		int result = mapper.update1(plan);

		if (result > 0) { System.out.println(result + "행 수정됨."); }
		
		return "redirect:/plan/list";
	}
	
	@PostMapping("/update/2")
	public String updatePlan2(@ModelAttribute Plan2DTO plan) {
		int result = mapper.update2(plan);

		if (result > 0) { System.out.println(result + "행 수정됨."); }
		
		return "redirect:/plan/list";
	}
	
	@PostMapping("/update/3")
	public String updatePlan3(@ModelAttribute Plan3DTO plan) {
		int result = mapper.update3(plan);

		if (result > 0) { System.out.println(result + "행 수정됨."); }
		
		return "redirect:/plan/list";
	}
	
}
