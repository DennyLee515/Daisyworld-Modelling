package Model;/*
 * Modelling Complex Software Systems_SWEN90004_2019
 * Assignment 2
 * Student:Dongming Li / Zhuxin Yang / Ruifeng Luo
 * ID:1002971 / 941731 / 686141
 */
/**
 * @program: ModellingAssignment2
 * @description: The util class to write data to csv files
 * @author: DennyLee
 **/
import java.io.*;

class CSVWriter {

    /**
     * Init the csv writer with table title and basic info
     * @param fileName the output file name
     * @param info the output information
     */
        static void InfoWriter(String fileName, String info) {
        File file = new File(fileName + ".csv");
        try {
            //use FileWriter, BufferedWriter to output data, generate new csv files
            FileWriter fileWriter = new FileWriter(file, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            //output basic info below
            bufferedWriter.write("scenario: \n 1.ramp-up-ramp-down\n 2.maintain current " +
                    "luminosity \n 3.low solar luminosity \n 4.our solar luminosity\n 5.high " +
                    "solar luminosity");
            bufferedWriter.newLine();
            bufferedWriter.write("start-%-whites" + "," +
                    "start-%-blacks" + "," + "albedo-of-whites" + "," + "albedo-of-blacks" + ","
                    + "scenario" + "," + "solar-luminosity" + "," + "albedo-of-surface"
            );

            bufferedWriter.newLine();
            bufferedWriter.write(info);
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            //flush and close the buffered writer
            bufferedWriter.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * The method to write data to csv files
     * @param fileName the output file name
     * @param data the output data
     */
    static void DataWriter(String fileName, String data) {
        File file = new File(fileName + ".csv");
        try {
            //use FileWriter, BufferedWriter to output data, append to basic info
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(data);
            bufferedWriter.newLine();
            //flush and close the buffered writer
            bufferedWriter.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}



