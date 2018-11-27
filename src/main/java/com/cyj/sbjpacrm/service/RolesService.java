package com.cyj.sbjpacrm.service;

import com.cyj.sbjpacrm.entity.RolesDB;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;

public interface RolesService {
    /***
     * 根据角色名查询角色
     * @param name
     * @return
     */
    RolesDB findByName(String name);

    /***
     * 用户所拥有的角色
     * @param loginName
     * @return
     */
    List<RolesDB> queryAllRoleByLoginName(String loginName);

    /***
     * 查询用户没有拥有的角色
     * @param loginName
     * @return
     */
    List<RolesDB> queryAllRole(String loginName);


}
