package com.cyj.sbjpacrm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles", schema = "sister")
@Data
public class RolesDB implements Serializable {
    @Id
    @Column(name = "Id", nullable = false, length = 100)
    private String id;
    @Column(name = "Name", nullable = false, length = 50)
    private String name;
    @Column(name = "Int0", nullable = true)
    private Integer int0;
    @Column(name = "String0", nullable = true, length = 50)
    private String string0;
    @Cascade(value = {org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @ManyToMany(mappedBy = "rolesDBS",fetch = FetchType.LAZY)
    private List<UsersDB> usersDBS;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "rolemodules",joinColumns = @JoinColumn(name = "roleId"),
            inverseJoinColumns = @JoinColumn(name = "moduleId"))
    private List<ModulesDB> modulesDBS;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "rolepermission",joinColumns = @JoinColumn(name = "roleId"),
            inverseJoinColumns = @JoinColumn(name = "permissionId"))
    private List<PermissionDB> permissionDBS;
}
