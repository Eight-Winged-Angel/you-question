<template>
  <el-card class="box-card" style="margin-top: 50px; width:300px;">
    <div style="margin-bottom: 80px; ">注册成功！</div>
    <el-row>
      <el-col :span="16" :offset="4" style="margin-top: 30px">
        <el-button type="primary" @click="finish">完成</el-button>
      </el-col>
    </el-row>
  </el-card>
</template>

<script>
export default {
  name: "RegisterSteps3",
  data() {
    return {};
  },
  methods: {
    finish() {
      this.$bus.$emit("closeRegister");
    },
  },
};
</script>

<style scoped>
.el-row {
  margin-top: 10px;
  align-items: center;
}
</style>