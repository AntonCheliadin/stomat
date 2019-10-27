import Vue from 'vue';
import Router from 'vue-router';

import Layout from '@/apps/admin/components/Layout/Layout';

// Doctors
import DoctorsListPage from '@/apps/admin/pages/Doctors/list/DoctorsList';
import DoctorsSchedulePage from '@/apps/admin/pages/Doctors/schedule/DoctorsSchedule';
// SMS
import SmsPage from '@/apps/admin/pages/SMS/SMS';

// Main
import DashboardPage from '@/apps/admin/pages/Dashboard/Dashboard';
import AddDoctorPage from "@/apps/admin/pages/Doctors/add/AddDoctor";
import ShowDoctorPage from "@/apps/admin/pages/Doctors/show/ShowDoctor";

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
                    path: 'doctors/show/:id?',
                    name: 'ShowDoctorPage',
                    component: ShowDoctorPage,
                },
                {
                    path: 'doctors/schedule/:id?',
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
