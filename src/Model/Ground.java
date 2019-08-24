package Model;/*
 * Modelling Complex Software Systems_SWEN90004_2019
 * Assignment 2
 * Student:Dongming Li / Zhuxin Yang / Ruifeng Luo
 * ID:1002971 / 941731 / 686141
 */
/**
 * @program: ModellingAssignment2
 * @description: the ground for all patches which can be empty or have daisies
 * @author: DennyLee
 **/

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

public class Ground {
    //the initial percentage of white daisies
    private double numWhite;
    //the initial percentage of white daisies
    private double numBlack;
    //the albedo of white daisies
    private double albedoWhite;
    //the albedo of black daisies
    private double albedoBlack;
    //the luminosity of the world
    private double luminosity;
    //the albedo of surface
    private double albedoSurface;
    //the global temperature
    private double globalTemperature;
    //the ticks
    private int ticks = 0;
    //the counted number of white daisies
    private int countWhite = 0;
    //the counted number of black daisies
    private int countBlack = 0;
    //the code of the scenario
    private int scenario;
    //the ground array to store patches
    private Patch[][] ground = new Patch[Params.WORLD_SIZE][Params.WORLD_SIZE];

    private Random r = new Random();

    /**
     * Construction method of ground
     *
     * @param numWhite      the initial percentage of white daisies
     * @param numBlack      the initial percentage of white daisies
     * @param albedoWhite   the albedo of white daisies
     * @param albedoBlack   the albedo of black daisies
     * @param luminosity    the luminosity of the world
     * @param albedoSurface the albedo of surface
     * @param scenario      the code of the scenario
     */
    public Ground(int numWhite, int numBlack, double albedoWhite, double albedoBlack, double
            luminosity, double albedoSurface, int scenario) {
        this.numWhite = numWhite * Params.WORLD_SIZE * Params.WORLD_SIZE / 100;
        this.numBlack = numBlack * Params.WORLD_SIZE * Params.WORLD_SIZE / 100;
        this.albedoWhite = albedoWhite;
        this.albedoBlack = albedoBlack;
        this.luminosity = luminosity;
        this.albedoSurface = albedoSurface;
        this.scenario = scenario;

    }

    /**
     * the method to setup the initial stage
     */
    public void setup() {
        String info = numWhite + "," + numBlack + "," + albedoWhite + "," + albedoBlack + "," +
                scenario + "," +
                "" + luminosity + "," + albedoSurface;

        //write the initial parameters and data to csv file
        CSVWriter.InfoWriter("Luminosity", info);
        CSVWriter.InfoWriter("GlobalTemperature", info);
        CSVWriter.InfoWriter("Population", info);
        CSVWriter.DataWriter("Luminosity", "Ticks,Luminosity");
        CSVWriter.DataWriter("GlobalTemperature", "Ticks,GlobalTemperature");
        CSVWriter.DataWriter("Population", "Ticks,Number of White,Number of Black");
        //create new instances of patches
        for (int x = 0; x < Params.WORLD_SIZE; x++) {
            for (int y = 0; y < Params.WORLD_SIZE; y++) {
                Patch patch = new Patch();
                ground[x][y] = patch;
            }
        }
        //seed initial daisies randomly
        seedRandomly();
        //calculate the temperature of each patch
        for (int x = 0; x < Params.WORLD_SIZE; x++) {
            for (int y = 0; y < Params.WORLD_SIZE; y++) {
                ground[x][y].calculateTemp(albedoSurface, luminosity);
            }
        }
        ticks = 0;

        System.out.println("------------------------------------Initial" +
                "------------------------------------");
        System.out.println("Ticks:" + ticks);
        System.out.println("White: " + CountDaisy(Params.WHITE));
        System.out.println("Black: " + CountDaisy(Params.BLACK));
        System.out.println("Global Temperature: " + calculateGlobalTemp());
        System.out.println();
        System.out.println("---------------------------------------GO--" +
                "------------------------------------");
        CSVWriter.DataWriter("Luminosity", (ticks + "," + luminosity));
        CSVWriter.DataWriter("GlobalTemperature", (ticks + "," + calculateGlobalTemp()));
        CSVWriter.DataWriter("Population", (ticks + "," + countWhite + "," + countBlack));

    }

