package project.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import project.util.DBUtil;

@Repository
public class PlanDAO {
	
	public List<PlanDTO> findAll() {
		List<PlanDTO> planList = new ArrayList<>();
		String sql = "SELECT PLAN_ID, PLAN_TYPE, PLAN_TITLE, PLAN_DATE, "
				+ "PLAN_DUE, PLAN_TIME FROM PLAN ORDER BY PLAN_ID";
		
		try (Connection conn = DBUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				if (rs.getInt("PLAN_TYPE") == 1) {
					PlanDTO p = new PlanDTO(
							rs.getString("plan_title"),
							rs.getString("PLAN_DATE"));
					p.setPlanType(1);
					planList.add(p);
				} else if (rs.getInt("PLAN_TYPE") == 2) {
					Plan2DTO p = new Plan2DTO(
							rs.getString("plan_title"),
							rs.getString("PLAN_DATE"), rs.getString("PLAN_DUE"));
					p.setPlanType(2);
					planList.add(p);
				} else if (rs.getInt("PLAN_TYPE") == 3) {
					Plan3DTO p = new Plan3DTO(
							rs.getString("plan_title"),
							rs.getString("PLAN_DATE"),
							rs.getString("PLAN_TIME"));
					p.setPlanType(3);
					planList.add(p);
				}
			}	
			if (planList.isEmpty()) {
				System.out.println("목록이 없습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return planList;
	}
}
