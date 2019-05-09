import Vue from 'vue'
import Vuex from 'vuex'
import scheduleWeekApi from 'api/scheduleWeek'
import moment from 'moment'

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        scheduleWeek: [] //list of scheduleItem
    },
    getters: {
        scheduleWeekCalendarEvents: (state) => {
            let events = [];

            for (let i in state.scheduleWeek) {
                let scheduleItem = state.scheduleWeek[i];
                let weekDay = moment().startOf('week').add(scheduleItem.dayOfWeek - 1, 'days');

                events.push({
                    id: scheduleItem.id,
                    title: scheduleItem.id,
                    start: weekDay.format("YYYY-MM-DD ") + scheduleItem.timeFrom,
                    end: weekDay.format("YYYY-MM-DD ") + scheduleItem.timeTo
                })
            }
            return events;
        }
    },
    mutations: {
        setScheduleWeek(state, scheduleWeek) {
            state.scheduleWeek = scheduleWeek
        },
        addScheduleWeekItem(state, scheduleItem) {
            state.scheduleWeek = [...state.scheduleWeek, scheduleItem]
        },
        updateScheduleWeekItem(state, scheduleItem) {
            state.scheduleWeek = [
                ...state.scheduleWeek.filter(item => item.id !== scheduleItem.id),
                scheduleItem
            ]
        },
        removeScheduleWeekItem(state, scheduleItem) {
            state.scheduleWeek = state.scheduleWeek.filter(item => item !== scheduleItem)
        }
    },
    actions: {
        async loadScheduleWeekAction({commit, state}, doctor) {
            const response = await scheduleWeekApi.get(doctor.id);
            const json = await response.json();

            commit('setScheduleWeek', json)
        },
        async addScheduleWeekAction({commit, state}, data) {
            const result = await scheduleWeekApi.add(data);
            const json = await result.json();
            const index = state.scheduleWeek.findIndex(item => item.id === json.id);

            if (index > -1) {
                commit('updateScheduleWeekItem', json)
            } else {
                commit('addScheduleWeekItem', json)
            }
        },
        async updateScheduleWeekAction({commit}, data) {
            const result = await scheduleWeekApi.update(data);
            const json = await result.json();
            commit('updateScheduleWeekItem', json)
        },
        async removeScheduleWeekAction({commit}, data) {
            const result = await scheduleWeekApi.remove(data.doctor.id, data.scheduleItem.id);

            if (result.ok) {
                commit('removeScheduleWeekItem', scheduleItem)
            }
        },
    }
})