package com.example.healthmetricsvisualv1alejandrosanchez;

public class UserModel {
    private String name;
    private int age;
    private int height1;
    private int height2;
    private double weight;
    private int currentWater;
    private int dailyWater;
    private int currentCalorie;
    private int dailyCalorie;

    public UserModel(int id, String name, int age, int height1, int height2, double weight, int currentWater, int dailyWater, int currentCalorie, int dailyCalorie) {
        this.name = name;
        this.age = age;
        this.height1 = height1;
        this.height2 = height2;
        this.weight = weight;
        this.currentWater = currentWater;
        this.dailyWater = dailyWater;
        this.currentCalorie = currentCalorie;
        this.dailyCalorie = dailyCalorie;
    }

    public UserModel(String userName, int userAge, int userHeight1, int userHeight2, double userWeight, int userCurrentWater, int userDailyWater, int userCurrentCalorie, int userDailyCalorie) {
    }

    @Override
    public String toString() {
        return "UserModel{" +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", height1=" + height1 +
                ", height2=" + height2 +
                ", weight=" + weight +
                ", currentWater=" + currentWater +
                ", dailyWater=" + dailyWater +
                ", currentCalorie=" + currentCalorie +
                ", dailyCalorie=" + dailyCalorie +
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

    public int getCurrentWater() {
        return currentWater;
    }

    public void setCurrentWater(int currentWater) {
        this.currentWater = currentWater;
    }

    public int getDailyWater() {
        return dailyWater;
    }

    public void setDailyWater(int dailyWater) {
        this.dailyWater = dailyWater;
    }

    public int getCurrentCalorie() {
        return currentCalorie;
    }

    public void setCurrentCalorie(int currentCalorie) {
        this.currentCalorie = currentCalorie;
    }

    public int getDailyCalorie() {
        return dailyCalorie;
    }

    public void setDailyCalorie(int dailyCalorie) {
        this.dailyCalorie = dailyCalorie;
    }
}
