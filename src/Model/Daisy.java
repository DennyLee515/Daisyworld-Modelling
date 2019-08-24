package Model;/*
 * Modelling Complex Software Systems_SWEN90004_2019
 * Assignment 2
 * Student:Dongming Li / Zhuxin Yang / Ruifeng Luo
 * ID:1002971 / 941731 / 686141
 */
/**
 * @program: ModellingAssignment2
 * @description: the daisy on the patch
 * @author: DennyLee
 **/
public class Daisy {
    //age of the daisy
    private int age;
    //albedo of the daisy
    private double albedo;
    //color of the daisy
    private String color = null;

    //the method to read the age of the daisy
    public int getAge() {
        return age;
    }
    //the method to write the age of the daisy
    public void setAge(int age) {
        this.age = age;
    }
    //the method to read the albedo of the daisy
    public double getAlbedo() {
        return albedo;
    }
    //the method to write the albedo of the daisy
    public void setAlbedo(double albedo) {
        this.albedo = albedo;
    }
    //the method to read the color of the daisy
    public String getColor() {
        return color;
    }
    //the method to write the color of the daisy
    public void setColor(String color) {
        this.color = color;
    }
}
