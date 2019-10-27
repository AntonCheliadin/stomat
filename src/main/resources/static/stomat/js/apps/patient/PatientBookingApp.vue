<template>
    <div>
        <patient-layout></patient-layout>
        <vuedal></vuedal>
    </div>
</template>

<script>
    import {Component as Vuedal} from 'vuedals';
    import PatientLayout from "./components/layout/PatientLayout.vue";

    export default {
        name: "PatientBookingApp",
        components: {PatientLayout, Vuedal},
    }
</script>

<style scoped>

</style>