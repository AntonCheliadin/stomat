import Vue from 'vue';
import Router from 'vue-router';

import Layout from '@/components/Layout/Layout';

// Doctors
import DoctorsListPage from '@/pages/Doctors/list/DoctorsList';
import DoctorsSchedulePage from '@/pages/Doctors/schedule/DoctorsSchedule';
// SMS
import SmsPage from '@/pages/SMS/SMS';

// Main
import DashboardPage from '@/pages/Dashboard/Dashboard';
import AddDoctorPage from "@/pages/Doctors/add/AddDoctor";

Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/admin',
      name: 'Layout',
      component: Layout,
      children: [
        {
          path: 'dashboard',
          name: 'DashboardPage',
          component: DashboardPage,
        },
        {
          path: 'doctors/list',
          name: 'DoctorsListPage',
          component: DoctorsListPage,
        },
        {
          path: 'doctors/add',
          name: 'AddDoctorPage',
          component: AddDoctorPage,
        },
        {
          path: 'doctors/schedule',
          name: 'DoctorsSchedulePage',
          component: DoctorsSchedulePage,
        },
        {
          path: 'sms',
          name: 'SmsPage',
          component: SmsPage,
        },
      ],
    },
  ],
});
