package vttp.batch5.ssf.noticeboard.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class notice {

    @NotNull(message="Title cannot be empty")
    @Size(min=3, max=128, message="Title must be between 3 and 128 characters")
    public String title;

    @NotNull(message="Email cannot be empty")
    @Email(message="Email must be a valid email format")
    public String poster;

    @NotNull(message="Post date cannot be empty")
    public String postDate;

    @NotNull(message="Please choose at least 1 category")
    public String[] categories;

    @NotNull(message="The contents of the notice cannot be empty")
    public String text;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public @NotNull(message = "Please choose at least 1 category") String[] getCategories() {
        return categories;
    }

    public void setCategories(@NotNull(message = "Please choose at least 1 category") String[] categories) {
        this.categories = categories;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "notice [title=" + title + ", poster=" + poster + ", postDate=" + postDate + ", categories=" + categories
                + ", text=" + text + "]";
    }



    
}
