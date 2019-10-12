<template>
    <b-row>
        <b-col lg="12">
            <Widget>
                <doctor-layout v-bind:doctor="this.$route.params.id"></doctor-layout>
                <vuedal></vuedal>
            </Widget>
        </b-col>
    </b-row>
</template>

<script>
    import {Component as Vuedal} from 'vuedals';
    import Widget from '@/components/Widget/Widget';
    import DoctorLayout from "../../../components/booking/layout/DoctorLayout.vue";

    export default {
        components: {DoctorLayout, Vuedal, Widget}
    }
</script>

<style>

</style>