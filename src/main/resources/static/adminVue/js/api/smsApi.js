import Vue from 'vue'

const sendSmsApi = Vue.resource('/api/sms/send');
const balanceSmsApi = Vue.resource('/api/sms/balance');

export default {
    sendSms: (sms) => sendSmsApi.save({}, sms),
    getBalance: () => balanceSmsApi.get()
}
