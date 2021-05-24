package fon.njt.redditclone.repository;

import fon.njt.redditclone.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
