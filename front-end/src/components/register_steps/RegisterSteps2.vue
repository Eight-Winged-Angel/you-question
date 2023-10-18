<template>
  <div>
    <el-card class="box-card" style="margin-top: 50px">
      <el-row>
        <div style="margin-bottom: 20px">填写基本信息</div>
      </el-row>
      <el-form
            ref="formRef"
            :model="form"
            :rules="formRules"
            label-width="0px"
            class="form"
            @blur="clearForm"
          >
            <el-form-item prop="name">
              <el-input
                placeholder="请输入用户名"
                v-model="form.name"
                prefix-icon="el-icon-user"
              ></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input
                id="codeInput"
                placeholder="请输入密码"
                v-model="form.password"
                type="password"
                prefix-icon="el-icon-lock"
              ></el-input>
            </el-form-item>
            
          </el-form>
      <!-- <el-row>
        <el-form ref="form" :model="form" label-width="50px">
          <el-form-item label="用户名:">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="密码:">
            <el-input v-model="form.password"></el-input>
          </el-form-item>
        </el-form>
      </el-row> -->
      <el-row>
        <el-col :span="16" :offset="4" style="margin-top: 30px">
          <el-button type="primary" @click="step3">下一步</el-button>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "RegisterSteps2",
  data() {
    return {
      form: {
        name: "",
        password: "",
      },
      formRules: {
        name: [
          // { required: true, message: "请输入正确格式的手机号", trigger: "blur" },
          // { min: 11, max: 11, message: "11位数字", trigger: "blur" },
        ],

        password : [
          { required: true, message: "请输入正确格式的密码", trigger: "blur" },
          { min: 6, max: 16, message: "长度为6位~16位", trigger: "blur" },
        ],

      },
    };
  },
  methods: {
    step3() {
      
      this.$refs.formRef.validate(async (valid) => {
        if (!valid) return;
      let token = window.sessionStorage.getItem("token");
      console.log("phhhhhhhhh")
      console.log(this.form,token,"oxxxxxxxx");
      axios
        .post("/auth/register", {
          name: this.form.name,
          password: this.form.password,
          token: token,
        })
        .then(function (resp) {
          console.log("注册成功", resp,this.form.name);
          // window.sessionStorage.setItem("token", resp.data.data);
        })
        .catch(function (resp) {
          console.log("注册失败", resp);
        });
      this.$bus.$emit("step2Finish", this.form);
    
  });
  },
  clearForm() {
      this.$refs.formRef.clearValidate();
    },
}}

</script>

<style scoped>
.el-row {
  margin-top: 10px;
  align-items: center;
}
</style>