<template>
  <el-container style="background-color:#f4f4f4">
    <el-aside class="vh100" style="width:20%;background-color: #eaeaea;" >
      <base-student-nav></base-student-nav>
    </el-aside>
    <div class="demo-image" style="position:absolute;margin-right: 5%;margin-left:78%;margin-top:0">
      <div class="block" v-for="fit in fits" :key="fit" >
        <span class="demonstration"></span>
        <!-- <el-image
          style="width: 200px; height: 120px;"
          :src="url"
          :fit="fit"></el-image> -->
      </div>
    </div>
    <router-view />
  </el-container>
</template>

<script>
import BaseBreadcrumb from "@/components/BaseBreadcrumb";
import BaseStudentNav from "@/components/student/BaseStudentNav";
import BaseStudentCourse from "@/views/studentCourse/BaseStudentCourse";

export default {
  components: { BaseStudentNav, BaseStudentCourse, BaseBreadcrumb },
  
  data() {
    return {
      breadcrumbItems: ["我的课程", "114514", this.$route.params.id],
      fits: [ 'cover', ],
      url:'https://img0.baidu.com/it/u=4042238712,2798828956&fm=253&fmt=auto&app=138&f=JPEG?w=610&h=457'

    };
  },
  mounted() {},
  methods: {
    toExam() {
      console.log("去刷题");
      this.$router.push("/student/stuexam");
    },
    toCourse() {
      this.$router.back();
    },
    toInform() {
      this.$router.back();
    },
  },
};
</script>

<style scoped>
.vh100 {
  height: 100vh;
}
</style>