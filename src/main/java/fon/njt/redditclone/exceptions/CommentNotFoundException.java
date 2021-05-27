package fon.njt.redditclone.exceptions;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(String postId) {
        super("Comments not found for post: " + postId);
    }
}
