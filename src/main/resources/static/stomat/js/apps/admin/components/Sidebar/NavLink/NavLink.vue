<template>
    <li v-if="!childrenLinks && isHeader" :class="{headerLink: true, className}">
        <router-link :to="link">
      <span class="icon">
        <font-awesome-icon :icon="icon" size="lg"/>
      </span>
            {{$t(header)}} <sup v-if="label" class="headerLabel">{{label}}</sup>
            <b-badge v-if="badge" class="badge rounded-f" variant="warning" pill>{{badge}}</b-badge>
        </router-link>
    </li>
    <li v-else-if="childrenLinks" :class="{headerLink: true, className}">
        <div @click="() => togglePanelCollapse(link)">
            <router-link :to="link" event="" class="d-flex">
        <span class="icon">
          <font-awesome-icon :icon="icon" size="lg"/>
        </span>
                {{$t(header)}} <sup v-if="label" class="headerLabel">{{label}}</sup>
                <div :class="{caretWrapper: true, carretActive: isActive}">
                    <font-awesome-icon icon="angle-left" size="sm"/>
                </div>
            </router-link>
        </div>
        <b-collapse :id="'collapse' + index" :visible="isActive">
            <ul>
                <NavLink v-for="link in childrenLinks"
                         :activeItem="activeItem"
                         :header="link.header"
                         :index="link.index"
                         :link="link.link"
                         :childrenLinks="link.childrenLinks"
                         :key="link.link"
                />
            </ul>
        </b-collapse>
    </li>
    <li v-else>
        <router-link :to="index !== 'menu' && link">
            {{$t(header)}} <sup v-if="label" class="headerLabel">{{label}}</sup>
        </router-link>
    </li>
</template>

<script>
    import {mapActions} from 'vuex';

    export default {
        name: 'NavLink',
        props: {
            badge: {type: String, default: ''},
            header: {type: String, default: ''},
            headerLink: {type: String, default: ''},
            link: {type: String, default: ''},
            childrenLinks: {type: Array, default: null},
            className: {type: String, default: ''},
            isHeader: {type: Boolean, default: false},
            deep: {type: Number, default: 0},
            activeItem: {type: String, default: ''},
            label: {type: String},
            index: {type: String},
            fontIcon: {type: Object, default: null}
        },
        data() {
            return {
                icon: this.fontIcon,
                headerLinkWasClicked: true,
            };
        },
        methods: {
            ...mapActions('sidebarModule', ['changeSidebarActive']),
            togglePanelCollapse(link) {
                this.changeSidebarActive(link);
                this.headerLinkWasClicked = !this.headerLinkWasClicked
                    || !this.activeItem || !this.activeItem.includes(this.index);
            },
        },
        computed: {
            isActive() {
                return (this.activeItem
                    && this.activeItem.includes(this.index)
                    && this.headerLinkWasClicked);
            },
        },
    };
</script>

<style src="./NavLink.scss" lang="scss" scoped/>
