<template>
  <div>
    <el-tabs
      v-model="activeName"
      @tab-click="handleClick"
      style="display: flex; justify-content: center"
    >
      <el-tab-pane name="first" label="我教的"></el-tab-pane>
      <el-tab-pane name="second" label="我协助的"></el-tab-pane>
      <el-tab-pane name="third" label="我学的"></el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
export default {
  name: "TagLeft",
  components: {},
  props: [],
  data() {
    return {
      activeName: "second",
    };
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    },
  },
};
</script>

<style>
</style>