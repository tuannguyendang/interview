package com.weather.app.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.weather.app.dao.WeatherDao;
import com.weather.app.entity.Weather;
import com.weather.app.util.ApplicationUtils;

/**
 * The Class WeatherDaoImpl.
 */
@Repository
public class WeatherDaoImpl implements WeatherDao {
	
	/** The session factory. */
	@Autowired
	private SessionFactory sessionFactory;

	/* (non-Javadoc)
	 * @see com.weather.app.dao.WeatherDao#addWeather(com.weather.app.entity.Weather)
	 */
	@Override
	public void addWeather(Weather weather) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(weather);
	}

	/* (non-Javadoc)
	 * @see com.weather.app.dao.WeatherDao#getWeatherByDate(java.util.Date, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Weather getWeatherByDate(Date date, Integer cityId) {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Weather.class);
		criteria.createAlias("weatherCity", "city");
		criteria.add(Restrictions.eq("city.cityId", cityId)).add(Restrictions.eq("weatherDate", ApplicationUtils.getFormattedFromDateTime(date)));
		List<Weather> weatherForecasts = criteria.list();
		if (weatherForecasts != null && !weatherForecasts.isEmpty())
			return weatherForecasts.get(0);
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.weather.app.dao.WeatherDao#getWeatherByDateAndName(java.util.Date, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Weather getWeatherByDateAndName(Date date, String cityName) {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Weather.class);
		criteria.createAlias("weatherCity", "city");
		criteria.add(Restrictions.eq("city.cityName", cityName)).add(Restrictions.eq("weatherDate", ApplicationUtils.getFormattedFromDateTime(date)));
		List<Weather> weatherForecasts = criteria.list();
		if (weatherForecasts != null && !weatherForecasts.isEmpty())
			return weatherForecasts.get(0);
		return null;
	}

	/* (non-Javadoc)
	 * @see com.weather.app.dao.WeatherDao#getWeatherList(java.lang.String, java.util.Date, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Weather> getWeatherList(String city, Date date, int from, int pageSize) {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Weather.class);
		criteria.setFirstResult(from);
		criteria.setMaxResults(pageSize);
		criteria.createAlias("weatherCity", "city");
		return criteria.add(Restrictions.eqOrIsNull("city.cityName", city)).add(Restrictions.ne("weatherDate", ApplicationUtils.getFormattedFromDateTime(date)))
				.addOrder(Order.desc("city.cityName")).addOrder(Order.desc("weatherDate")).list();
	}

}