    /**
     * the go method to run the simulator
     */
    public void go() {
        //setup the ground
        setup();
        //run the simulator for finite states
        for (int i = 1; i <= Params.MAX_TICKS; i++) {
            //calculate the temperature of each patch
            for (int x = 0; x < Params.WORLD_SIZE; x++) {
                for (int y = 0; y < Params.WORLD_SIZE; y++) {
                    ground[x][y].calculateTemp(albedoSurface, luminosity);
                }
            }
            //diffuse the temperature
            diffuse(Params.DIFFUSE_RATIO);
            //check the survivability of daisies on each patch
            checkSurvivability();

            //calculate the global temperature
            globalTemperature = calculateGlobalTemp();
            BigDecimal dGlobalTemperature = new BigDecimal(globalTemperature);
            double global = dGlobalTemperature.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            //count the number of different daisies
            countWhite = CountDaisy(Params.WHITE);
            countBlack = CountDaisy(Params.BLACK);
            //ticks increase
            ticks++;

            if (scenario == 1) {
                if (ticks > 200 && ticks <= 400) {
                    luminosity = new BigDecimal(luminosity + 0.005).setScale(4,
                            BigDecimal.ROUND_HALF_UP).doubleValue();
                } else if (ticks > 600 && ticks <= 850) {
                    luminosity = new BigDecimal(luminosity - 0.0025).setScale(4,
                            BigDecimal.ROUND_HALF_UP).doubleValue();
                }
            }

            //print data to the console
            System.out.println("Ticks:" + ticks);
            System.out.println("Global Temperature: " + globalTemperature);
            System.out.println("White: " + countWhite);
            System.out.println("Black: " + countBlack);
            System.out.println("Luminosity" + luminosity);
            System.out.println();

            //write data to the csv file
            CSVWriter.DataWriter("Luminosity", (ticks + "," + luminosity));
            CSVWriter.DataWriter("GlobalTemperature", (ticks + "," + global));
            CSVWriter.DataWriter("Population", (ticks + "," + countWhite + "," + countBlack));
        }
    }

    /**
     * the method to seed white and black daisies randomly at the initial stage
     */
    public void seedRandomly() {
        //run when the number of white or black daisies is greater than 0
        while (numWhite > 0 || numBlack > 0) {
            //find a random position
            int x = r.nextInt(Params.WORLD_SIZE);
            int y = r.nextInt(Params.WORLD_SIZE);
            //if the number of white daisies is greater than 0
            if (numWhite > 0) {
                // and if the position doesn't have a daisy
                if (!ground[x][y].getStatus()) {
                    //seed
                    seed(ground[x][y], Params.WHITE, r.nextInt(Params.MAX_AGE));
                    numWhite--;
                }
                //if the number of black daisies is greater than 0
            } else if (numBlack > 0) {
                // and if the position doesn't have a daisy
                if (!ground[x][y].getStatus()) {
                    //seed
                    seed(ground[x][y], Params.BLACK, r.nextInt(Params.MAX_AGE));
                    numBlack--;
                }
            }
        }
    }

    /**
     * seed at the designated location
     *
     * @param patch the patch to be seeded
     * @param color the color of the seeded daisy
     * @param age   the age of the seeded daisy
     */
    private void seed(Patch patch, String color, int age) {
        //set the status of patch to true
        patch.setStatus(true);
        //get new daisy instance
        Daisy daisy = new Daisy();
        //seed white daisy
        if (color.equals(Params.WHITE)) {
            daisy.setAlbedo(albedoWhite);
            daisy.setColor(Params.WHITE);
            //seed black daisies
        } else if (color.equals(Params.BLACK)) {
            daisy.setAlbedo(albedoBlack);
            daisy.setColor(Params.BLACK);
        }
        //set age
        daisy.setAge(age);
        patch.setDaisy(daisy);
    }

