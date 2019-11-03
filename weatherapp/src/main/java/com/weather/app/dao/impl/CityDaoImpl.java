package com.weather.app.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.weather.app.dao.CityDao;
import com.weather.app.entity.City;

/**
 * The Class CityDaoImpl.
 */
@Repository
public class CityDaoImpl implements CityDao {

	/** The session factory. */
	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * Sets the session factory.
	 *
	 * @param sf the new session factory
	 */
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	/* (non-Javadoc)
	 * @see com.weather.app.dao.CityDao#addCity(com.weather.app.entity.City)
	 */
	@Override
	@Transactional
	public City addCity(City city) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(city);
		return city;
	}

	/* (non-Javadoc)
	 * @see com.weather.app.dao.CityDao#getCityByCityId(java.lang.Integer)
	 */
	@Override
	public City getCityByCityId(Integer cityId) {
		Session session = this.sessionFactory.getCurrentSession();
		City p = (City) session.get(City.class, cityId);
		return p;
	}

	/* (non-Javadoc)
	 * @see com.weather.app.dao.CityDao#removeCity(java.lang.Integer)
	 */
	@Override
	@Transactional
	public void removeCity(Integer cityId) {
		Session session = this.sessionFactory.getCurrentSession();
		City p = (City) session.load(City.class, cityId);
		if (null != p) {
			session.delete(p);
		}
	}

	/* (non-Javadoc)
	 * @see com.weather.app.dao.CityDao#getCityByCityName(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public City getCityByCityName(String cityName) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from City where cityName = :cityName");
		query.setParameter("cityName", cityName);
		List<City> cities = query.list();
		if (cities != null && !cities.isEmpty()) {
			return cities.get(0);
		}
		return null;
	}

}
