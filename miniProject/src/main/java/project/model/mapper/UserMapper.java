package project.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import project.model.UserDTO;

@Mapper
public interface UserMapper {
	UserDTO findById(String userId);
	
	boolean login(UserDTO user);
}
