import moment from 'moment';
import bookingApi from "../../api/bookingApi";

export default {
    state: {
        bookings: [],
        doctor: 10,
        bookingCalendarDate: moment()
    },
    getters: {
        bookingsToCalendarEvents: (state) => {
            let events = [];
            for (let booking of state.bookings) {

                events.push({
                    id: booking.id,
                    className: 'doctor-booking',
                    title: `${booking.patient.firstName} ${booking.patient.lastName}`, //name of patient
                    start: booking.startDate,
                    end: booking.endDate
                })
            }

            return events;
        },
        bookingById: (state) => (id) => {
            return state.bookings.find(item => item.id == id);
        }
    },

    mutations: {
        setBookings(state, bookings) {
            state.bookings = bookings
        },
        setBookingCalendarDate(state, date) {
            state.bookingCalendarDate = date;
        },
        addBooking(state, booking) {
            state.bookings.push(booking)
        },
        updateBooking(state, booking) {
            state.bookings = state.bookings.filter(item => item.id != booking.id);
            state.bookings.push(booking)
        },
        removeBooking(state, id) {
            state.bookings = state.bookings.filter(item => item.id != id)
        }
    },
    actions: {
        async loadBookingsAction({commit, state}, data) {
            let weekStart = state.bookingCalendarDate.clone();
            data = {
                doctor: state.doctor,
                from: weekStart.format("YYYY-MM-DD"),
                to: weekStart.add(1, 'weeks').format("YYYY-MM-DD")
            };

            const response = await bookingApi.list(data);
            const json = await response.json();

            commit('setBookings', json)
        },
        async moveBookingAction({commit, state}, data) {
            const result = await bookingApi.move(data);
            const json = await result.json();
            commit('updateBooking', json)
        },
        async updateBookingAction({commit, state}, data) {
            data.doctor = state.doctor;
            const result = await bookingApi.update(data);
            const json = await result.json();
            commit('updateBooking', json)
        },
        async removeBookingAction({commit, state}, id) {
            const result = await bookingApi.remove(id);

            if (result.ok) {
                commit('removeBooking', id)
            }
        },
    }
}