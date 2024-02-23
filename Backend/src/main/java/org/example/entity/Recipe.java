package org.example.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "recipe", schema = "adt")
public class Recipe implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "time")
    private Integer time;

    @Column(name = "noOfSteps")
    private Integer noOfSteps;

    @Column(name = "recipeProcedure", length = 10485760)
    private String recipeProcedure;

    @Column(name = "description", length = 10485760)
    private String description;

    @Column(name = "ingredients", length = 2000)
    private String ingredients;

    @Column(name = "noOfIngredients")
    private String noOfIngredients;
    @Column(name = "approved")
    private boolean approved;

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getNoOfSteps() {
        return noOfSteps;
    }

    public void setNoOfSteps(Integer noOfSteps) {
        this.noOfSteps = noOfSteps;
    }

    public String getRecipeProcedure() {
        return recipeProcedure;
    }

    public void setRecipeProcedure(String recipeProcedure) {
        this.recipeProcedure = recipeProcedure;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNoOfIngredients() {
        return noOfIngredients;
    }

    public void setNoOfIngredients(String noOfIngredients) {
        this.noOfIngredients = noOfIngredients;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", noOfSteps=" + noOfSteps +
                ", recipeProcedure=" + recipeProcedure +
                ", description='" + description + '\'' +
                ", ingredients=" + ingredients +
                ", noOfIngredients='" + noOfIngredients + '\'' +
                '}';
    }


}
