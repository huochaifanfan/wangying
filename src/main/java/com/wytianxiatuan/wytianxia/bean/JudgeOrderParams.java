package com.wytianxiatuan.wytianxia.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/23 0023.
 */

public class JudgeOrderParams {
    private String id;
    private List<Comment> comment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    public static class Comment{
        private String auto_id;
        private String score;
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAuto_id() {
            return auto_id;
        }

        public void setAuto_id(String auto_id) {
            this.auto_id = auto_id;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

    }
}
