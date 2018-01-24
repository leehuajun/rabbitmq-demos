package cn.cvtb.receiver;

import java.io.Serializable;

/**
 * @author: lhj
 * @create: 2017-01-13 18:13
 * @description: 说明
 */
public class User implements Serializable {
    private static final long serialVersionUID = 3125939724600065480L;

    private Integer id;
    private String name;
    private String address;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public User() {
    }

    public User(Integer id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
