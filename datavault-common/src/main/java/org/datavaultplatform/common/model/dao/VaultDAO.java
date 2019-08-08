package org.datavaultplatform.common.model.dao;

import java.util.List;

import org.datavaultplatform.common.model.Vault;
 
public interface VaultDAO {

    public void save(Vault vault);
    
    public void update(Vault vault);

    public List<Vault> list();

    public List<Vault> list(String sort, String order, String offset, String maxResult);

    public Vault findById(String Id);

    public List<Vault> search(String nquery, String sort, String order, String offset, String maxResult);

    public int count();

    public int getRetentionPolicyCount(int status);

	public Long getTotalNumberOfVaults();

	public Long getTotalNumberOfVaults(String query);
}
