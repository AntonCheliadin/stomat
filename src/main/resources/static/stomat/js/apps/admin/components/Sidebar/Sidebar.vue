<template>
    <nav
            :class="{sidebar: true, sidebarStatic, sidebarOpened}">
        <header class="logo">
            <router-link to="/admin/dashboard">
                <span class="text-warning">{{$t('app.name')}}</span>
                <br>{{$t('app.dentistry')}}
            </router-link>
        </header>
        <ul class="nav">
            <NavLink
                    header="general.dashboard"
                    link="/admin/dashboard"
                    index="dashboard"
                    isHeader
                    :fontIcon="icons.home"
            />
            <NavLink
                    :activeItem="activeItem"
                    header="doctors.title"
                    link="/admin/doctors"
                    index="doctors"
                    :fontIcon="icons.doctors"
                    :childrenLinks="[
          { header: 'doctors.list.title', link: '/admin/doctors/list' },
          { header: 'doctors.add.title', link: '/admin/doctors/add' },
          { header: 'doctors.schedule.title', link: '/admin/doctors/schedule' },
        ]"
            />
            <NavLink
                    header="sms.title"
                    link="/admin/sms"
                    index="sms"
                    isHeader
                    :fontIcon="icons.sms"
            />
        </ul>
    </nav>
</template>

<script>
    import {mapActions, mapState} from 'vuex';
    import NavLink from './NavLink/NavLink';
    import {faEnvelope, faHome, faUserMd} from '@fortawesome/free-solid-svg-icons'

    export default {
        name: 'Sidebar',
        components: {NavLink},
        data() {
            return {
                icons: {
                    home: faHome,
                    doctors: faUserMd,
                    sms: faEnvelope
                }
            };
        },
        methods: {
            ...mapActions('sidebarModule', ['changeSidebarActive']),
            setActiveByRoute() {
                const paths = this.$route.fullPath.split('/');
                paths.pop();
                this.changeSidebarActive(paths.join('/'));
            },
        },
        created() {
            this.setActiveByRoute();
        },
        computed: {
            ...mapState('sidebarModule', {
                sidebarStatic: state => state.sidebarStatic,
                sidebarOpened: state => !state.sidebarClose,
                activeItem: state => state.sidebarActiveElement,
            }),
        },
    };
</script>

<!-- Sidebar styles should be scoped -->
<style src="./Sidebar.scss" lang="scss" scoped/>
