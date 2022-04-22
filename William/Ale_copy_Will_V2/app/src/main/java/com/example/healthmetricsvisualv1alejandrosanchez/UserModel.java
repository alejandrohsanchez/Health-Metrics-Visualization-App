package com.example.healthmetricsvisualv1alejandrosanchez;

public class UserModel {
    private String name;
    private int age;
    private int height1;
    private int height2;
    private double weight;
    private int water;
    private int calories;
    private double workout;

    public UserModel(int id, String name, int age, int height1, int height2, double weight, int water, int calories, double workout) {
        this.name = name;
        this.age = age;
        this.height1 = height1;
        this.height2 = height2;
        this.weight = weight;
        this.water = water;
        this.calories = calories;
        this.workout = workout;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", height1=" + height1 +
                ", height2=" + height2 +
                ", weight=" + weight +
                ", water=" + water +
                ", calories=" + calories +
                ", workout=" + workout +
                '}';
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight1() {
        return height1;
    }
    public void setHeight1(int height1) {
        this.height1 = height1;
    }

    public int getHeight2() {
        return height2;
    }
    public void setHeight2(int height2) {
        this.height2 = height2;
    }

    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getWater() {
        return water;
    }
    public void setWater(int water) {
        this.water = water;
    }

    public int getCalories() {
        return calories;
    }
    public void setCalories(int calories) {
        this.calories = calories;
    }

    public double getWorkout() {
        return workout;
    }
    public void setWorkout(double workout) {
        this.workout = workout;
    }
}
