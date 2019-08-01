package org.datavaultplatform.common.model.dao;

import org.datavaultplatform.common.model.RoleAssignment;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class RoleAssignmentDaoImpl implements RoleAssignmentDAO {

    private SessionFactory sessionFactory;

    public RoleAssignmentDaoImpl() {}

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean roleAssignmentExists(RoleAssignment roleAssignment) {
        Session session = this.sessionFactory.openSession();
        Criteria criteria = session.createCriteria(RoleAssignment.class);
        criteria.add(Restrictions.eq("role_id", roleAssignment.getRole().getId()));
        criteria.add(Restrictions.eq("user_id", roleAssignment.getUser().getID()));
        if (roleAssignment.getSchool() != null) {
            criteria.add(Restrictions.eq("school_id", roleAssignment.getSchool().getID()));
        }
        if (roleAssignment.getVault() != null) {
            criteria.add(Restrictions.eq("vault_id", roleAssignment.getVault().getID()));
        }
        boolean exists = criteria.uniqueResult() != null;
        session.close();
        return exists;
    }

    @Override
    public void store(RoleAssignment roleAssignment) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(roleAssignment);
        tx.commit();
        session.close();
    }

    @Override
    public RoleAssignment find(Long id) {
        Session session = this.sessionFactory.openSession();
        Criteria criteria = session.createCriteria(RoleAssignment.class);
        criteria.add(Restrictions.eq("id", id));
        RoleAssignment roleAssignment = (RoleAssignment) criteria.uniqueResult();
        session.close();
        return roleAssignment;
    }

    @Override
    public List<RoleAssignment> findAll() {
        Session session = this.sessionFactory.openSession();
        Criteria criteria = session.createCriteria(RoleAssignment.class);
        List<RoleAssignment> roleAssignments = criteria.list();
        session.close();
        return roleAssignments;
    }

    @Override
    public List<RoleAssignment> findBySchoolId(String schoolId) {
        return findBy("school_id", schoolId);
    }

    private List<RoleAssignment> findBy(String columnName, String columnValue) {
        Session session = this.sessionFactory.openSession();
        Criteria criteria = session.createCriteria(RoleAssignment.class);
        criteria.add(Restrictions.eq(columnName, columnValue));
        List<RoleAssignment> roleAssignments = criteria.list();
        session.close();
        return roleAssignments;
    }

    @Override
    public List<RoleAssignment> findByVaultId(String vaultId) {
        return findBy("vault_id", vaultId);
    }

    @Override
    public List<RoleAssignment> findByUserId(String userId) {
        return findBy("user_id", userId);
    }

    @Override
    public void update(RoleAssignment roleAssignment) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.update(roleAssignment);
        tx.commit();
        session.close();
    }

    @Override
    public void delete(Long id) {
        RoleAssignment roleAssignment = find(id);
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(roleAssignment);
        tx.commit();
        session.close();
    }
}
