package org.datavault.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name="Deposits")
public class Deposit {

    // Deposit Identifier
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private String id;
    
    // Serialise date in ISO 8601 format
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;
    
    @JsonIgnore
    @ManyToOne
    private Vault vault;
    
    public enum Status {
        OPEN, CLOSED
    }
    
    private String note;
    private Status status;
    
    // For now, a deposit relates to a single bag.
    private String bagId;
    
    // Record the file path that the user selected for this deposit.
    private String filePath;
    
    public Deposit() {}
    public Deposit(String note) {
        this.note = note;
        this.status = Status.OPEN;
    }
    
    public String getID() { return id; }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getCreationTime() {
        return creationTime;
    }
    
    public Vault getVault() { return vault; }
    
    public void setVault(Vault vault) {
        this.vault = vault;
    }
    
    public String getNote() { return note; }
    
    public void setNote(String note) {
        this.note = note;
    }

    public Status getStatus() { return status; }

    public void setStatus(Status status) {
        this.status = status;
    }
    
    public String getBagId() { return bagId; }
    
    public void setBagId(String bagId) {
        this.bagId = bagId;
    }
    
    public String getFilePath() { return filePath; }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
