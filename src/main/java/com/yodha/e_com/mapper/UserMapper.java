package com.yodha.e_com.mapper;


import com.yodha.e_com.dto.UsersRequest;
import com.yodha.e_com.dto.UsersResponsedto;
import com.yodha.e_com.entities.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    Users toUsers(UsersRequest dto);

    UsersResponsedto toUsersResDto(Users dto);

}
