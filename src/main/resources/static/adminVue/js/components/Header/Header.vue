<template>
    <b-navbar class="header d-print-none">
        <b-nav>
            <b-nav-item>
                <a class="d-md-down-none px-2" href="#" @click="toggleSidebarMethod" id="barsTooltip">
                    <font-awesome-icon icon="align-justify" size="lg"/>
                </a>
                <a class="fs-lg d-lg-none" href="#" @click="switchSidebarMethod">
                    <font-awesome-icon icon="align-justify" size="lg"/>
                </a>
            </b-nav-item>
        </b-nav>
        <b-nav class="ml-auto">
            <b-nav-item-dropdown class="settingsDropdown d-sm-down-none" no-caret right>
                <template slot="button-content">
                    <font-awesome-icon icon="cog" size="lg"/>
                </template>
                <b-dropdown-item-button @click="logout">
                    <font-awesome-icon icon="sign-out-alt" size="lg"/> Log Out
                </b-dropdown-item-button>
            </b-nav-item-dropdown>
        </b-nav>
    </b-navbar>
</template>

<script>
    import {mapActions, mapState} from 'vuex';
    import Notifications from '@/components/Notifications/Notifications';

    export default {
        name: 'Headed',
        components: {Notifications},
        computed: {
            ...mapState('sidebarModule', {
                sidebarClose: state => state.sidebarClose,
                sidebarStatic: state => state.sidebarStatic,
            }),
        },
        methods: {
            ...mapActions('sidebarModule', ['toggleSidebar', 'switchSidebar', 'changeSidebarActive']),
            switchSidebarMethod() {
                if (!this.sidebarClose) {
                    this.switchSidebar(true);
                    this.changeSidebarActive(null);
                } else {
                    this.switchSidebar(false);
                    const paths = this.$route.fullPath.split('/');
                    paths.pop();
                    this.changeSidebarActive(paths.join('/'));
                }
            },
            toggleSidebarMethod() {
                this.toggleSidebar();

                if (this.sidebarStatic) {
                    this.changeSidebarActive(null);
                } else {
                    const paths = this.$route.fullPath.split('/');
                    paths.pop();
                    this.changeSidebarActive(paths.join('/'));
                }
            },
            logout() {
                window.location = "/logout";
            },
        },
    };
</script>

<style src="./Header.scss" lang="scss" scoped/>
