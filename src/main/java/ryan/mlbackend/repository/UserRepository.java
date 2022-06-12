package ryan.mlbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ryan.mlbackend.entity.UserInfo;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserInfo, Integer> {

    public Optional<UserInfo> findByEmail(String email);

}
