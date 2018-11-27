package com.cyj.sbjpacrm.controller;

import com.cyj.sbjpacrm.entity.ModulesDB;
import com.cyj.sbjpacrm.repository.ModuleRepository;
import com.cyj.sbjpacrm.result.Result;
import com.cyj.sbjpacrm.service.ModulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/modules")
public class ModulesCrotroller {
    @Autowired
    private ModulesService modulesService;
    @Autowired
    private ModuleRepository moduleRepository;

    /***
     * 获取模块树
     * @return
     */
    @RequestMapping("/getModulesTree")
    public Object getModulesTree() {
        List<ModulesDB> list = modulesService.getModeulesTree();
        return list;
    }

    /***
     * 添加父级模块
     * @param modulesDB
     * @return
     */
    @RequestMapping("/addZhuModules")
    public Object addZhuModules(ModulesDB modulesDB) {
        System.out.println("模块==>" + modulesDB);

        try {
            moduleRepository.save(modulesDB);
            return new Result("添加成功", 1);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("添加失败", 0);
        }
    }

    /***
     * 添加模块
     * @param moduleId
     * @return
     */
    @RequestMapping("/deleteModules")
    public Object deleteModules(String moduleId) {

        try {
            String[] modulesid = moduleId.split(",");
            for (String s : modulesid) {
                Integer m = Integer.parseInt(s);
                moduleRepository.deleteById(m);
            }
            return new Result("删除成功", 1);
        } catch (Exception e) {




            e.printStackTrace();
            return new Result("删除失败", 0);
        }

    }

    /***
     * 根据角色id获取模块
     * @param roled
     * @return
     */
    @RequestMapping("/queryRoltesSeModules")
    public List<ModulesDB> queryRoltesSeModules(String roled) {
        return modulesService.queryRoltesSeModules(roled);
    }

    @RequestMapping("/rolesSetModules")
    public Object rolesSetModules(String rolname, String moduleId) {

        try {
            modulesService.rolesSetModules(rolname, moduleId);
            return new Result("添加成功", 1);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("添加失败", 0);
        }
    }
}
