<template>
    <div>
        booking failed
        <p v-text="errorMessage"></p>
    </div>
</template>

<script>
    import HttpStatus from "../../constants/HttpStatus";

    export default {
        name: "BookingFailPopup",
        props: ['responseCode'],
        data() {
            return {//if (response.code === HttpStatus.CONFLICT) {
                errorMessage: this.responseCode === HttpStatus.CONFLICT
                    ? "Время уже занято"
                    : "Что-то пошло не так, попробуйте еще раз или позвоните нам"
            }
        }
    }
</script>

<style scoped>

</style>