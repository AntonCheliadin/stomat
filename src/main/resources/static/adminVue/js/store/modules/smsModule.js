import smsApi from "../../api/smsApi";

const parseXml = (xmlString) => {
    return new DOMParser().parseFromString(xmlString, "application/xml")
};

export default {
    namespaced: true,
    state: {
        balance: null
    },
    getters: {
        getBalance: (state) => {
            return state.balance;
        }
    },
    mutations: {
        setBalance(state, balance) {
            state.balance = balance;
        }
    },
    actions: {
        async sendSms({commit}, sms) {
            const response = await smsApi.sendSms(sms);
            return parseXml(response.bodyText);
        },
        async fetchBalance({commit}) {
            const response = await smsApi.getBalance();
            let xml = parseXml(response.bodyText);
            const balance = xml.getElementsByTagName('balance')[0].innerHTML;

            commit('setBalance', balance)
        }
    },

}