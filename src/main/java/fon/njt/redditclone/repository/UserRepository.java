package fon.njt.redditclone.repository;

import fon.njt.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
