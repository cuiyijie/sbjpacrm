package com.cyj.sbjpacrm.repository;

import com.cyj.sbjpacrm.entity.ModulesDB;
import com.cyj.sbjpacrm.entity.RolesDB;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface RolesRepository extends JpaRepository<RolesDB,Integer> {
   /***
    * 根据角色名字查询角色
    * @param name
    * @return
    */
   RolesDB findByName(String name);

   /***
    * 查询多个角色
    * @param names
    * @return
    */
   List<RolesDB> findByNameIn(String... names);

   /***
    * 角色根据名称查询分页显示
    * @param name
    * @param pageable
    * @return
    */
   Page<RolesDB> findByNameLike(String name, Pageable pageable);

   /***
    * 通过id查询
    * @param id
    * @return
    */
   RolesDB findById(String id);
}
