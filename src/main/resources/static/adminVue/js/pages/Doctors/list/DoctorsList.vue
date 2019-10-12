<template>
    <b-row>
        <b-col lg="12">
            <Widget>
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>{{$t('general.person.firstName')}}</th>
                            <th>{{$t('general.person.fathersName')}}</th>
                            <th>{{$t('general.person.lastName')}}</th>
                            <th>{{$t('general.person.email')}}</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="doctor in getDoctors" v-bind:key="doctor.id">
                            <td>{{ doctor.id }}</td>
                            <td>{{ doctor.firstName }}</td>
                            <td>{{ doctor.fathersName }}</td>
                            <td>{{ doctor.lastName }}</td>
                            <td>{{ doctor.email }}</td>
                            <td>
                                <a :href="'/admin/doctors/show/'+doctor.id">
                                    <font-awesome-icon icon="edit" size="sm"/>
                                </a>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </Widget>
        </b-col>
    </b-row>
</template>

<script>
    import Widget from '@/components/Widget/Widget';
    import {mapGetters, mapActions} from 'vuex'

    export default {
        name: 'DoctorsList',
        components: {Widget},
        computed: mapGetters(['getDoctors']),
        created() {
            this.loadDoctors();
        },
        data() {
            return {};
        },
        methods: {
            ...mapActions(['loadDoctors']),
        },
    };
</script>

<style lang="scss">
</style>
