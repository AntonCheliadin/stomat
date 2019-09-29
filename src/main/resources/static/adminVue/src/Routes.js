import Vue from 'vue';
import Router from 'vue-router';

import Layout from '@/components/Layout/Layout';
import Login from '@/pages/Login/Login';

// Doctors
import DoctorsListPage from '@/pages/Doctors/list/DoctorsList';
import DoctorsSchedulePage from '@/pages/Doctors/schedule/DoctorsSchedule';
// SMS
import SmsPage from '@/pages/SMS/SMS';

// Main
import AnalyticsPage from '@/pages/Dashboard/Dashboard';

Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/admin/login',
      name: 'Login',
      component: Login,
    },
    {
      path: '/admin',
      name: 'Layout',
      component: Layout,
      children: [
        {
          path: 'dashboard',
          name: 'AnalyticsPage',
          component: AnalyticsPage,
        },
        {
          path: 'doctors/list',
          name: 'DoctorsListPage',
          component: DoctorsListPage,
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
