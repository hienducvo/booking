package com.hotel.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.booking.common.SearchCriteria;
import com.hotel.booking.dao.BookingDAO;
import com.hotel.booking.entity.Booking;
import com.hotel.booking.entity.Hotel;

@Service("bookingService")
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingDAO bookingDAO;
	
	@Override
	public List<Booking> findBookings(String username) {
		
		return bookingDAO.findBookings(username);
	}

	@Override
	public List<Hotel> findHotels(SearchCriteria criteria) {
		
		return bookingDAO.findHotels(criteria);
	}

	@Override
	public Hotel findHotelById(Long id) {
	
		return bookingDAO.findHotelById(id);
	}

	@Override
	public Booking createBooking(Long hotelId, String userName) {
		
		return bookingDAO.createBooking(hotelId, userName);
	}

	@Override
	public void persistBooking(Booking booking) {
		
		bookingDAO.persistBooking(booking);
	}

	@Override
	public void cancelBooking(Long id) {

		bookingDAO.cancelBooking(id);
	}

}
