<template>
    <div>
        extra schedule form {{event.id}}
    </div>
</template>

<script>
    export default {
        name: "ExtraSchedulePopup",
        props: ['event'],
        created() {
            console.log("popup event=", this.event)
        }
    }
</script>

<style scoped>

</style>