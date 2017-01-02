/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author shashiupadhyay
 */
public class Product {
    private String PID;
    private String PNAME;
    private int QOH;
    private int QOH_THRESHOLD;
    private double ORIGINAL_PRICE;
    private double DISCNT_RATE;

    /**
     * @return the PID
     */
    public String getPID() {
        return PID;
    }

    /**
     * @param PID the PID to set
     */
    public void setPID(String PID) {
        this.PID = PID;
    }

    /**
     * @return the PNAME
     */
    public String getPNAME() {
        return PNAME;
    }

    /**
     * @param PNAME the PNAME to set
     */
    public void setPNAME(String PNAME) {
        this.PNAME = PNAME;
    }

    /**
     * @return the QOH
     */
    public int getQOH() {
        return QOH;
    }

    /**
     * @param QOH the QOH to set
     */
    public void setQOH(int QOH) {
        this.QOH = QOH;
    }

    /**
     * @return the QOH_THRESHOLD
     */
    public int getQOH_THRESHOLD() {
        return QOH_THRESHOLD;
    }

    /**
     * @param QOH_THRESHOLD the QOH_THRESHOLD to set
     */
    public void setQOH_THRESHOLD(int QOH_THRESHOLD) {
        this.QOH_THRESHOLD = QOH_THRESHOLD;
    }

    /**
     * @return the ORIGINAL_PRICE
     */
    public double getORIGINAL_PRICE() {
        return ORIGINAL_PRICE;
    }

    /**
     * @param ORIGINAL_PRICE the ORIGINAL_PRICE to set
     */
    public void setORIGINAL_PRICE(double ORIGINAL_PRICE) {
        this.ORIGINAL_PRICE = ORIGINAL_PRICE;
    }

    /**
     * @return the DISCNT_RATE
     */
    public double getDISCNT_RATE() {
        return DISCNT_RATE;
    }

    /**
     * @param DISCNT_RATE the DISCNT_RATE to set
     */
    public void setDISCNT_RATE(double DISCNT_RATE) {
        this.DISCNT_RATE = DISCNT_RATE;
    }
    
    
}
