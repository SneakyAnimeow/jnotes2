package club.anims.jnotes2.data.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Categories", schema = "main")
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Basic
    @Column(name = "name", nullable = false, length = -1)
    @Size(min = 1, max = 64)
    private String name;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
    
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private Collection<Note> notes;

    public Integer getId() {
        return id;
    }

    public Category setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Category setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(name, category.name) && Objects.equals(user.getId(), category.user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, user.getId());
    }

    public User getUser() {
        return user;
    }

    public Category setUser(User user) {
        this.user = user;
        return this;
    }

    public Collection<Note> getNotes() {
        return notes;
    }

    public Category setNotes(Collection<Note> notes) {
        this.notes = notes;
        return this;
    }
}
