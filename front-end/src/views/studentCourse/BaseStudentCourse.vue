<template>
  <el-container>
    <el-header style="width: 100%">
      <base-breadcrumb
        class="breadcrumb"
        :items="breadcrumbItems"
        ref="breadcrumb"
      ></base-breadcrumb>
    </el-header>

    <el-main style="width: 100%">
      <el-tabs :before-leave="leaveTab">
        <el-tab-pane>
          <template slot="label">
            <span class="tab-title">课程简介</span>
          </template>
          <the-intro></the-intro>
        </el-tab-pane>
        <el-tab-pane :disabled="this.disabled">
          <template slot="label">
            <span class="tab-title">通知公告</span>
          </template>
          <the-notice></the-notice>
        </el-tab-pane>
        <el-tab-pane :disabled="this.disabled">
          <template slot="label">
            <span class="tab-title" @click="courseTest">课程练习</span>
          </template>
          <keep-alive>
            <router-view></router-view>
          </keep-alive>
        </el-tab-pane>
      </el-tabs>
    </el-main>
  </el-container>
</template>


<script>
import BaseBreadcrumb from "@/components/BaseBreadcrumb";
import TheIntro from "@/views/studentCourse/TheIntro";
import TheNotice from "@/views/studentCourse/TheNotice";

export default {
  components: { TheNotice, TheIntro, BaseBreadcrumb },
  data() {
    return {
      disabled:true,
      breadcrumbItems: ["我的课程", this.$route.params.id],
      courseId: this.$route.params.id,
    }},
  mounted() {
    console.log("面包屑：", this.breadcrumbItems);
    this.$store.commit("SETCOUERSID", this.courseId);
    console.log("store存储：", this.$store.state.currentCourseID);
    if(this.$store.state.notSelected==true){
      this.disabled=false;
    }
    else{this.disabled = true}
    
  },
  computed:{
    nSelected(){
      return this.$store.state.notSelected
    }
  },
  watch: {
    nSelected(n,o){
      if(n ==true)
      this.disabled = false;
      else
      this.disabled = true
        
      },
      deep: true,
      // immediate:true
    
    $route: {
      handler() {
        this.breadcrumbItems[1] = this.$route.params.id;
        console.log("变化",this.breadcrumbItems,"thisssssss");
        this.$store.commit("SETCOUERSID", this.$route.params.id);
        //深度监听
        this.$refs.breadcrumb.changeId(this.breadcrumbItems);
      },
      deep: true,
    },
  },

  methods: {
    courseTest() {
      console.log("跳转", this.courseId);
      this.$router.push({ name: "coursepractice" });
    },
    leaveTab(activeName, oldActiveName) {
      console.log("activeName", activeName, "oldActiveName", oldActiveName);
      if (oldActiveName === "2" && activeName !== "2") {
        this.$router.back();
      }
    }, //tabs切换时的钩子
  },
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