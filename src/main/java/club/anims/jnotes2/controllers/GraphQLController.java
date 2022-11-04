package club.anims.jnotes2.controllers;

import club.anims.jnotes2.data.dtos.CategoryDto;
import club.anims.jnotes2.data.dtos.NoteDto;
import club.anims.jnotes2.data.model.Category;
import club.anims.jnotes2.data.model.Note;
import club.anims.jnotes2.data.repositories.CategoryRepository;
import club.anims.jnotes2.data.repositories.NoteRepository;
import club.anims.jnotes2.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class GraphQLController {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private LoginService loginService;

    @SchemaMapping(typeName = "Query", field = "notes")
    public NoteDto[] getNotes(@Argument int categoryId) {
        var user = loginService.validateTokenAndGetUser();

        var category = categoryRepository.findById(categoryId);

        if (category.isEmpty()) {
            throw new RuntimeException("Category not found");
        }

        if (category.get().getUser().getId().intValue() != user.getId().intValue()) {
            throw new RuntimeException("You don't have access to this category");
        }

        return category.get().getNotes().stream().map(NoteDto::new).toArray(NoteDto[]::new);
    }

    @SchemaMapping(typeName = "Query", field = "categories")
    public CategoryDto[] getCategories() {
        var user = loginService.validateTokenAndGetUser();

        var categories = user.getCategories();
        return categories.stream().map(CategoryDto::new).toArray(CategoryDto[]::new);
    }

    @SchemaMapping(typeName = "Mutation", field = "addCategory")
    public CategoryDto addCategory(@Argument String name) {
        var user = loginService.validateTokenAndGetUser();

        return new CategoryDto(
                categoryRepository.save(new Category().setName(name).setUser(user))
        );
    }

    @SchemaMapping(typeName = "Mutation", field = "addNote")
    public NoteDto addNote(@Argument String name, @Argument String content, @Argument int categoryId) {
        var user = loginService.validateTokenAndGetUser();

        var category = categoryRepository.findById(categoryId);

        if (category.isEmpty()) {
            throw new RuntimeException("Category not found");
        }

        if (!user.getCategories().contains(category.get())) {
            throw new RuntimeException("Category does not belong to user");
        }

        return new NoteDto(
                noteRepository.save(new Note().setName(name).setContent(content).setCategory(category.get()))
        );
    }

    @SchemaMapping(typeName = "Mutation", field = "deleteCategory")
    public CategoryDto deleteCategory(@Argument int id) {
        var user = loginService.validateTokenAndGetUser();

        var category = categoryRepository.findById(id);

        if (category.isEmpty()) {
            throw new RuntimeException("Category not found");
        }

        if (!user.getCategories().contains(category.get())) {
            throw new RuntimeException("Category does not belong to user");
        }

        var categoryDto = new CategoryDto(category.get());
        categoryRepository.delete(category.get());

        return categoryDto;
    }

    @SchemaMapping(typeName = "Mutation", field = "deleteNote")
    public NoteDto deleteNote(@Argument int id) {
        var user = loginService.validateTokenAndGetUser();

        var note = noteRepository.findById(id);

        if (note.isEmpty()) {
            throw new RuntimeException("Note not found");
        }

        if (!user.getCategories().contains(note.get().getCategory())) {
            throw new RuntimeException("Note does not belong to user");
        }

        var noteDto = new NoteDto(note.get());
        noteRepository.delete(note.get());

        return noteDto;
    }
}
