import moment from "moment";
import weekScheduleApi from "../../api/weekScheduleApi";
import extraScheduleApi from "../../api/extraScheduleApi";

export default {
    state: {
        extraSchedule: [],
        weekScheduleBackground: [],
        calendarDate: moment()
    },
    getters: {
        eventsForExtraScheduleCalendar: (state) => {
            let events = [];
            for (let scheduleItem of state.extraSchedule) {

                events.push({
                    id: scheduleItem.id,
                    className: 'extra-schedule-type-' + scheduleItem.type,
                    title: scheduleItem.type,
                    start: scheduleItem.fromDate,
                    end: scheduleItem.toDate,
                    allDay: scheduleItem.allDay
                })
            }

            let weekStart = state.calendarDate.startOf('isoWeek');

            for (let scheduleItem of state.weekScheduleBackground) {
                let weekDay = weekStart.clone().add(scheduleItem.dayOfWeek - 1, 'days');

                events.push({
                    start: weekDay.format("YYYY-MM-DD ") + scheduleItem.timeFrom,
                    end: weekDay.format("YYYY-MM-DD ") + scheduleItem.timeTo,
                    rendering: 'background'
                })
            }

            return events;

            // return this.extraScheduleCalendarEvents(state)
            //     .concat(this.weekScheduleBackgroundCalendarEvents())
        },
        extraScheduleById: (state) => (id) => {
            return state.extraSchedule.find(item => item.id == id);
        }
    },

    mutations: {
        setWeekScheduleBackground(state, weekScheduleBackground) {
            state.weekScheduleBackground = weekScheduleBackground
        },
        // setCalendarDate(state, calendarDate) {
        //     state.calendarDate = calendarDate
        // },
        setExtraSchedule(state, data) {
            state.extraSchedule = data.json.map((it) => {
                it.doctor = data.doctor;
                return it;
            })
        },
        addExtraScheduleItem(state, scheduleItem) {
            state.extraSchedule.push(scheduleItem)
        },
        updateExtraScheduleItem(state, scheduleItem) {
            state.extraSchedule = state.extraSchedule.filter(item => item.id !== scheduleItem.id);
            state.extraSchedule.push(scheduleItem)
        },
        removeExtraScheduleItem(state, scheduleItemId) {
            state.extraSchedule = state.extraSchedule.filter(item => item.id !== Number(scheduleItemId))
        }
    },
    actions: {
        async loadBackgroundWeekScheduleAction({commit, state}, doctor) {
            const response = await weekScheduleApi.get(doctor);
            const json = await response.json();

            commit('setWeekScheduleBackground', json)
        },
        async loadExtraScheduleAction({commit, state}, data) {
            const response = await extraScheduleApi.get(data);
            const json = await response.json();

            commit('setExtraSchedule', {json: json, doctor: data.doctor})//todo: return doctorId for schedule...
        },
        async addExtraScheduleAction({commit, state}, data) {
            const result = await extraScheduleApi.add(data);
            const json = await result.json();
            commit('addExtraScheduleItem', json)
        },
        async updateExtraScheduleAction({commit, state}, data) {
            const result = await extraScheduleApi.update(data);
            const json = await result.json();
            json.doctor = data.doctor;
            commit('updateExtraScheduleItem', json)
        },
        async removeExtraScheduleAction({commit, state}, itemId) {
            const result = await extraScheduleApi.remove(itemId);

            if (result.ok) {
                commit('removeExtraScheduleItem', itemId)
            }
        },
    }
}