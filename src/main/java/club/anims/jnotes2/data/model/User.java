package club.anims.jnotes2.data.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Users", schema = "main")
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Basic
    @Column(name = "name", nullable = false, length = -1)
    @Size(min = 1, max = 64)
    private String name;
    
    @Basic
    @Column(name = "password", nullable = false, length = -1)
    @Size(min = 4, max = 64)
    private String password;
    
    @Basic
    @Column(name = "token", length = -1)
    private String token;
    
    @Basic
    @Column(name = "token_validation_timeout", length = -1)
    private String tokenValidationTimeout;
    
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Collection<Category> categories;

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getToken() {
        return token;
    }

    public User setToken(String token) {
        this.token = token;
        return this;
    }

    public String getTokenValidationTimeout() {
        return tokenValidationTimeout;
    }

    public User setTokenValidationTimeout(String tokenValidationTimeout) {
        this.tokenValidationTimeout = tokenValidationTimeout;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(password, user.password) && Objects.equals(token, user.token) && Objects.equals(tokenValidationTimeout, user.tokenValidationTimeout);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, token, tokenValidationTimeout);
    }

    public Collection<Category> getCategories() {
        return categories;
    }

    public User setCategories(Collection<Category> categories) {
        this.categories = categories;
        return this;
    }
}
