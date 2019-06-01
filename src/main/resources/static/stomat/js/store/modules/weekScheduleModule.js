import moment from "moment";
import weekScheduleApi from "../../api/weekScheduleApi";

export default {
    state: {
        weekSchedule: [], //list of scheduleItem
        calendarDate: moment()
    },
    getters: {
        weekScheduleToCalendarEvents: (state) => {
            let events = [];
            for (let scheduleItem of state.weekSchedule) {
                let weekDay = moment().startOf('isoWeek').add(scheduleItem.dayOfWeek - 1, 'days');

                events.push({
                    id: scheduleItem.id,
                    title: scheduleItem.id,
                    start: weekDay.format("YYYY-MM-DD ") + scheduleItem.timeFrom,
                    end: weekDay.format("YYYY-MM-DD ") + scheduleItem.timeTo
                })
            }
            return events;
        },
    },

    mutations: {
        setWeekSchedule(state, weekSchedule) {
            state.weekSchedule = weekSchedule
        },
        setCalendarDate(state, calendarDate) {
            state.calendarDate = calendarDate
        },
        addWeekScheduleItem(state, scheduleItem) {
            state.weekSchedule.push(scheduleItem)
        },
        updateWeekScheduleItem(state, scheduleItem) {
            state.weekSchedule = state.weekSchedule.filter(item => item.id !== scheduleItem.id);
            state.weekSchedule.push(scheduleItem)
        },
        removeWeekScheduleItem(state, scheduleItem) {
            state.weekSchedule = state.weekSchedule.filter(item => item.id !== scheduleItem.id)
        }
    },
    actions: {
        async loadWeekScheduleAction({commit, state}, doctor) {
            const response = await weekScheduleApi.get(doctor);
            const json = await response.json();

            commit('setWeekSchedule', json)
        },
        async addWeekScheduleAction({commit, state}, data) {
            const result = await weekScheduleApi.add(data);
            const json = await result.json();
            const index = state.weekSchedule.findIndex(item => item.id === json.id);

            if (index > -1) {
                commit('updateWeekScheduleItem', json)
            } else {
                commit('addWeekScheduleItem', json)
            }
        },
        async updateWeekScheduleAction({commit}, data) {
            const result = await weekScheduleApi.update(data);
            const json = await result.json();
            commit('updateWeekScheduleItem', json)
        },
        async removeWeekScheduleAction({commit}, data) {
            const result = await weekScheduleApi.remove(data);

            if (result.ok) {
                commit('removeWeekScheduleItem', data)
            }
        }
    }
}