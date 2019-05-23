package org.datavaultplatform.common.model.dao;

import java.util.List;
import org.datavaultplatform.common.model.Deposit;

public interface DepositDAO {

    public void save(Deposit deposit);
    
    public void update(Deposit deposit);
    
    public void delete(Deposit deposit);
    
    public List<Deposit> list(String sort);

    public Deposit findById(String Id);

    public int count();

    public int queueCount();

    public int inProgressCount();

    public List<Deposit> inProgress();

    public List<Deposit> completed();

    public List<Deposit> search(String query, String sort);

    public Long size();
}
