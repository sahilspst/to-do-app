package model;

import java.sql.Date;

public class Task {
    private String title;
    private Date targetDate;
    private String status;

    public Task(String title, Date targetDate, String status) {
        this.title = title;
        this.targetDate = targetDate;
        this.status = status;
    }
    public String getTitle() {
        return title;
    }
    public Date getTargetDate() {
        return targetDate;
    }
    public String getStatus() {
        return status;
    }
}