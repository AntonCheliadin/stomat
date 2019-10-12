import isScreen from '@/core/screenHelper';

export default {
    namespaced: true,
    state: {
        sidebarClose: false,
        sidebarStatic: true,
        sidebarActiveElement: null
    },
    getters: {
        getSidebarStatic: (state) => {
            return state.sidebarStatic;
        },
        getSidebarClose: (state) => {
            return state.sidebarStatic;
        }
    },
    mutations: {
        toggleSidebar(state) {
            const nextState = !state.sidebarStatic;

            localStorage.sidebarStatic = nextState;
            state.sidebarStatic = nextState;

            if (!nextState && (isScreen('lg') || isScreen('xl'))) {
                state.sidebarClose = true;
            }
        },
        switchSidebar(state, value) {
            if (value) {
                state.sidebarClose = value;
            } else {
                state.sidebarClose = !state.sidebarClose;
            }
        },
        setSidebarState(state, value) {
            state.sedebarClose = value;
        },
        handleSwipe(state, e) {
            if (e.direction === 4) {
                state.sidebarClose = false;
            }

            if (e.direction === 2 && !state.sidebarClose) {
                state.sidebarClose = true;
            }
        },
        changeSidebarActive(state, index) {
            state.sidebarActiveElement = index;
        },
    },
    actions: {
        async toggleSidebar({commit}) {
            commit('toggleSidebar');
        },
        async switchSidebar({commit}, value) {
            commit('switchSidebar', value);
        },
        async handleSwipe({commit}, e) {
            commit('handleSwipe', e);
        },
        async changeSidebarActive({commit}, index) {
            commit('changeSidebarActive', index);
        },
    },
};
