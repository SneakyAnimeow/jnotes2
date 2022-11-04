package club.anims.jnotes2.data.dtos;

import club.anims.jnotes2.data.model.Category;
import club.anims.jnotes2.data.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * A DTO for the {@link club.anims.jnotes2.data.model.User} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserDto implements Serializable {
    private Integer id;
    private String name;
    private String token;
    private String tokenValidationTimeout;
    private Collection<Integer> categoryIds;

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.token = user.getToken();
        this.tokenValidationTimeout = user.getTokenValidationTimeout();
        this.categoryIds = user.getCategories() != null ? user.getCategories().stream().map(Category::getId).collect(Collectors.toList()) : new ArrayList<>();
    }
}