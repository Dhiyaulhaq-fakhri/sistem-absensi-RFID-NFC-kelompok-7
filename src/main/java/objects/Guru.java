/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objects;

import java.time.LocalDateTime;

/**
 *
 * @author Lenovo
 */
public class Guru {
    
    private String fullname;
    private String username;
    private String password;
    private LocalDateTime lastlogin;
    
    public Guru() {
    }

    public Guru(String fullname, String username, String password, LocalDateTime lastlogin) {
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.lastlogin = lastlogin;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(LocalDateTime lastlogin) {
        this.lastlogin = lastlogin;
    }

    
    
}
