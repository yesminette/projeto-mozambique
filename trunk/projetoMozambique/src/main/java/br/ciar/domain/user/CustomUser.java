/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ciar.domain.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUser extends User{

    private String nome;
    private String dn;
    
    public CustomUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, GrantedAuthority[] authorities, String nome){
         super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
         this.nome = nome;
         this.dn = "cn="+nome+",";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }
}
