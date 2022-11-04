package club.anims.jnotes2.data.dtos;

import club.anims.jnotes2.data.model.Note;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * A DTO for the {@link club.anims.jnotes2.data.model.Note} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class NoteDto implements Serializable {
    private Integer id;
    private String name;
    private String content;
    private Integer categoryId;

    public NoteDto(Note note) {
        this.id = note.getId();
        this.name = note.getName();
        this.content = note.getContent();
        this.categoryId = note.getCategory().getId();
    }
}