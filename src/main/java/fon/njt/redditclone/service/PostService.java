package fon.njt.redditclone.service;

import fon.njt.redditclone.dto.PostRequest;
import fon.njt.redditclone.dto.PostResponse;
import fon.njt.redditclone.exceptions.PostNotFoundException;
import fon.njt.redditclone.exceptions.SubredditNotFoundException;
import fon.njt.redditclone.exceptions.UserNotFoundException;
import fon.njt.redditclone.mapper.PostMapper;
import fon.njt.redditclone.model.Post;
import fon.njt.redditclone.model.Subreddit;
import fon.njt.redditclone.model.User;
import fon.njt.redditclone.repository.PostRepository;
import fon.njt.redditclone.repository.SubredditRepository;
import fon.njt.redditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class PostService {
    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
        User currentUser = authService.getCurrentUser();
        postRepository.save(postMapper.map(postRequest, subreddit, currentUser));
    }

    @Transactional
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional
    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepository.findById(subredditId).orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
        List<Post> posts = postRepository.findBySubreddit(subreddit);
        return posts.stream()
                .map(postMapper::mapToDto)
                .collect(toList());

    }

}
