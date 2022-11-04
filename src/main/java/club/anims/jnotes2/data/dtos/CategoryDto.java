package club.anims.jnotes2.data.dtos;

import club.anims.jnotes2.data.model.Category;
import club.anims.jnotes2.data.model.Note;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * A DTO for the {@link Category} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CategoryDto implements Serializable {
    private Integer id;
    private String name;
    private Collection<Integer> noteIds;

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.noteIds = category.getNotes() != null ? category.getNotes().stream().map(Note::getId).collect(Collectors.toList()) : new ArrayList<>();
    }
}