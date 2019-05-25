import Vue from 'vue'
import Vuex from 'vuex'
import weekScheduleApi from 'api/weekScheduleApi'
import extraScheduleApi from 'api/extraScheduleApi'
import moment from 'moment'

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        weekSchedule: [], //list of scheduleItem
        extraSchedule: [],
        weekScheduleBackground: [],
        doctor: 10,
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
        // weekScheduleBackgroundCalendarEvents: (state) => {
        //     this._generateWeekScheduleEvents(state.weekScheduleBackground, true)
        // },
        // _generateWeekScheduleEvents: (state) => (weekSchedule, background) => {
        //     console.log("_generateWeekScheduleEvents", state, weekSchedule, background)
        //     let events = [];
        //     for (let scheduleItem of weekSchedule) {
        //         let weekDay = moment().startOf('isoWeek').add(scheduleItem.dayOfWeek - 1, 'days');
        //
        //         events.push({
        //             id: scheduleItem.id,
        //             title: scheduleItem.id,
        //             start: weekDay.format("YYYY-MM-DD ") + scheduleItem.timeFrom,
        //             end: weekDay.format("YYYY-MM-DD ") + scheduleItem.timeTo,
        //             background: background
        //         })
        //     }
        //     return events;
        // },
        // extraScheduleCalendarEvents: (state) => {
        //     console.log("extraScheduleCalendarEvents", this, state)
        //     let events = [];
        //     for (let scheduleItem of state.extraSchedule) {
        //
        //         events.push({
        //             id: scheduleItem.id,
        //             className: 'extra-schedule-type-' + scheduleItem.type,
        //             title: scheduleItem.type,
        //             start: scheduleItem.fromDate,
        //             end: scheduleItem.toDate,
        //             allDay: scheduleItem.allDay
        //         })
        //     }
        //     return events;
        // },
        //todo: optimize
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
        setWeekSchedule(state, weekSchedule) {
            state.weekSchedule = weekSchedule
        },
        setWeekScheduleBackground(state, weekScheduleBackground) {
            state.weekScheduleBackground = weekScheduleBackground
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
        },
        setExtraSchedule(state, schedule) {
            state.extraSchedule = schedule.map((it) => {
                it.doctor = state.doctor;
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
        removeExtraScheduleItem(state, scheduleItem) {
            state.extraSchedule = state.extraSchedule.filter(item => item.id !== Number(scheduleItem.id))
        }
    },
    actions: {
        async loadWeekScheduleAction({commit, state}, doctor) {
            const response = await weekScheduleApi.get(doctor.id);
            const json = await response.json();

            commit('setWeekSchedule', json)
        },
        async loadBackgroundWeekScheduleAction({commit, state}, doctor) {
            const response = await weekScheduleApi.get(doctor.id);
            const json = await response.json();

            commit('setWeekScheduleBackground', json)
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
        },
        async loadExtraScheduleAction({commit, state}, data) {
            data.doctor = state.doctor;
            const response = await extraScheduleApi.get(data);
            const json = await response.json();

            commit('setExtraSchedule', json)
        },
        async addExtraScheduleAction({commit, state}, data) {
            data.doctor = state.doctor;
            const result = await extraScheduleApi.add(data);
            const json = await result.json();
            commit('addExtraScheduleItem', json)
        },
        async updateExtraScheduleAction({commit, state}, data) {
            data.doctor = state.doctor;
            const result = await extraScheduleApi.update(data);
            const json = await result.json();
            commit('updateExtraScheduleItem', json)
        },
        async removeExtraScheduleAction({commit, state}, data) {
            data.doctor = state.doctor;
            const result = await extraScheduleApi.remove(data);

            if (result.ok) {
                commit('removeExtraScheduleItem', data)
            }
        },
    }
})