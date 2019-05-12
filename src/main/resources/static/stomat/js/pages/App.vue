<template>
    <div>
        <extra-schedule></extra-schedule>
        <week-schedule></week-schedule>
    </div>
</template>

<script>
    import WeekSchedule from "../components/schedule/WeekSchedule.vue";
    import ExtraSchedule from "../components/schedule/ExtraSchedule.vue";

    export default {
        components: {ExtraSchedule, WeekSchedule},
        data() {
            return {}
        }
    }
</script>

<style>

</style>