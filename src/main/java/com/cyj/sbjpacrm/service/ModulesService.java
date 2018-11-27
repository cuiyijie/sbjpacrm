package com.cyj.sbjpacrm.service;

import com.cyj.sbjpacrm.entity.ModulesDB;
import com.cyj.sbjpacrm.entitySerch.ModluesTree;

import java.util.List;

public interface ModulesService {
    /***
     * 获取模块树
     * @return
     */
    List<ModulesDB> getModeulesTree();

    /***
     * 根据角色id获取模块
     * @param roleId
     * @return
     */
    List<ModulesDB> queryRoltesSeModules(String roleId);

    /***
     * 根据角色名设置模块
     * @param rolname
     * @param moduleId
     */
    void rolesSetModules(String rolname,String moduleId);
}