    /**
     * the method to check if the daisy can survive and seed
     * This parabola has a peak value of 1 -- the maximum growth factor possible at an optimum
     * temperature of 22.5 degrees C and drops to zero at local temperatures of 5 degrees C and
     * 40 degrees C. Thus, growth of new daisies can only occur within this temperature range,
     * with decreasing probability of growth new daisies closer to the x-intercepts of the parabolas
     * this probability calculation is based on the local temperature.
     */
    private void checkSurvivability() {
        //increase age for all daisies
        for (int x = 0; x < Params.WORLD_SIZE; x++) {
            for (int y = 0; y < Params.WORLD_SIZE; y++) {
                if (ground[x][y].getStatus()) {
                    int age = ground[x][y].getDaisy().getAge() + 1;
                    //set age
                    ground[x][y].getDaisy().setAge(age);
                }
            }
        }
        // judge whether a new seed can be seeded
        for (int x = 0; x < Params.WORLD_SIZE; x++) {
            for (int y = 0; y < Params.WORLD_SIZE; y++) {
                double seedThreshold;
                int age;
                double temperature;
                //a array list to store available neighbours
                ArrayList<Patch> neighbour = new ArrayList<>();
                //if there is a daisy on the patch
                if (ground[x][y].getStatus()) {
                    //get the age of the daisy
                    age = ground[x][y].getDaisy().getAge();
                    //get the temperature of each patch to calculate threshold
                    temperature = ground[x][y].getPatchTemp();
                    //if the daisy is still alive
                    if (age < Params.MAX_AGE) {
                        //and if the daisy isn't seeded by other daisies at the same year
                        if (age >= 1) {
                            //calculate the seed threshold
                            seedThreshold = (0.1457 * temperature) - (0.0032 * temperature *
                                    temperature) - 0.6443;
                            //if the seed threshold is greater than a random number between 0 and 1
                            //one of its available neighbours can be seeded
                            if (r.nextFloat() < seedThreshold) {
                                for (int i = x - 1; i <= x + 1; i++) {
                                    for (int j = y - 1; j <= y + 1; j++) {
                                        if (i >= 0 && i < Params.WORLD_SIZE && j >= 0 && j <
                                                Params.WORLD_SIZE && !(i == x && j == y)) {
                                            if (!ground[i][j].getStatus())
                                                neighbour.add(ground[i][j]);

                                        }
                                    }
                                }
                                //if there is available neighbours, choose one randomly and seed
                                if (!neighbour.isEmpty()) {
                                    //seed the same color daisy
                                    String tempColor = ground[x][y].getDaisy().getColor();
                                    Patch tempPatch = neighbour.get(r.nextInt(neighbour.size()));
                                    seed(tempPatch, tempColor, 0);
                                }
                            }
                        }
                    } else {
                        //if the age is greater than the max age, remove it from the ground
                        ground[x][y].die();
                    }
                }
            }
        }
    }


    /**
     * the method to calculate the clobal temperature
     *
     * @return the global temperature
     */
    private double calculateGlobalTemp() {
        double globalTemp = 0;
        for (int x = 0; x < Params.WORLD_SIZE; x++) {
            for (int y = 0; y < Params.WORLD_SIZE; y++) {
                globalTemp += ground[x][y].getPatchTemp();
            }
        }
        globalTemp = globalTemp / (Params.WORLD_SIZE * Params.WORLD_SIZE);
        return globalTemp;
    }

    /**
     * the method to calculate the diffuse temperature
     *
     * @param ratio the ratio of diffuse
     */
    private void diffuse(double ratio) {
        //pre diffuse
        //reset all the value of diffuse temperature
        for (int x = 0; x < Params.WORLD_SIZE; x++) {
            for (int y = 0; y < Params.WORLD_SIZE; y++) {
                ground[x][y].setDiffuseInTemp(0);
            }
        }
        //calculate diffuse
        for (int x = 0; x < Params.WORLD_SIZE; x++) {
            for (int y = 0; y < Params.WORLD_SIZE; y++) {
                double patchTemp = ground[x][y].getPatchTemp();
                double diffuseOutTemp = ratio * patchTemp;
                int validNeighbour = 0;
                for (int i = x - 1; i <= x + 1; i++) {
                    for (int j = y - 1; j <= y + 1; j++) {
                        //for every neighbour of that patch, calculate the diffuse temperature
                        if (i >= 0 && i < Params.WORLD_SIZE && j >= 0 && j < Params.WORLD_SIZE &&
                                !(i == x && j == y)) {
                            //count how many neighbour is available
                            validNeighbour++;
                            ground[i][j].setDiffuseInTemp(ground[i][j].getDiffuseInTemp() +
                                    diffuseOutTemp / 8);
                        }
                    }
                }
                //reduce the heat diffused out from the patch
                ground[x][y].setPatchTemp(patchTemp - diffuseOutTemp / 8 * validNeighbour);
            }
        }
        //post diffuse
        for (int x = 0; x < Params.WORLD_SIZE; x++) {
            for (int y = 0; y < Params.WORLD_SIZE; y++) {
                ground[x][y].setPatchTemp(ground[x][y].getDiffuseInTemp() + ground[x][y]
                        .getPatchTemp());
            }
        }
    }

    /**
     * the method to count daisies
     *
     * @param color the color of daisy to be counted
     * @return the number of daisies
     */
    private int CountDaisy(String color) {
        int calculateDaisy = 0;
        for (int x = 0; x < Params.WORLD_SIZE; x++) {
            for (int y = 0; y < Params.WORLD_SIZE; y++) {
                //for each patch, increase the counter if the color matches parameter
                if (ground[x][y].getStatus()) {
                    if (ground[x][y].getDaisy().getColor().equals(color))
                        calculateDaisy++;
                }
            }
        }
        return calculateDaisy;
    }

}

