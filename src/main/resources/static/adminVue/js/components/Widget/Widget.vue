<template>
  <section :class="{ widget: true, className }" v-bind="dataWidgster" ref="widget">
    <h5 v-if="title && typeof title === 'string' && !customHeader" class="title">{{title}}</h5>
    <header v-if="title && customHeader" class="title" v-html="title" />
    <div :class="`widgetBody widget-body ${bodyClass}`">
      <slot></slot>
    </div>
  </section>
</template>

<script>
import $ from 'jquery';
import 'imports-loader?window.jQuery=jquery,this=>window!widgster'; // eslint-disable-line

export default {
  name: 'Widget',
  props: {
    customHeader: { type: Boolean, default: false },
    tooltipPlacement: { default: 'top' },
    showTooltip: { type: Boolean, default: false },
    collapse: { type: [Boolean, String], default: false },
    className: { default: '' },
    title: { default: '' },
    bodyClass: { default: '' },
    options: { default: () => ({}) },
    dataWidgster: { type: Object, default: () => ({}) },
  },
  computed: {
    randomId() {
      return Math.floor(Math.random() * 100);
    },
  },
  mounted() {
    const { options } = this;
    options.bodySelector = '.widget-body';
    $(this.$refs.widget).widgster(options);
  },
};
</script>

<style src="./Widget.scss" lang="scss" />
