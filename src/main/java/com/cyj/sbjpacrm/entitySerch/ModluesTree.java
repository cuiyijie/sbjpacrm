package com.cyj.sbjpacrm.entitySerch;

import com.cyj.sbjpacrm.entity.ModulesDB;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModluesTree {

    private Integer id;

    private String text;

    private Integer parentId;

    private String path;

    private Integer weight;

    private String iconClass;

    private String ops;

    private Integer int0;

    private List<ModluesTree> modulesDBChildren;
}
