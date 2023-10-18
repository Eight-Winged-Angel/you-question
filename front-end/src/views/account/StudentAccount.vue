<template>
  <div style="width: 100%">
    <el-container class="el-container123456">
      <el-header style="width: 100%">
        <base-breadcrumb
          class="breadcrumb123456"
          :items="breadcrumbItems"
          ref="breadcrumb"
        ></base-breadcrumb>
      </el-header>
      <el-main style="width: 80%">
        <el-card style="margin-bottom: 40px">
          <el-row class="topInfo123456" style="width: 100%">
            <img
              :src="account.avatar"
              style="
                height: 100px;
                width: 100px;
                border-radius: 50px;
                margin-right: 20px;
              "
            />
            <div>
              <el-row>
                <div class="name123456">{{ account.name }}</div>
              </el-row>
              <el-row>
                <div style="color: rgb(104, 158, 255)">开通会员</div>
              </el-row>
            </div>
          </el-row>
        </el-card>

        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="账户信息" name="first">
            <el-card>
              <el-row>
                <el-col :span="3">
                  <div
                    style="
                      text-align: left;
                      padding-left: 10px;
                      margin-bottom: 25px;
                    "
                  >
                    基础信息
                  </div>
                </el-col>
                <el-col :offset="18" :span="3" style="display: flex">
                  <el-button
                    type="primary"
                    @click="handleEdit"
                    style="margin-bottom: 25px"
                    v-show="!isEdit"
                  >
                    编辑
                  </el-button>
                  <el-button
                    type="success"
                    @click="handleFinish"
                    style="margin-bottom: 25px"
                    v-show="isEdit"
                  >
                    完成
                  </el-button>
                </el-col>
              </el-row>

              <el-form
              class="el-form123456"
                ref="form"
                label-width="110px"
                label-position="left"
                :disabled="!isEdit"
              >
                <el-form-item label="姓名" style="padding-left: 35px">
                  <el-input class="el-input123456" v-model="account.name"></el-input> </el-form-item
                ><el-form-item label="学号" style="padding-left: 35px">
                  <el-input class="el-input123456" v-model="account.stuId"></el-input> </el-form-item
                ><el-form-item label="学校" style="padding-left: 35px">
                  <el-input class="el-input123456" v-model="account.college"></el-input> </el-form-item
                ><el-form-item label="学院" style="padding-left: 35px">
                  <el-input class="el-input123456" v-model="account.school"></el-input> </el-form-item
                ><el-form-item label="专业" style="padding-left: 35px">
                  <el-input class="el-input123456" v-model="account.major"></el-input> </el-form-item
                ><el-form-item label="班级" style="padding-left: 35px">
                  <el-input class="el-input123456" v-model="account.class"></el-input> </el-form-item
                ><el-form-item label="年级" style="padding-left: 35px">
                  <el-input class="el-input123456" v-model="account.grade"></el-input> </el-form-item
              ></el-form>
            </el-card>
          </el-tab-pane>
          <el-tab-pane label="通知设置" name="second" disabled="false">
          </el-tab-pane>
        </el-tabs>
      </el-main>
      <el-footer>
        <el-button type="danger" round icon="el-icon-my-export" @click="logout">退出登录</el-button>
      </el-footer>
    </el-container>
  </div>
</template>

<script>
import BaseBreadcrumb from "@/components/BaseBreadcrumb";
export default {
  components: { BaseBreadcrumb },
  props: [],
  data() {
    return {
      breadcrumbItems: ["个人中心"],
      activeName: "first",
      account: {
        name: "张三",
        avatar: require("../../assets/love.png"),
        stuId: "2020123554",
        college: "北京邮电大学",
        school: "计算机学院",
        major: "软件工程",
        class: "202211318",
        grade: 3,
      },
      isEdit: false,
    };
  },
  methods: {
    handleEdit() {
      console.log("修改信息");
      this.isEdit = true;
    },
    handleFinish() {
      this.isEdit = false;
    },
    logout(){
      console.log("退出登录");
      sessionStorage.removeItem("token");
      this.$router.replace('/');
    }
  },
};
</script>

<style>
.name123456 {
  font-size: 23px;
  margin-bottom: 10px;
  text-align: left;
}
.topInfo123456 {
  display: flex;
  align-items: center;
}
.breadcrumb123456 {
  margin: 0.5em 0;
}
.el-container123456 {
  widows: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.el-form123456 {
  background: #fff;
  box-shadow: 2px 2px 2px 2px #ccc;
  border-radius: 10px;
  border-left: solid 5px #4285f4;
  padding: 10px;
}
.el-input123456 {
  background-color: rgb(244, 244, 244);
}
.el-icon-my-export {
  background: url("@/assets/logout2.png");
  font-size: 16px;
  background-size: cover;
}
.el-icon-my-export:before{
  content: "替";
  font-size: 16px;
  visibility: hidden;
}
</style>