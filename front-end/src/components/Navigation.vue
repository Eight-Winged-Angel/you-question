<template>
  <div>
    <el-menu
      :default-active="activeIndex"
      class="el-menu-demo nav"
      mode="horizontal"
      @select="handleSelect"
    >
      <el-menu-item index="1" @click="toMain">我的课堂</el-menu-item>
      <el-menu-item index="2">备课中心</el-menu-item>
      <el-menu-item index="3">消息中心</el-menu-item>
      <el-menu-item index="4" @click="toLogin">{{ loginState }}</el-menu-item>
      <el-menu-item index="5" @click="toRegister">注册</el-menu-item>
    </el-menu>
  </div>
</template>

<script>
import { mapState, mapGetters, mapMutations, mapActions } from "vuex"; //🔴
export default {
  name: "Navigation",
  props: [],
  data() {
    return {
      activeIndex: "1",
      activeIndex2: "1",
    };
  },
  computed: {
    ...mapState(["loginState"]),
    ...mapGetters([]),
  },
  methods: {
    toLogin() {
      this.$router.push({
        name: "Login",
      });
    },
    toRegister(){
      this.$router.push({
        name: "Register",
      });
    },
    toMain() {
      this.$router.push({
        name: "Main",
      });
    },
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
  },
};
</script>

<style>
.nav{
  margin: 0px;
  height: 90px;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>