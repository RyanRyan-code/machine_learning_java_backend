package ryan.mlbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ryan.mlbackend.entity.Session;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Integer> {

    public Optional<Session> findByEmail(String email);

}
