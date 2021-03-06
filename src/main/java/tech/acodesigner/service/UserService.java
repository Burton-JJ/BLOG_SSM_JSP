package tech.acodesigner.service;

import tech.acodesigner.dto.OperationResult;
import tech.acodesigner.dto.UserDto;
import tech.acodesigner.entity.User;

import java.util.List;

/**
 *
 * @description
 * @author Burton
 * @date 2018/5/11 15:14
 * @param
 * @return
 *
 */
public interface UserService {

    public OperationResult<UserDto> checkUser(User user);

    public OperationResult registerUser(User user);

    public List<UserDto> getUsers();

    public OperationResult getUser(int userId);

    public OperationResult updateUser(User user);

    public OperationResult deleteUser(int userId);

}
