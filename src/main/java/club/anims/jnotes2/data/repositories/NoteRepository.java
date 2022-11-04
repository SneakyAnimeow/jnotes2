package club.anims.jnotes2.data.repositories;

import club.anims.jnotes2.data.model.Note;

public interface NoteRepository extends org.springframework.data.jpa.repository.JpaRepository<Note, Integer> {
    Note findByName(String name);
}
