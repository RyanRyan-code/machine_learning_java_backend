package ryan.mlbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ryan.mlbackend.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    public Optional<User> findByEmail(String email);

}
