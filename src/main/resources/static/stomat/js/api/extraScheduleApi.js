import Vue from 'vue'

const extraScheduleApi = Vue.resource('/schedule/extra{/id}');

export default {
    get: data => extraScheduleApi.get(data),
    add: item => extraScheduleApi.save({}, item),
    update: item => extraScheduleApi.update({id: item.id}, item),
    remove: item => extraScheduleApi.remove({id: item.id}, {doctor: item.doctor})
}
