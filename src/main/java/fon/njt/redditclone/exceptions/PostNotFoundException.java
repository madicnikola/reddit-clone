package fon.njt.redditclone.exceptions;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String messsage) {
        super(messsage);
    }
}
