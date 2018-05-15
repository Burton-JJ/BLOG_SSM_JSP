package tech.acodesigner.dao;

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
public interface UserDao {

    public List<UserDto> getUsers();

    public UserDto getUserByUserId(int userId);

    public UserDto getUserByUser(User user);

    public int saveUser(User user);

    public int updateUser(User user);

    public int deleteUser(int userId);

}
