import moment from "moment";
import weekScheduleApi from "../../api/weekScheduleApi";
import extraScheduleApi from "../../api/extraScheduleApi";
import {extraScheduleToRequestParams} from "../../apps/admin/components/schedule/converters/extraScheduleConverter";

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
        },
        extraScheduleById: (state) => (id) => {
            return state.extraSchedule.find(item => item.id === Number(id));
        }
    },

    mutations: {
        setWeekScheduleBackground(state, weekScheduleBackground) {
            state.weekScheduleBackground = weekScheduleBackground
        },
        setExtraCalendarDate(state, calendarDate) {
            state.calendarDate = calendarDate
        },
        setExtraSchedule(state, extraSchedule) {
            state.extraSchedule = extraSchedule;
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
        async loadBackgroundWeekScheduleAction({commit}, doctor) {
            const response = await weekScheduleApi.get(doctor);
            const json = await response.json();

            commit('setWeekScheduleBackground', json)
        },
        async loadExtraScheduleAction({commit}, data) {
            const response = await extraScheduleApi.get(data);
            const json = await response.json();

            commit('setExtraSchedule', json)
        },
        async addExtraScheduleAction({commit}, data) {
            const result = await extraScheduleApi.add(data);
            const json = await result.json();
            commit('addExtraScheduleItem', json)
        },
        async updateExtraScheduleAction({commit}, extraSchedule) {
            const result = await extraScheduleApi.update(extraScheduleToRequestParams(extraSchedule));
            const json = await result.json();
            commit('updateExtraScheduleItem', json)
        },
        async removeExtraScheduleAction({commit}, itemId) {
            const result = await extraScheduleApi.remove(itemId);

            if (result.ok) {
                commit('removeExtraScheduleItem', itemId)
            }
        },
    }
}