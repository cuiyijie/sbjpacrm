package com.cyj.sbjpacrm.service;

import com.cyj.sbjpacrm.entity.UsersDB;
import com.cyj.sbjpacrm.entitySerch.Userserch;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;

public interface UserService {
    /***
     * 根据用户名查询用户
     * @param loginName
     * @return
     */
    UsersDB findByLoginName(String loginName);

    /***
     * 多条件分页查询
     * @param userserch
     * @return
     */
    Page<UsersDB> queryByDynamicSQLPageUser(Userserch userserch);

    /***
     * 修改用户
     * @param usersDB
     */
    @Transactional
    @Modifying
    UsersDB updateUser(UsersDB usersDB);

    /***
     * 根据id删除用户
     * @param usersDB
     */
    @Transactional
    @Modifying
     void deleteUser  (UsersDB usersDB);

    /***
     * 重置密码
     * @param usersDB
     * @return
     */
    UsersDB updateCzPwd(UsersDB usersDB);


}
