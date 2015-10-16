package classes;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.jboss.weld.bean.AbstractBean;

@Entity
@NamedQueries(value = {
@NamedQuery(name = "user.findByUserAndPassword",
            query = "SELECT FROM users u"
            + "WHERE u.USERNAME = :name AND u.PASSWORD = :password")})
@Table(name = "users")

public class Users {
 
    private static final long serialVersionUID = 1L;
    @Transient
    public static final String FIND_BY_EMAIL_SENHA = "user.findByUserAndPassword";

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;    

    @Column
    private String password;
    

    // <editor-fold desc="GET and SET" defaultstate="collapsed">
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
        this.name = name.trim();
    }

    

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password.trim();
    }

    
    // </editor-fold>
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        return (obj instanceof AbstractBean) ? (this.getId() == null ? this == obj : this.getId().equals(((AbstractBean) obj).getId())) : false;
    }

}

