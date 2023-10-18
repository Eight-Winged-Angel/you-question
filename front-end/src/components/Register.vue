<template>
  <div
    style="
      display: flex;
      flex-direction: column;
      align-items: center;
      width: 100%;
      height: 450px;
    "
  >
    <el-steps :active="active" simple style="width: 80%">
      <el-step title="手机验证" icon="el-icon-phone-outline"></el-step>
      <el-step title="基本信息" icon="el-icon-s-order"></el-step>
      <el-step title="完成" icon="el-icon-check"></el-step>
    </el-steps>

    <router-view></router-view>
  </div>
</template>

<script>
import { mapState, mapGetters, mapMutations, mapActions } from "vuex";

export default {
  name: "Register",
  props: ["newResgister"],
  mounted() {
    const that = this;
    console.log("@@@@");
    this.$bus.$on("step1Finish", (data) => {
      console.log("手机号验证完成", data);
      this.active = 2;
      that.$router.replace({
        name: "RegisterSteps2",
      });
    });
    this.$bus.$on("step2Finish", (data) => {
      console.log("注册账号完成", data);
      this.active = 3;
      that.$router.replace({
        name: "RegisterSteps3",
      });
    });
    this.$router.push({
      name: "RegisterSteps1",
    });
  },
  watch: {
    newResgister(newName, oldName) {
      if (oldName === false && newName === true) {
        console.log("register刷新");
        this.$router.push({
          name: "RegisterSteps1",
        });
        this.active = 1;
      }
    },
  },
  data() {
    return {
      active: 1,
    };
  },
  computed: {},
  methods: {},
};
</script>

<style>
</style>