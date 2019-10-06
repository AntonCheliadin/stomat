<template>
    <div>
        <doctor-layout v-bind:doctor="doctor"></doctor-layout>
        <vuedal></vuedal>
    </div>
</template>

<script>
    import {Component as Vuedal} from 'vuedals';
    import DoctorLayout from "../../../components/booking/layout/DoctorLayout.vue";

    export default {
        components: {DoctorLayout, Vuedal},
        props: ['doctor']
    }
</script>

<style>

</style>