package org.datavaultplatform.broker.services;

import java.util.Date;
import org.datavaultplatform.common.model.Deposit;
import org.datavaultplatform.common.model.Vault;
import org.datavaultplatform.common.model.ArchiveStore;
import org.datavaultplatform.common.model.dao.DepositDAO;

import java.util.UUID;
import java.util.List;

public class DepositsService {

    private DepositDAO depositDAO;
    
    public List<Deposit> getDeposits(String sort) {
        return depositDAO.list(sort);
    }
    
    public void addDeposit(Vault vault,
                           Deposit deposit,
                           String shortPath,
                           String origin,
                           String archiveDeviceID) {
        
        Date d = new Date();
        deposit.setCreationTime(d);
        
        deposit.setVault(vault);
        deposit.setStatus(Deposit.Status.NOT_STARTED);
        
        // Set display values for the deposit path/origin
        deposit.setShortFilePath(shortPath);
        deposit.setFileOrigin(origin);
        
        // Generate a new UUID for this Bag.
        deposit.setBagId(UUID.randomUUID().toString());
        
        // Link the deposit to the archive store
        deposit.setArchiveDevice(archiveDeviceID);
        
        depositDAO.save(deposit);
    }
    
    public void updateDeposit(Deposit deposit) {
        depositDAO.update(deposit);
    }
    
    public Deposit getDeposit(String depositID) {
        return depositDAO.findById(depositID);
    }
    
    public void setDepositDAO(DepositDAO depositDAO) {
        this.depositDAO = depositDAO;
    }

    public int count() { return depositDAO.count(); }

    public int queueCount() { return depositDAO.queueCount(); }

    public List<Deposit> search(String query, String sort) { return this.depositDAO.search(query, sort); }

    public Long size() { return depositDAO.size(); }
}

