import isScreen from '@/core/screenHelper';

export default {
  state: {
    sidebarClose: false,
    sidebarStatic: false,
    sidebarActiveElement: null,
    chatOpen: false,
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
      if (e.direction === 4 && !state.chatOpen) {
        state.sidebarClose = false;
      }

      if (e.direction === 2 && !state.sidebarClose) {
        state.sidebarClose = true;
        return;
      }

      state.chatOpen = e.direction === 2;
    },
    changeSidebarActive(state, index) {
      state.sidebarActiveElement = index;
    },
  },
  actions: {
    async toggleSidebar({ commit }) {
      commit('toggleSidebar');
    },
    async switchSidebar({ commit }, value) {
      commit('switchSidebar', value);
    },
    async handleSwipe({ commit }, e) {
      commit('handleSwipe', e);
    },
    async changeSidebarActive({ commit }, index) {
      commit('changeSidebarActive', index);
    },
  },
};
