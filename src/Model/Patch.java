package Model;/*
 * Modelling Complex Software Systems_SWEN90004_2019
 * Assignment 2
 * Student:Dongming Li / Zhuxin Yang / Ruifeng Luo
 * ID:1002971 / 941731 / 686141
 */
/**
 * @program: ModellingAssignment2
 * @description: Patches of the ground
 * @author: DennyLee
 **/

public class Patch {
    //the temperature of each patch
    private double patchTemp = 0;
    //status of the patch, true for existing a daisy
    private boolean status = false;
    //the daisy instance
    private Daisy daisy = null;
    //to save diffuse temperature from neighbour
    private double diffuseInTemp = 0;

    //method to read the temperature of each patch
    public double getPatchTemp() {
        return patchTemp;
    }
    //method to write temperature of each patch
    public void setPatchTemp(double patchTemp) {
        this.patchTemp = patchTemp;
    }
    //method to read the status of each patch
    public boolean getStatus() {
        return status;
    }
    //method to write status
    public void setStatus(boolean status) {
        this.status = status;
    }
    //method to get the daisy instance
    public Daisy getDaisy() {
        return daisy;
    }
    //method to set the daisy instance
    public void setDaisy(Daisy daisy) {
        this.daisy = daisy;
    }
    //method to get the saved diffuse temperature
    public double getDiffuseInTemp() {
        return diffuseInTemp;
    }
    //method to set the diffuse temperature
    public void setDiffuseInTemp(double diffuseInTemp) {
        this.diffuseInTemp = diffuseInTemp;
    }

    /**
     * method to calculate the temperature of each patch
     * @param albedoSurface the albedo of surface
     * @param luminosity the solar luminosity
     */
    public void calculateTemp(double albedoSurface, double luminosity) {
        double absorbedLuminosity = 0;
        double localHeating = 0;

        // if there is no daisy on the patch, the percentage of absorbed energy is calculated (1 -
        // albedo-of-surface) and then multiplied by the solar-luminosity to give a scaled
        // absorbed-luminosity.
        if (!status) {
            absorbedLuminosity = ((1 - albedoSurface) * luminosity);
        } else {
            //else the percentage of absorbed energy is calculated (1 - albedo) and then
            // multiplied by the solar-luminosity to give a scaled absorbed-luminosity.
            absorbedLuminosity = ((1 - daisy.getAlbedo()) * luminosity);
        }

        if (absorbedLuminosity > 0) {
            localHeating = 72 * Math.log(absorbedLuminosity) + 80;
        } else {
            localHeating = 80;
        }

        patchTemp = (patchTemp + localHeating) / 2;
    }

    //the method to remove the daisy from the patch
    public void die() {
        setStatus(false);
        setDaisy(null);
    }

}
