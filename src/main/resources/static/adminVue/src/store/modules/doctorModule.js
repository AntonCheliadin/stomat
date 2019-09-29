import doctorApi from "../../api/doctorApi";

export default {
    state: {
        doctors: []
    },
    getters: {
        findDoctorById: (state) => (id) => {
            return state.doctors.find((it) => it.id === id);
        },
        getDoctors: (state) => {
            return state.doctors;
        }
    },
    mutations: {
        setDoctors(state, doctors) {
            state.doctors = doctors;
        }
    },
    actions: {
        async loadDoctors({commit}) {
            const response = await doctorApi.get();
            const json = await response.json();

            commit('setDoctors', json)
        }
    }
}