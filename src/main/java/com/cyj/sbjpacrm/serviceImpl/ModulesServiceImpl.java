package com.cyj.sbjpacrm.serviceImpl;


import com.cyj.sbjpacrm.entity.ModulesDB;
import com.cyj.sbjpacrm.entity.RolesDB;
import com.cyj.sbjpacrm.entitySerch.ModluesTree;
import com.cyj.sbjpacrm.repository.ModuleRepository;
import com.cyj.sbjpacrm.repository.RolesRepository;
import com.cyj.sbjpacrm.service.ModulesService;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.annotation.AnnotationList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@Service
public class ModulesServiceImpl implements ModulesService {
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private RolesRepository rolesRepository;

    /***
     * 模块树
     * @return
     */
    public List<ModulesDB> getModeulesTree() {

        List<ModulesDB> list = moduleRepository.findByParentId(0);
        this.getModulesChildred(list);
        return list;
    }

    /***
     * 递归模块子节点
     * @param list
     */
    public void getModulesChildred(List<ModulesDB> list) {
        for (ModulesDB modulesDB : list) {
            List<ModulesDB> modulesDBList = moduleRepository.findByParentId(modulesDB.getId());
            if (modulesDBList != null && !modulesDBList.isEmpty()) {

                modulesDB.setChildren(modulesDBList);
                this.getModulesChildred(modulesDBList);
            }
        }

    }

    /***
     * 角色设置模块显示已选中的模块
     * @param roleId
     * @return
     */
    public List<ModulesDB> queryRoltesSeModules(String roleId) {
        System.out.println("角色id==>"+roleId);
        RolesDB rolesDBS = rolesRepository.findByName(roleId);

        rolesDBS.setUsersDBS(null);

        List<ModulesDB> modulesDBS = rolesDBS.getModulesDBS();
        List<Integer> rolemodulesId = new ArrayList<>();
        for (ModulesDB modulesDB : modulesDBS) {
            rolemodulesId.add(modulesDB.getId());
        }
        List<ModulesDB> list = moduleRepository.findByParentId(0);
        setChiledrensChex(list, rolemodulesId);
        return list;
    }

    /***
     * 递归选中
     * @param moduleslist
     * @param rolemodulesId
     */
    public void setChiledrensChex(List<ModulesDB> moduleslist, List<Integer> rolemodulesId) {
        for (ModulesDB modulesDB : moduleslist) {
            List<ModulesDB> modulesDBList = moduleRepository.findByParentId(modulesDB.getId());
            if (rolemodulesId != null && !rolemodulesId.isEmpty()) {
                if (rolemodulesId.contains(modulesDB.getId())) {
                    if (modulesDBList == null || modulesDBList.isEmpty())
                        modulesDB.setChecked(true);

                }
            }

            if (modulesDBList != null && !modulesDBList.isEmpty()) {
                modulesDB.setChildren(modulesDBList);
                this.setChiledrensChex(modulesDBList, rolemodulesId);
            }

        }
    }

    /***
     * 角色设置模块
     * @param rolname
     * @param moduleId
     */

    public void rolesSetModules(String rolname,String moduleId){
        RolesDB rolesDB= rolesRepository.findByName(rolname);
        rolesDB.setModulesDBS(null);
        String[] modulesid = moduleId.split(",");

        List<ModulesDB> modulesDB=new ArrayList<>();
        for (String s : modulesid) {
            int a=Integer.parseInt(s);
            modulesDB .add(moduleRepository.findById(a).orElse(null));

        }
        rolesDB.setModulesDBS(modulesDB);
        rolesRepository.save(rolesDB);
    }


}
