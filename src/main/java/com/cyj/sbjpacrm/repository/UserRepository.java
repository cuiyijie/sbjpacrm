package com.cyj.sbjpacrm.repository;

import com.cyj.sbjpacrm.entity.UsersDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface UserRepository extends JpaRepository<UsersDB, Integer> , JpaSpecificationExecutor<UsersDB> {
    /***
     * 根据登录名获取用户
     * @param loginName
     * @return
     */
    UsersDB findByLoginName(String loginName);


}
