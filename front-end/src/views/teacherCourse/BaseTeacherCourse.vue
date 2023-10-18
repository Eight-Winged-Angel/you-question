<template>
  <el-container>
    <el-header style="width: 100%;background-color: ;">
      <base-breadcrumb
        class="breadcrumb"
        :items="breadcrumbItems"
        ref="breadcrumb"
      ></base-breadcrumb>
    </el-header>
    <el-main style="width: 100%;;">
      <el-tabs style="background-color:;">
        <el-tab-pane>
          <template slot="label">
            <span class="tab-title">课程简介</span>
          </template>
          <the-intro></the-intro>
        </el-tab-pane>
        <el-tab-pane >
          <template slot="label">
            <span class="tab-title">通知公告</span>
          </template>
          <the-notice></the-notice>
        </el-tab-pane>
        <el-tab-pane >
          <template slot="label">
            <span class="tab-title">课程练习</span>
          </template>
          <ThePractice/>
        </el-tab-pane>
        <el-tab-pane >
          <template slot="label">
            <span class="tab-title">学生名单</span>
          </template>
          <the-students style=""></the-students>
        </el-tab-pane>
      </el-tabs>
    </el-main>
  </el-container>
</template>

<script>
import BaseBreadcrumb from "@/components/BaseBreadcrumb";
import TheIntro from "@/views/teacherCourse/TheIntro";
import TheNotice from "@/views/teacherCourse/TheNotice";
import TheStudents from "@/views/teacherCourse/TheStudents";
import ThePractice from "@/views/teacherCourse/ThePractice"
export default {
  components: { TheNotice, TheIntro, TheStudents, BaseBreadcrumb,ThePractice },
  mounted(){
    console.log("面包屑：",this.breadcrumbItems)
  },
  watch: {
    $route: {
      handler() {
        this.breadcrumbItems[1] = this.$route.params.id;
        console.log("变化",this.breadcrumbItems);
        //深度监听
        this.$refs.breadcrumb.changeId(this.breadcrumbItems);
      },
      deep: true,
    },
  },
  data() {
    return {
      breadcrumbItems: ["我的课程", this.$route.params.id],
    };
  },
  methods:{}
};
</script>

<style scoped>
.breadcrumb {
  margin: 0.5em 0;
}

.tab-title {
  font-size: large;
  font-weight: bold;
  letter-spacing: 0.2em;
}
</style>