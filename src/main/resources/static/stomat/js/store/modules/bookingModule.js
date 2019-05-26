import freeTimeApi from "../../api/freeTimeApi";
import moment from 'moment';

export default {
    state: {
        freeTimes: [], //list of scheduleItem
        doctor: 10,
        bookingCalendarDate: moment(),
        bookingParams: null
    },
    getters: {
        freeTimesToCalendarEvents: (state) => {
            let events = [];
            for (let freeTime of state.freeTimes) {

                events.push({
                    className: 'free-time',
                    title: "free time",
                    start: freeTime.from,
                    end: freeTime.to
                })
            }

            return events;
        },
        getBookingParams: (state) => {
            return state.bookingParams;
        }
    },

    mutations: {
        setFreeTimes(state, freeTimes) {
            state.freeTimes = freeTimes
        },
        setBookingCalendarDate(state, date) {
            state.bookingCalendarDate = date;
        },
        setBookingParams(state, data) {
            state.bookingParams = data;
        }
    },
    actions: {
        async loadFreeTimesAction({commit, state}, data) {
            let weekStart = state.bookingCalendarDate.clone();
            data = {
                doctor: state.doctor,
                from: weekStart.format("YYYY-MM-DD"),
                to: weekStart.add(1, 'weeks').format("YYYY-MM-DD")
            };

            const response = await freeTimeApi.get(data);
            const json = await response.json();

            commit('setFreeTimes', json)
        },
    }
}