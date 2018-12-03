package com.djorquab.jarvis.technicaltest.mappers;

import com.djorquab.jarvis.technicaltest.dto.UserDTO;
import com.djorquab.jarvis.technicaltest.model.Users;
import org.mapstruct.Mapper;

@Mapper
public interface UsersMapper extends AbstractMapper<Users, UserDTO> {
}
