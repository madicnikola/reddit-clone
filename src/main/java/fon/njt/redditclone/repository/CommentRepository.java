package fon.njt.redditclone.repository;

import fon.njt.redditclone.model.Comment;
import fon.njt.redditclone.model.Post;
import fon.njt.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findByUser(User user);
}
