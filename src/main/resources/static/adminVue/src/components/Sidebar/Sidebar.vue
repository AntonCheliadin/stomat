<template>
  <nav
    :class="{sidebar: true, sidebarStatic, sidebarOpened}"
    @mouseenter="sidebarMouseEnter"
    @mouseleave="sidebarMouseLeave"
  >
    <header class="logo">
      <router-link to="/admin"><span class="text-warning">Kapelko</span> stomat</router-link>
    </header>
    <ul class="nav">
      <NavLink
        header="Dashboard"
        link="/admin/dashboard"
        iconName="flaticon-home"
        index="dashboard"
        isHeader
      />
      <NavLink
              :activeItem="activeItem"
              header="Doctors"
              link="/admin/doctors"
              iconName="flaticon-user"
              index="doctors"
              :childrenLinks="[
          { header: 'List', link: '/admin/doctors/list' },
          { header: 'Add', link: '/admin/doctors/add' },
          { header: 'Schedule', link: '/admin/doctors/schedule' },
        ]"
      />
      <NavLink
        header="SMS"
        link="/admin/sms"
        iconName="flaticon-smartphone"
        index="sms"
        isHeader
      />
    </ul>
    <p>
    <div class="sidebarAlerts"></div>
  </nav>
</template>

<script>
import { mapState, mapActions } from 'vuex';
import isScreen from '@/core/screenHelper';
import NavLink from './NavLink/NavLink';

export default {
  name: 'Sidebar',
  components: { NavLink },
  data() {
    return {
      alerts: [
        {
          id: 0,
          title: 'Sales Report',
          value: 15,
          footer: 'Calculating x-axis bias... 65%',
          color: 'info',
        },
        {
          id: 1,
          title: 'Personal Responsibility',
          value: 20,
          footer: 'Provide required notes',
          color: 'danger',
        },
      ],
    };
  },
  methods: {
    ...mapActions('layout', ['changeSidebarActive', 'switchSidebar']),
    setActiveByRoute() {
      const paths = this.$route.fullPath.split('/');
      paths.pop();
      this.changeSidebarActive(paths.join('/'));
    },
    sidebarMouseEnter() {
      if (!this.sidebarStatic && (isScreen('lg') || isScreen('xl'))) {
        this.switchSidebar(false);
        this.setActiveByRoute();
      }
    },
    sidebarMouseLeave() {
      if (!this.sidebarStatic && (isScreen('lg') || isScreen('xl'))) {
        this.switchSidebar(true);
        this.changeSidebarActive(null);
      }
    },
  },
  created() {
    this.setActiveByRoute();
  },
  computed: {
    ...mapState('layout', {
      sidebarStatic: state => state.sidebarStatic,
      sidebarOpened: state => !state.sidebarClose,
      activeItem: state => state.sidebarActiveElement,
    }),
  },
};
</script>

<!-- Sidebar styles should be scoped -->
<style src="./Sidebar.scss" lang="scss" scoped/>
