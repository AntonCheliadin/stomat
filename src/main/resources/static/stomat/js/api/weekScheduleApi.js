import Vue from 'vue'

const weekScheduleApi = Vue.resource('/schedule/week{/id}');

export default {
    get: doctorId => weekScheduleApi.get({doctor: doctorId}),
    add: scheduleItem => weekScheduleApi.save({}, scheduleItem),
    update: scheduleItem => weekScheduleApi.update({id: scheduleItem.id}, scheduleItem),
    remove: scheduleItem => weekScheduleApi.remove({id: scheduleItem.id}, scheduleItem)
}
