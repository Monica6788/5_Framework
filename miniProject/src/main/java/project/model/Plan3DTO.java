package project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor 
@AllArgsConstructor
@Data
public class Plan3DTO extends PlanDTO {
	private String planTime;

	public Plan3DTO(String planTitle, String planDate, String planTime) {
		super(planTitle, planDate);
		this.planTime = planTime;
	}
}
