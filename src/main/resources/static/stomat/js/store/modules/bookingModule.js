import freeTimeApi from "../../api/freeTimeApi";
import moment from 'moment';

export default {
    state: {
        freeTimes: [], //list of scheduleItem
        doctor: 10,
        bookingCalendarDate: moment()
    },
    getters: {
        freeTimesToCalendarEvents: (state) => {
            let events = [];
            for (let freeTime of state.freeTimes) {

                events.push({
                    className: 'free-time',
                    // title: freeTime.type,
                    start: freeTime.from,
                    end: freeTime.to
                })
            }

            return events;
        }
    },

    mutations: {
        setFreeTimes(state, freeTimes) {
            state.freeTimes = freeTimes
        },
        setBookingCalendarDate(state, date) {
            state.bookingCalendarDate = date;
        }
    },
    actions: {
        async loadFreeTimesAction({commit, state}, data) {
            data.doctor = state.doctor;
            const response = await freeTimeApi.get(data);
            const json = await response.json();

            commit('setFreeTimes', json)
        }
    }
}