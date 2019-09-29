import Vue from 'vue'

const weekScheduleApi = Vue.resource('http://localhost:8080/api/manage/schedule/week{/id}');

export default {
    get: doctor => weekScheduleApi.get({doctor: doctor}),
    add: scheduleItem => weekScheduleApi.save({}, scheduleItem),
    update: scheduleItem => weekScheduleApi.update({id: scheduleItem.id}, scheduleItem),
    remove: scheduleItem => weekScheduleApi.remove({id: scheduleItem.id}, scheduleItem)
}
