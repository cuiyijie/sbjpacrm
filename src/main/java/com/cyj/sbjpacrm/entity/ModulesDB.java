package com.cyj.sbjpacrm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "modules", schema = "sister", catalog = "")
@Data

public class ModulesDB implements Serializable {
    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JsonProperty("text")
    @Column(name = "Name", nullable = false, length = 50)
    private String name;
    @Column(name = "ParentId", nullable = true)
    private Integer parentId;
    @Column(name = "Path", nullable = true, length = 200)
    private String path;
    @Column(name = "Weight", nullable = true)
    private Integer weight;
    @Column(name = "icon_class", nullable = true, length = 20)
    private String iconClass;
    @Column(name = "Ops", nullable = true, length = 100)
    private String ops;
    @Column(name = "Int0", nullable = true)
    private Integer int0;
    @JsonIgnore
    @ManyToMany(mappedBy = "modulesDBS", fetch = FetchType.EAGER)
    private List<RolesDB> rolesDBS;


    @Transient
    private List<ModulesDB> children;
    @Transient
    private boolean checked;





}
