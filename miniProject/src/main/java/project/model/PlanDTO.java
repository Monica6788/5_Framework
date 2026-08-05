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
	private String planDue;
	private String planTime;
}
