package org.example.model;

public class Comment {

    private String comment;

    private String userName;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    @Override
    public String toString() {
        return "Comment{" +
                "comment='" + comment + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
