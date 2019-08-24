package Model;/*
 * Modelling Complex Software Systems_SWEN90004_2019
 * Assignment 2
 * Student:Dongming Li / Zhuxin Yang / Ruifeng Luo
 * ID:1002971 / 941731 / 686141
 */
/**
 * @program: ModellingAssignment2
 * @description: The model simulator
 * @author: DennyLee
 **/

import java.util.Scanner;

public class Simulator {
    public static void main(String[] args) {
        //the input percentage of white daisy
        int numWhite;
        //the input percentage of black daisy
        int numBlack;
        //the albedo of white daisy
        double albedoWhite;
        //the albedo of black daisy
        double albedoBlack;
        //the code of scenario
        int scenario = 0;
        //the solar luminosity index
        double luminosity;
        //the albedo of surface
        double albedoSurface;
        //Scanner class to input data
        Scanner scanner = new Scanner(System.in);

        //input the percentage of white daisy
        System.out.println("Please input the proportion of white daisy(0-50)");
        numWhite = scanner.nextInt();
        //input the percentage of black daisy
        System.out.println("Please input the proportion of black daisy(0-50)");
        numBlack = scanner.nextInt();
        //input the albedo of white daisy
        System.out.println("Please input the albedo of white(0-0.99)");
        albedoWhite = scanner.nextDouble();
        //input the percentage of black daisy
        System.out.println("Please input the albedo of black(0-0.99)");
        albedoBlack = scanner.nextDouble();
        //input the code of scenario
        System.out.println("Please choose the scenario: \n 1.ramp-up-ramp-down\n 2.maintain " +
                "current luminosity\n 3.low solar " +
                "luminosity\n 4.our solar luminosity\n 5.high solar luminosity");
        scenario = scanner.nextInt();
        //input the solar luminosity
        System.out.println("please input the solar luminosity(0.001-3)");
        luminosity = scanner.nextDouble();
        //input the albedo of surface
        System.out.println("Please input the albedo of surface(0-1)");
        albedoSurface = scanner.nextDouble();

        //assign luminosity according to scenario
        if (scenario == 1)
            luminosity = 0.8;
        else if (scenario == 3)
            luminosity = 0.6;
        else if (scenario == 4)
            luminosity = 1.0;
        else if (scenario == 5)
            luminosity = 1.4;
        //start the simulator
        System.out.println
                ("-------------------------------------START-------------------------------------");
        Ground ground = new Ground(numWhite, numBlack, albedoWhite, albedoBlack, luminosity,
                albedoSurface, scenario);
        ground.go();
    }
}
