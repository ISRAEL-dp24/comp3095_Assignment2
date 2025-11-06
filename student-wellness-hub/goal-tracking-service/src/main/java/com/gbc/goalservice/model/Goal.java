package com.gbc.goalservice.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
@Document("goals")
public class Goal {
    @Id private String goalId;
    private String title; private String description; private LocalDate targetDate; private String status; private String category;
    public Goal() {}
    public String getGoalId(){return goalId;} public void setGoalId(String id){this.goalId=id;}
    public String getTitle(){return title;} public void setTitle(String t){this.title=t;}
    public String getDescription(){return description;} public void setDescription(String d){this.description=d;}
    public LocalDate getTargetDate(){return targetDate;} public void setTargetDate(LocalDate d){this.targetDate=d;}
    public String getStatus(){return status;} public void setStatus(String s){this.status=s;}
    public String getCategory(){return category;} public void setCategory(String c){this.category=c;}
}
