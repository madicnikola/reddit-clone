package fon.njt.redditclone.repository;

import fon.njt.redditclone.model.Post;
import fon.njt.redditclone.model.Subreddit;
import fon.njt.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUser(User user);

    List<Post> findBySubreddit(Subreddit subreddit);
}
