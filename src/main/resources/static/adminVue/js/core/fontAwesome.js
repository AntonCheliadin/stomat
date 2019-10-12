import Vue from 'vue'

import {library} from '@fortawesome/fontawesome-svg-core';
import {
    faEnvelope, faAlignJustify, faAngleLeft, faSignOutAlt, faCog, faAngleUp, faAngleDown, faEdit
} from '@fortawesome/free-solid-svg-icons';
import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome'


library.add(
    faAngleLeft, faEnvelope, faAlignJustify, faSignOutAlt, faCog, faAngleUp, faAngleDown, faEdit
);

Vue.component('font-awesome-icon', FontAwesomeIcon);