package mate.academy.internetshop.model;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.sql.rowset.serial.SerialBlob;

import org.apache.log4j.Logger;

public class User {
    private static final Logger logger = Logger.getLogger(User.class);

    private Long id;
    private String name;
    private String login;
    private String password;
    private byte[] salt;
    private String token;
    private Set<Role> roles;

    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
        roles = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public Optional<Blob> getSaltBlob() {
        try {
            Blob blob = new SerialBlob(salt);
            return Optional.of(blob);
        } catch (SQLException e) {
            logger.error("Can't create blob for user!", e);
        }
        return Optional.empty();
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name='" + name + '\'' + ", login='" + login + '\''
                + ", password='" + password + '\'' + ", token='" + token + '\'' + '}';
    }
}
