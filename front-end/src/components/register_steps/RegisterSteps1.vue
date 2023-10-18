<template>
  <div>
    <el-card class="box-card" style="margin-top: 50px">
      <el-row>
        <div style="margin-bottom: 20px">验证手机号</div>
      </el-row>
      <el-row>
          <el-form
            ref="phoneAuthRef"
            :model="phoneAuth"
            :rules="phoneAuthRules"
            label-width="0px"
            class="phoneAuth"
            @blur="clearPhoneAuthForm"
          >
            <el-form-item prop="PhonesNum">
              <el-input
                placeholder="请输入手机号"
                v-model="phoneAuth.PhonesNum"
                prefix-icon="el-icon-phone"
              ></el-input>
            </el-form-item>
            <el-form-item prop="VerificationCode">
              <el-row :gutter="15">
                <el-col :span="15">
                  <el-input
                    id="codeInput"
                    placeholder="请输入验证码"
                    v-model="phoneAuth.VerificationCode"
                    prefix-icon="el-icon-setting"
                  ></el-input>
                </el-col>
                <el-col :span="6">
                  <el-button @click="getAuthCode">获取验证码</el-button>
                </el-col>
              </el-row>
            </el-form-item>
            
          </el-form>
          <el-row>
          <el-col :span="16" :offset="4" style="margin-top: 30px">
            <el-button type="primary" @click="step2">下一步</el-button>
          </el-col>
          </el-row>
        <!-- <el-row>
          <el-input v-model="phoneAuth.PhonesNum"></el-input>
        </el-row>
        <el-row>
          <el-col :span="14">
            <el-input v-model="phoneAuth.VerificationCode"></el-input>
          </el-col>
          <el-col :span="10">
            <el-button type="primary" @click="getAuthCode"
              >获取验证码</el-button
            >
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="16" :offset="4" style="margin-top: 30px">
            <el-button type="primary" @click="step2">下一步</el-button>
          </el-col>
        </el-row> -->
      </el-row>
    </el-card>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "RegisterSteps1",
  data() {
    return {
      phoneAuth: {
        PhonesNum: "",
        VerificationCode: "",
      },
      phoneAuthRules: {
        PhonesNum: [
          { required: true, message: "请输入正确格式的手机号", trigger: "blur" },
          { min: 11, max: 11, message: "11位数字", trigger: "blur" },
        ],

        VerificationCode: [
          { required: true, message: "请输入正确格式的验证码", trigger: "blur" },
          { min: 6, max: 6, message: "长度为6位", trigger: "blur" },
        ],

      },

    };
  },
  methods: {
    step2() {
      this.$refs.phoneAuthRef.validate(async (valid) => {
        if (!valid) return;
      axios
        .post("/auth/code/verify", {
          phone: this.phoneAuth.PhonesNum,
          code: this.phoneAuth.VerificationCode,
        })
        .then(function (resp) {
          console.log("成功，验证手机", resp);
          window.sessionStorage.setItem("token", resp.data.data);
        })
        .catch(function (resp) {
          console.log("失败，验证手机", resp);
        });

      this.$bus.$emit("step1Finish", this.phoneAuth);
      });
    },
    getAuthCode() {
      axios({
        method: "get",
        url: "/auth/code/send",
        params: { phone: this.phoneAuth.PhonesNum },
      })
        .then(function (resp) {
          console.log("发送验证码成功", resp);
        })
        .catch(function (resp) {
          console.log("发送验证码失败", resp);
        });
    },
    clearPhoneAuthForm() {
      this.$refs.phoneAuthRef.clearValidate();
    },
  },
};
</script>

<style scoped>
.el-row {
  margin-top: 10px;
}
</style>