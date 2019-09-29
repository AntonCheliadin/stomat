<template>
    <div>
        {{$t('manage.doctor.tabs.make-booking.success.title')}}
    </div>
</template>

<script>
    export default {
        name: "BookingSuccessPopup",
        props: ['booking'],
        data() {
            return {}
        }
    }
</script>

<style scoped>

</style>