package com.fjut.cf.pojo.po;

/**
 * @author axiang [2019/10/18]
 */
public class PermissionTypePO {
    private Integer id;
    private String permissionName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    @Override
    public String toString() {
        return "PermissionTypePO{" +
                "id=" + id +
                ", permissionName='" + permissionName + '\'' +
                '}';
    }
}
