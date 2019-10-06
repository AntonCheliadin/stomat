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
        index="dashboard"
        isHeader
        :fontIcon="icons.home"
      />
      <NavLink
              :activeItem="activeItem"
              header="Doctors"
              link="/admin/doctors"
              index="doctors"
              :fontIcon="icons.doctors"
              :childrenLinks="[
          { header: 'List', link: '/admin/doctors/list' },
          { header: 'Add', link: '/admin/doctors/add' },
          { header: 'Schedule', link: '/admin/doctors/schedule' },
        ]"
      />
      <NavLink
        header="SMS"
        link="/admin/sms"
        index="sms"
        isHeader
        :fontIcon="icons.sms"
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
import {faEnvelope, faUserMd, faHome} from '@fortawesome/free-solid-svg-icons'

export default {
  name: 'Sidebar',
  components: { NavLink },
  data() {
    return {
      icons: {
        home: faHome,
        doctors: faUserMd,
        sms: faEnvelope
      },
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
    ...mapActions(['changeSidebarActive', 'switchSidebar']),
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
    ...mapState({
      sidebarStatic: state => state.sidebarStatic,
      sidebarOpened: state => !state.sidebarClose,
      activeItem: state => state.sidebarActiveElement,
    }),
  },
};
</script>

<!-- Sidebar styles should be scoped -->
<style src="./Sidebar.scss" lang="scss" scoped/>
