package project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor 
@AllArgsConstructor
@Data
public class PlanDTO {
	private int planId;
	private int planType;
	private String planTitle;
	private String planDate;
	
	public PlanDTO(String planTitle, String planDate) {
		super();
		this.planTitle = planTitle;
		this.planDate = planDate;
	}
	
}
