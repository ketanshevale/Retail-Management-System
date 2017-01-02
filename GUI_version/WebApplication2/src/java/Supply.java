
import java.sql.Date;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author shashiupadhyay
 */
public class Supply {
    
    private int SUP;
    private String PID;
    private String SID;
    private Date SDATE;
    private int Quantity;

    /**
     * @return the SUP
     */
    public int getSUP() {
        return SUP;
    }

    /**
     * @param SUP the SUP to set
     */
    public void setSUP(int SUP) {
        this.SUP = SUP;
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
     * @return the SID
     */
    public String getSID() {
        return SID;
    }

    /**
     * @param SID the SID to set
     */
    public void setSID(String SID) {
        this.SID = SID;
    }

    /**
     * @return the SDATE
     */
    public Date getSDATE() {
        return SDATE;
    }

    /**
     * @param SDATE the SDATE to set
     */
    public void setSDATE(Date SDATE) {
        this.SDATE = SDATE;
    }

    /**
     * @return the Quantity
     */
    public int getQuantity() {
        return Quantity;
    }

    /**
     * @param Quantity the Quantity to set
     */
    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }
    
    
}
