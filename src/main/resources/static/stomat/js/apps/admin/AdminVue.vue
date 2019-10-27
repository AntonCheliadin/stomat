<template>
    <router-view/>
</template>

<script>
    export default {
        name: 'AdminVueApp',
        created() {
            const currentPath = this.$router.history.current.path;

            if (currentPath === '/' || currentPath === '/admin') {
                this.$router.push('/admin/dashboard');
            }
        },
    };
</script>

<style src="../../../styles/admin/theme.scss" lang="scss"/>
