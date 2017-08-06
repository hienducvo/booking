package com.hotel.booking.dao;

import java.util.List;

import com.hotel.booking.common.SearchCriteria;
import com.hotel.booking.entity.Booking;
import com.hotel.booking.entity.Hotel;

public interface BookingDAO {
	/**
	 * Find bookings made by the given user
	 * 
	 * @param username
	 *            the user's name
	 * @return their bookings
	 */
	public List<Booking> findBookings(String username);

	/**
	 * Find hotels available for booking by some criteria.
	 * 
	 * @param criteria
	 *            the search criteria
	 * @return a list of hotels meeting the criteria
	 */
	public List<Hotel> findHotels(SearchCriteria criteria);

	/**
	 * Find hotels by their identifier.
	 * 
	 * @param id
	 *            the hotel id
	 * @return the hotel
	 */
	public Hotel findHotelById(Long id);

	/**
	 * Create a new, transient hotel booking instance for the given user.
	 * 
	 * @param hotelId
	 *            the hotelId
	 * @param userName
	 *            the user name
	 * @return the new transient booking instance
	 */
	public Booking createBooking(Long hotelId, String userName);

	/**
	 * Persist the booking to the database
	 * 
	 * @param booking
	 *            the booking
	 */
	public void persistBooking(Booking booking);

	/**
	 * Cancel an existing booking.
	 * 
	 * @param id
	 *            the booking id
	 */
	public void cancelBooking(Long id);
}
