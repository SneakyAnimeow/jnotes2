package club.anims.jnotes2.data.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "Notes", schema = "main")
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Basic
    @Column(name = "name", nullable = false, length = -1)
    @Size(min = 1, max = 64)
    private String name;
    
    @Basic
    @Column(name = "content", nullable = false, length = -1)
    @Size(max = 8192)
    private String content;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;

    public Integer getId() {
        return id;
    }

    public Note setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Note setName(String name) {
        this.name = name;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Note setContent(String content) {
        this.content = content;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public Note setCategory(Category category) {
        this.category = category;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(id, note.id) && Objects.equals(name, note.name) && Objects.equals(content, note.content) && Objects.equals(category.getId(), note.category.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, content, category.getId());
    }
}
