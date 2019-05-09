import Vue from 'vue'

const scheduleWeekApi = Vue.resource('/schedule/week{/id}');

export default {
    get: doctorId => scheduleWeekApi.get({doctor: doctorId}),
    add: (doctorId, scheduleItem) => scheduleWeekApi.save({doctor: doctorId, scheduleItem: scheduleItem}),
    update: (doctorId, scheduleItem) => scheduleWeekApi.update({
        id: scheduleItem.id,
        doctor: doctorId,
        scheduleItem: scheduleItem
    }),
    remove: (doctorId, scheduleItemId) => scheduleWeekApi.remove({scheduleItemId}, {
        id: scheduleItemId,
        doctor: doctorId
    })
}
