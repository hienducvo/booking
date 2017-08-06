package com.hotel.booking.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.hotel.booking.common.SearchCriteria;
import com.hotel.booking.entity.Booking;
import com.hotel.booking.entity.Hotel;
import com.hotel.booking.entity.User;

@Repository
public class BookingDAOImpl implements BookingDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Booking> findBookings(String username) {
		if (username != null || !"".equals(username)) {
			return sessionFactory.getCurrentSession()
					.createQuery("select b from Booking b where b.user.username = :username order by b.checkinDate")
					.setParameter("username", username).list();
		}

		return null;
	}
	
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Hotel> findHotels(SearchCriteria criteria) {
		String pattern = getSearchPattern(criteria);
		int startIndex = criteria.getPage() * criteria.getPageSize();

		return sessionFactory.getCurrentSession()
				.createQuery(
						"select h from Hotel h where "
						+ "lower(h.name) like :pattern or "
						+ "lower(h.city) like :pattern  or "
						+ "lower(h.zip) like :pattern or "
						+ "lower(h.address) like :pattern")
				.setParameter("pattern", pattern)
				.setFirstResult(startIndex)
				.setMaxResults(criteria.getPageSize()).list();
	}

	@Transactional
	public Hotel findHotelById(Long id) {
		return sessionFactory.getCurrentSession().get(Hotel.class, id);
	}

	@Transactional(readOnly = true)
	public Booking createBooking(Long hotelId, String userName) {
		Hotel hotel = sessionFactory.getCurrentSession().get(Hotel.class, hotelId);
		User user = findUser(userName);
		Booking booking = new Booking(hotel, user);
		return booking;
	}

	@Transactional(readOnly = true)
	public void persistBooking(Booking booking) {
		sessionFactory.getCurrentSession().persist(booking);

	}

	@Transactional
	public void cancelBooking(Long id) {
		Booking booking = sessionFactory.getCurrentSession().get(Booking.class, id);
		if(booking != null)
		sessionFactory.getCurrentSession().delete(booking);

	}

	private String getSearchPattern(SearchCriteria criteria) {
		if (StringUtils.hasText(criteria.getSearchString())) {
			return "%" + criteria.getSearchString().toLowerCase().replace('*', '%') + "%";
		} else {
			return "%";
		}
	}
	
	private User findUser(String username) {
		return (User) sessionFactory.getCurrentSession().createQuery("select u from User u where u.username = :username")
			.setParameter("username", username).uniqueResult();
	    }

}
