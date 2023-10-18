<template>
  <div>
    <el-row class="a" type="flex" justify="center">
      <el-card style="width: 30%" shadow="always">
        <el-row>
          <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
            <el-tab-pane label="教师登录" name="teacher"></el-tab-pane>
            <el-tab-pane label="学生登录" name="student"></el-tab-pane>
          </el-tabs>
        </el-row>

        <el-row>
          <el-form label-position="right" label-width="80px" :model="account">
            <el-form-item label="账号">
              <el-input v-model="account.name"></el-input>
            </el-form-item>
            <el-form-item label="密码">
              <el-input v-model="account.password"></el-input>
            </el-form-item>
          </el-form>
        </el-row>

        <el-row type="flex" justify="end">
          <el-col :span="6" v-show="btnShow">
            <el-button type="danger" @click="logout">退出登录</el-button>
          </el-col>
          <el-col :span="6">
            <el-button type="success" @click="login">登录</el-button>
          </el-col>
        </el-row>
      </el-card>
    </el-row>
  </div>
</template>

<script>
import { mapState, mapGetters, mapMutations, mapActions } from "vuex"; //🔴

export default {
  name: "Login",
  props: [],
  data() {
    return {
      activeName: "teacher",
      account: {
        name: "",
        password: "",
      },
    };
  },
  computed: {
    ...mapState(["loginState"]),
    ...mapGetters([]),
    btnShow() {
      return this.loginState == "已登录";
    },
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    },
    login() {
      console.log("登录");
      let _this = this;
      if (this.account.name === "" || this.account.password === "") {
        alert("账号或密码不能为空");
      } else {
        // this.axios({
        //   method: 'post',
        //   url: '/user/login',
        //   data: _this.loginForm
        // }).then(res => {
        //   console.log(res.data);
        //   _this.userToken = 'Bearer ' + res.data.data.body.token;
        //   // 将用户token保存到vuex中
        //   _this.changeLogin({ Authorization: _this.userToken });
        //   _this.$router.push('/home');
        //   alert('登陆成功');
        // }).catch(error => {
        //   alert('账号或密码错误');
        //   console.log(error);
        // });
        _this.userToken = "Bearer " + "asd1235xxsd5z";
        _this.Change({ Authorization: _this.userToken });
        alert(
          `${this.activeName}登录,账号${this.account.name},密码${this.account.password}`
        );
        _this.$router.push({
          name: "Main",
        });
      }
    },
    logout() {
      alert("退出登录");
      console.log("退出登录");
      this.Change({ Authorization: "" });
    },
    ...mapMutations({ Change: "CHANGELOG" }),
    ...mapActions({ change: "changeLog" }),
  },
  mounted() {
    console.log("nihao");
  },
};
</script>

<style  scoped>
.a {
  width: 100%;
}
</style>