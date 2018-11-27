package com.cyj.sbjpacrm.repository;

import com.cyj.sbjpacrm.entity.ModulesDB;
import com.cyj.sbjpacrm.entity.RolesDB;
import com.cyj.sbjpacrm.entity.UsersDB;
import com.cyj.sbjpacrm.entitySerch.ModluesTree;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;


public interface ModuleRepository extends JpaRepository<ModulesDB, Integer> {

    List<ModulesDB> findByRolesDBSIn(List<RolesDB> rolesDBS);

    /***
     * 根据父级id获取模块
     * @param id
     * @return
     */
    List<ModulesDB> findByParentId(Integer id);


    void deleteById(String id);
    void deleteByIdIn(String... id);

}
