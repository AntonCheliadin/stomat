<template>
    <div :class="{root: true, sidebarClose, sidebarStatic}">
        <Sidebar/>
        <div class="wrap">
            <Header/>
            <v-touch class="content" @swipeleft="handleSwipe" @swiperight="handleSwipe"
                     :swipe-options="{direction: 'horizontal', threshold: 100}">
                <router-view/>
            </v-touch>
        </div>
    </div>
</template>

<script>
    import {mapState, mapActions} from 'vuex';

    import Sidebar from '@/apps/admin/components/Sidebar/Sidebar';
    import Header from '@/apps/admin/components/Header/Header';

    export default {
        name: 'Layout',
        components: {Sidebar, Header},
        methods: {
            ...mapActions(
                'sidebarModule', ['switchSidebar', 'handleSwipe', 'changeSidebarActive'],),
        },
        computed: {
            ...mapState('sidebarModule', {
                sidebarClose: state => state.sidebarClose,
                sidebarStatic: state => state.sidebarStatic
            }),
        },
        created() {
            if (!this.sidebarClose) {
                setTimeout(() => {
                    this.switchSidebar(true);
                    this.changeSidebarActive(null);
                }, 500);
            }
        },
    };
</script>

<style scoped src="./Layout.scss" lang="scss"/>
