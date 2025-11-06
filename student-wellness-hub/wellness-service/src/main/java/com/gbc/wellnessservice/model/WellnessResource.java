package com.gbc.wellnessservice.model;
import jakarta.persistence.*;
@Entity
@Table(name="wellness_resources")
public class WellnessResource {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resourceId;
    private String title;
    @Column(length=2000)
    private String description;
    private String category;
    private String url;
    public WellnessResource() {}
    public WellnessResource(String title, String description, String category, String url) { this.title=title; this.description=description; this.category=category; this.url=url; }
    public Long getResourceId(){return resourceId;} public void setResourceId(Long id){this.resourceId=id;}
    public String getTitle(){return title;} public void setTitle(String t){this.title=t;}
    public String getDescription(){return description;} public void setDescription(String d){this.description=d;}
    public String getCategory(){return category;} public void setCategory(String c){this.category=c;}
    public String getUrl(){return url;} public void setUrl(String u){this.url=u;}
}
