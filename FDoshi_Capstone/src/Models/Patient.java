/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.time.LocalDate;

/**
 *
 * @author FDoshi
 */
public class Patient {
    private int ptID;
    private String phys;
    private String ptLastName;
    private String ptFirstName;
    private String age;
    private String ptStatus;
    private LocalDate createDate;
    private String medsList;
    private String ptNotes;
   

    public Patient() {
    }

    public Patient(int ptID, String phys, String ptLastName, String ptFirstName, String age, String ptStatus, LocalDate createDate, String medsList, String ptNotes) {
        this.ptID = ptID;
        this.phys = phys;
        this.ptLastName = ptLastName;
        this.ptFirstName = ptFirstName;
        this.age = age;
        this.ptStatus = ptStatus;
        this.createDate = createDate;
        this.medsList = medsList;
        this.ptNotes = ptNotes;
    }    
  
    public int getPtID() {
        return ptID;
    }

    public void setPtID(int ptID) {
        this.ptID = ptID;
    }

    public String getPhysName() {
        return phys;
    }

    public void setPhysName(String userName) {
        this.phys = userName;
    }

    public String getPtLastName() {
        return ptLastName;
    }

    public void setPtLastName(String ptLastName) {
        this.ptLastName = ptLastName;
    }

    public String getPtFirstName() {
        return ptFirstName;
    }

    public void setPtFirstName(String ptFirstName) {
        this.ptFirstName = ptFirstName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPtStatus() {
        return ptStatus;
    }

    public void setPtStatus(String ptStatus) {
        this.ptStatus = ptStatus;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public String getMedsList() {
        return medsList;
    }

    public void setMedsList(String medsList) {
        this.medsList = medsList;
    }

    public String getPtNotes() {
        return ptNotes;
    }

    public void setPtNotes(String ptNotes) {
        this.ptNotes = ptNotes;
    }
    
    
    
    
    
    
}
