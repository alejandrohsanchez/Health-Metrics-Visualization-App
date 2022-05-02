package com.example.healthmetricsvisualv1alejandrosanchez;

public class UserModel {
    private String name;
    private int age;
    private int height1;
    private int height2;
    private double weight;
    private double goalWeight;
    private int currentWater;
    private int goalWater;
    private double currentCalorie;
    private double goalCalorie;
    private int currentWorkout;
    private int goalWorkout;

    public UserModel(int id, String name, int age, int height1, int height2, double weight, double goalWeight, int currentWater, int goalWater, double currentCalorie, double goalCalorie, int currentWorkout, int goalWorkout) {
        this.name = name;
        this.age = age;
        this.height1 = height1;
        this.height2 = height2;
        this.weight = weight;
        this.currentWater = currentWater;
        this.goalWater = goalWater;
        this.currentCalorie = currentCalorie;
        this.goalCalorie = goalCalorie;
        this.currentWorkout = currentWorkout;
        this.goalWorkout = goalWorkout;
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
                ", goalWeight=" + goalWeight +
                ", currentWater=" + currentWater +
                ", goalWater=" + goalWater +
                ", currentCalorie=" + currentCalorie +
                ", goalCalorie=" + goalCalorie +
                ", currentWorkout=" + currentWorkout +
                ", goalWorkout=" + goalWorkout +
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

    public double getGoalWeight() {
        return goalWeight;
    }

    public void setGoalWeight(double goalWeight) {
        this.goalWeight = goalWeight;
    }

    public int getCurrentWater() {
        return currentWater;
    }

    public void setCurrentWater(int currentWater) {
        this.currentWater = currentWater;
    }

    public int getGoalWater() {
        return goalWater;
    }

    public void setGoalWater(int goalWater) {
        this.goalWater = goalWater;
    }

    public double getCurrentCalorie() {
        return currentCalorie;
    }

    public void setCurrentCalorie(double currentCalorie) {
        this.currentCalorie = currentCalorie;
    }

    public double getGoalCalorie() {
        return goalCalorie;
    }

    public void setGoalCalorie(double goalCalorie) {
        this.goalCalorie = goalCalorie;
    }

    public int getCurrentWorkout() {
        return currentWorkout;
    }

    public void setCurrentWorkout(int currentWorkout) {
        this.currentWorkout = currentWorkout;
    }

    public int getGoalWorkout() {
        return goalWorkout;
    }

    public void setGoalWorkout(int goalWorkout) {
        this.goalWorkout = goalWorkout;
    }
}
