
import java.sql.Date;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author shashiupadhyay
 */
public class Customer {
    
    private String CID;
    private String CNAME;
    private String TELEPHONE;
    private int VISITS_MADE;
    private Date LAST_VISIT_DATE;

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
     * @return the CNAME
     */
    public String getCNAME() {
        return CNAME;
    }

    /**
     * @param CNAME the CNAME to set
     */
    public void setCNAME(String CNAME) {
        this.CNAME = CNAME;
    }

    /**
     * @return the TELEPHONE
     */
    public String getTELEPHONE() {
        return TELEPHONE;
    }

    /**
     * @param TELEPHONE the TELEPHONE to set
     */
    public void setTELEPHONE(String TELEPHONE) {
        this.TELEPHONE = TELEPHONE;
    }

    /**
     * @return the VISITS_MADE
     */
    public int getVISITS_MADE() {
        return VISITS_MADE;
    }

    /**
     * @param VISITS_MADE the VISITS_MADE to set
     */
    public void setVISITS_MADE(int VISITS_MADE) {
        this.VISITS_MADE = VISITS_MADE;
    }

    /**
     * @return the LAST_VISIT_DATE
     */
    public Date getLAST_VISIT_DATE() {
        return LAST_VISIT_DATE;
    }

    /**
     * @param LAST_VISIT_DATE the LAST_VISIT_DATE to set
     */
    public void setLAST_VISIT_DATE(Date LAST_VISIT_DATE) {
        this.LAST_VISIT_DATE = LAST_VISIT_DATE;
    }
    
}
