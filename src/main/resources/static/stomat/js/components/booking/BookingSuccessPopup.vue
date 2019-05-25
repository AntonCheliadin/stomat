<template>
    <div>
        booking success
    </div>
</template>

<script>
    export default {
        name: "BookingSuccessPopup",
        props: ['booking'],
        data() {
            console.log("this.booking", this.booking);
            return {
                booking: this.booking
            }
        }
    }
</script>

<style scoped>

</style>