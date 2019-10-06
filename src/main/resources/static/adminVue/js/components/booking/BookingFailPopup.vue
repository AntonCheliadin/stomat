<template>
    <div>
        <h3>{{$t('manage.doctor.tabs.make-booking.fail.title')}}</h3>
        <p v-text="errorMessage"></p>
    </div>
</template>

<script>
    import HttpStatus from "../../constants/HttpStatus";

    export default {
        name: "BookingFailPopup",
        props: ['responseCode'],
        data() {
            return {
                errorMessage: this.responseCode === HttpStatus.CONFLICT
                    ? this.$t('manage.doctor.tabs.make-booking.fail.error.busy')
                    : this.$t('manage.doctor.tabs.make-booking.fail.error.unknown')
            }
        }
    }
</script>

<style scoped>

</style>