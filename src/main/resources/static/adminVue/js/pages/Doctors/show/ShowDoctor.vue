<template>
    <b-row>
        <b-col lg="12">
            <Widget v-if="doctor">
                <DoctorForm :doctor="doctor" @submit="onSubmit"/>
            </Widget>
        </b-col>
    </b-row>
</template>

<script>
    import Widget from '@/components/Widget/Widget';
    import {mapActions} from "vuex";
    import DoctorForm from "@/components/doctor/DoctorForm";

    export default {
        name: "ShowDoctor",
        components: {Widget, DoctorForm},
        created() {
            this.loadDoctorByIdFromUri();
        },
        data() {
            return {
                doctor: null
            };
        },
        methods: {
            ...mapActions(['loadDoctor']),
            async loadDoctorByIdFromUri() {
                this.doctor = await this.loadDoctor(this.$route.params.id);
            },
            onSubmit(submitData) {
                console.log("on submit submitData", submitData)
            }
        },
    }
</script>

<style scoped>

</style>