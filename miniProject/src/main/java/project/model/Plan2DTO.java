package project.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor 
@Data
public class Plan2DTO extends PlanDTO {
	private String planDue;

	public Plan2DTO(String planTitle, String planDate, String planDue) {
		super(planTitle, planDate);
		this.planDue = planDue;
	}
	
	
}
