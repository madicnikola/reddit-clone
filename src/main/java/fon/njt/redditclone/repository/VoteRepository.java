package fon.njt.redditclone.repository;

import fon.njt.redditclone.model.Post;
import fon.njt.redditclone.model.User;
import fon.njt.redditclone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
