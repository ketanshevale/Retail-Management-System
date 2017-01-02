
import java.sql.Date;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author shashiupadhyay
 */
public class Purchases {
    
    private int PUR;
    private String EID;
    private String PID;
    private String CID;
    private int QTY;
    private Date PTIME;
    private double TOTAL_PRICE;

    /**
     * @return the PUR
     */
    public int getPUR() {
        return PUR;
    }

    /**
     * @param PUR the PUR to set
     */
    public void setPUR(int PUR) {
        this.PUR = PUR;
    }

    /**
     * @return the EID
     */
    public String getEID() {
        return EID;
    }

    /**
     * @param EID the EID to set
     */
    public void setEID(String EID) {
        this.EID = EID;
    }

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
     * @return the CID
     */
    public String getCID() {
        return CID;
    }

    /**
     * @param CID the CID to set
     */
    public void setCID(String CID) {
        this.CID = CID;
    }

    /**
     * @return the QTY
     */
    public int getQTY() {
        return QTY;
    }

    /**
     * @param QTY the QTY to set
     */
    public void setQTY(int QTY) {
        this.QTY = QTY;
    }

    /**
     * @return the PTIME
     */
    public Date getPTIME() {
        return PTIME;
    }

    /**
     * @param PTIME the PTIME to set
     */
    public void setPTIME(Date PTIME) {
        this.PTIME = PTIME;
    }

    /**
     * @return the TOTAL_PRICE
     */
    public double getTOTAL_PRICE() {
        return TOTAL_PRICE;
    }

    /**
     * @param TOTAL_PRICE the TOTAL_PRICE to set
     */
    public void setTOTAL_PRICE(double TOTAL_PRICE) {
        this.TOTAL_PRICE = TOTAL_PRICE;
    }
    
}
