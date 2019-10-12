<template>
    <b-row>
        <b-col lg="12">
            <Widget custom-header :title="'<h2>'+$t('doctors.add.title')+'</h2>'">
                <DoctorForm @submit="onSubmit"/>
            </Widget>
        </b-col>
    </b-row>
</template>

<script>
    import Widget from '@/components/Widget/Widget';
    import DoctorForm from "@/components/doctor/DoctorForm";

    export default {
        name: "AddDoctor",
        components: {Widget, DoctorForm},
        methods: {
            onSubmit(submitData) {
                console.log("on submit AddDoctor submitData", submitData)
            }
        }
    }
</script>

<style scoped>

</style>