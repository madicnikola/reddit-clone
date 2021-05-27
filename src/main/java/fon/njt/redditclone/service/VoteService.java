package fon.njt.redditclone.service;

import fon.njt.redditclone.dto.PostResponse;
import fon.njt.redditclone.dto.VoteDto;
import fon.njt.redditclone.exceptions.PostNotFoundException;
import fon.njt.redditclone.exceptions.SpringRedditException;
import fon.njt.redditclone.model.Post;
import fon.njt.redditclone.model.Vote;
import fon.njt.redditclone.model.VoteType;
import fon.njt.redditclone.repository.PostRepository;
import fon.njt.redditclone.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static fon.njt.redditclone.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + voteDto.getPostId()));

        Optional<Vote> voteByPostAndUser = voteRepository
                .findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());

        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType()
                .equals(voteDto.getVoteType())) {
            throw new SpringRedditException("You have already " + voteDto.getVoteType() + "'d for this post");
        }
        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);

    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
