package club.anims.jnotes2.data.repositories;

import club.anims.jnotes2.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByName(String name);

    Optional<User> findByToken(String token);
}
