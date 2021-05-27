package fon.njt.redditclone.service;

import fon.njt.redditclone.dto.CommentsDto;
import fon.njt.redditclone.exceptions.PostNotFoundException;
import fon.njt.redditclone.exceptions.UserNotFoundException;
import fon.njt.redditclone.mapper.CommentsMapper;
import fon.njt.redditclone.model.Comment;
import fon.njt.redditclone.model.NotificationEmail;
import fon.njt.redditclone.model.Post;
import fon.njt.redditclone.model.User;
import fon.njt.redditclone.repository.CommentRepository;
import fon.njt.redditclone.repository.PostRepository;
import fon.njt.redditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class CommentService {
    private static final String POST_URL = "";

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentsMapper commentsMapper;
    private final CommentRepository commentRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;


    public void save(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post is not available:" + commentsDto.getPostId()));
        Comment comment = commentsMapper.map(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

        String message = mailContentBuilder
                .build(post.getUser().getUsername() + " posted a comment on your post. " + POST_URL);
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + "Commented on your post", user.getEmail(), message));

    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        ////////////////////// SAFE WAY
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId.toString()));
        List<Comment> comments = commentRepository.findByPost(post);
        return comments.stream()
                .map(commentsMapper::mapToDto)
                .collect(toList());
        //////////// RISKY
//        List<Comment> comments = commentRepository.findByPost(postId);
//        return comments.stream()
//                .map(commentsMapper::mapToDto)
//                .collect(toList());

    }

    public List<CommentsDto> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UserNotFoundException(userName));

        List<Comment> comments = commentRepository.findByUser(user);
        return comments.stream()
                .map(commentsMapper::mapToDto)
                .collect(toList());

    }
}
