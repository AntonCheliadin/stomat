<template>
    <div>
        <div>Тип события {{event.id}}:</div>

        <input type="radio" id="typeInclude" value="INCLUDE" v-model="type">
        <label for="typeInclude">добавить доступное время</label>
        <br>
        <input type="radio" id="typeExclude" value="EXCLUDE" v-model="type">
        <label for="typeExclude">исключить из доступного времени</label>
        <br>
        <input id="allDay" type="checkbox" v-model="allDay" true-value="true" false-value="false">
        <label for="allDay">на весь день</label>
        <br>
        <input id="fromDate" v-text="fromDate" v-model="fromDate"/>
        <label for="fromDate">От</label>
        <br>
        <input id="toDate" v-text="toDate" v-model="toDate"/>
        <label for="toDate">До</label>
        <br>
        <button @click="save">Save</button>
        <button v-if="id" @click="deleteClick">Delete</button>
    </div>
</template>

<script>
    import {mapGetters, mapActions} from 'vuex';
    import {default as Vuedals, Component as Vuedal, Bus as VuedalsBus} from 'vuedals';
    import moment from 'moment';

    export default {
        name: "ExtraSchedulePopup",
        props: ['event'],
        computed: mapGetters(['extraScheduleById']),
        created() {
        },
        data() {
            return {
                id: this.event.id,
                type: this.event.type ? this.event.type : "INCLUDE",
                fromDate: moment(this.event.fromDate).format('YYYY-MM-DD HH:mm'),
                toDate: this.event.toDate ? moment(this.event.toDate).format('YYYY-MM-DD HH:mm') : "",
                allDay: this.event.allDay
            }
        },
        methods: {
            ...mapActions(['loadExtraScheduleAction', 'addExtraScheduleAction', 'updateExtraScheduleAction',
                'removeExtraScheduleAction']),
            save() {
                this.event.type = this.type;
                this.event.fromDate = this.fromDate;
                this.event.toDate = this.toDate;
                this.event.allDay = this.allDay;
                if (this.event.id == null) {
                    this.addExtraScheduleAction(this.event);
                } else {
                    this.updateExtraScheduleAction(this.event);
                }
                this.$emit('vuedals:close');
            },
            deleteClick() {
                this.removeExtraScheduleAction(this.event);
                this.$emit('vuedals:close');
            },
        }
    }
</script>

<style scoped>

</style>