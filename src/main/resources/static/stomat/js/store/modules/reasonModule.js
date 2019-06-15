import reasonApi from "../../api/reasonApi";

export default {
    state: {
        reasons: []
    },
    getters: {
        findOrGetDefaultReason: (state) => (id) => {
            let reason = state.reasons.find((it) => it.id === id);
            if (!reason) {
                reason = state.reasons.find((it) => it.defaults);
            }
            return {
                label: reason.name,
                id: reason.id
            }
        },
        getReasonOptions: (state) => {
            return state.reasons.map((it) => {
                return {
                    label: it.name,
                    id: it.id
                }
            });
        },
        getReasons: (state) => {
            return state.reasons;
        }
    },
    mutations: {
        setReasons(state, reasons) {
            state.reasons = reasons;
        }
    },
    actions: {
        async loadReasons({commit, state}, data) {
            const response = await reasonApi.get();
            const json = await response.json();

            commit('setReasons', json)
        }
    }
}