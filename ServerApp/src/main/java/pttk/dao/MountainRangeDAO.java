package pttk.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import pttk.model.MountainRange;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Repository
public class MountainRangeDAO implements IMountainRangeDAO {

    private static final Logger logger_ = LoggerFactory.getLogger(MountainRangeDAO.class);
    private SessionFactory sessionFactory_;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory_ = sf;
    }

    @Override
    public void addMountainRage(MountainRange mountainRange) {
        Session session = this.sessionFactory_.getCurrentSession();
        session.persist(mountainRange);
        logger_.info("MountainRange saved successfully, MountainRange Details="+mountainRange);
    }

    @Override
    public void updateMountainRange(MountainRange p) {
        Session session = this.sessionFactory_.getCurrentSession();
        session.update(p);
        logger_.info("MountainRange updated successfully, MountainRange Details="+p);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MountainRange> listMountainRange() {
        Session session = this.sessionFactory_.getCurrentSession();
        List<MountainRange> mountainRanges = session.createQuery("from MountainRange").list();
        for(MountainRange mountainRange : mountainRanges){
            logger_.info("MountainRange list : " + mountainRange);
        }
        return mountainRanges;
    }

    @Override
    public MountainRange getMountainRangeById(int id) {
        Session session = this.sessionFactory_.getCurrentSession();
        MountainRange mountainRange = session.load(MountainRange.class, id);
        logger_.info("MountainRange loaded successfully, MountainRange details=" + mountainRange);
        return mountainRange;
    }

    @Override
    public void removeMountainRange(int id) {
        Session session = this.sessionFactory_.getCurrentSession();
        MountainRange p = session.load(MountainRange.class, id);
        if(null != p){
            session.delete(p);
        }
        logger_.info("MountainRange deleted successfully, MountainRange details="+p);
    }
}
