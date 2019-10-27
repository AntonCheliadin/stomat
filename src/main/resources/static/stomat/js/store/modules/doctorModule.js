import doctorApi from "../../api/doctorApi";

export default {
    state: {
        doctors: []
    },
    getters: {
        findDoctorById: (state) => (id) => {
            return state.doctors.find((it) => it.id == id);
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
            const response = await doctorApi.list();
            const json = await response.json();

            commit('setDoctors', json)
        },
        async loadDoctor({commit}, id) {
            const response = await doctorApi.get(id);
            return await response.json();
        },
        async createDoctor({commit}, doctor) {
            const response = await doctorApi.create(doctor);
            return await response.json();
        },
        async updateDoctor({commit}, doctor) {
            const response = await doctorApi.update(doctor);
            return await response.json();
        },
        async deleteDoctor({commit}, doctor) {
            const response = await doctorApi.delete(doctor);
            return await response.json();
        }
    }
}