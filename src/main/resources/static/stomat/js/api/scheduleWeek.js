import Vue from 'vue'

const scheduleWeekApi = Vue.resource('/schedule/week{/id}');

export default {
    get: doctorId => scheduleWeekApi.get({doctor: doctorId}),
    add: scheduleItem => scheduleWeekApi.save({}, scheduleItem),
    update: scheduleItem => scheduleWeekApi.update({id: scheduleItem.id}, scheduleItem),
    remove: scheduleItem => scheduleWeekApi.remove({id: scheduleItem.id}, scheduleItem)
}
