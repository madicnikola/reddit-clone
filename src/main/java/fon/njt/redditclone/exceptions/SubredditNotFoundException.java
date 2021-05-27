package fon.njt.redditclone.exceptions;

public class SubredditNotFoundException extends RuntimeException {
    public SubredditNotFoundException(String subredditName) {
        super(subredditName);
    }
}
