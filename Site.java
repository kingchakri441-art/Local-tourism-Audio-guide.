package com.tourism.guide.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "sites")
public class Site {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank @Column(nullable=false) private String name;
    @NotBlank @Column(nullable=false) private String state;
    @NotBlank @Column(nullable=false) private String category;
    @NotNull @Column(nullable=false) private Double lat;
    @NotNull @Column(nullable=false) private Double lon;
    @Column(columnDefinition="TEXT") private String description;
    @Column(columnDefinition="TEXT") private String history;
    private String food;
    @Column(columnDefinition="TEXT") private String hidden;
    private String audio;

    public Site() {}
    public Site(String name,String state,String category,double lat,double lon,String description,String history,String food,String hidden,String audio){
        this.name=name;this.state=state;this.category=category;this.lat=lat;this.lon=lon;this.description=description;this.history=history;this.food=food;this.hidden=hidden;this.audio=audio;
    }
    public Long getId(){return id;} public String getName(){return name;} public String getState(){return state;} public String getCategory(){return category;}
    public Double getLat(){return lat;} public Double getLon(){return lon;} public String getDescription(){return description;} public String getHistory(){return history;}
    public String getFood(){return food;} public String getHidden(){return hidden;} public String getAudio(){return audio;}
    public void setId(Long id){this.id=id;} public void setName(String v){name=v;} public void setState(String v){state=v;} public void setCategory(String v){category=v;}
    public void setLat(Double v){lat=v;} public void setLon(Double v){lon=v;} public void setDescription(String v){description=v;} public void setHistory(String v){history=v;}
    public void setFood(String v){food=v;} public void setHidden(String v){hidden=v;} public void setAudio(String v){audio=v;}
}
