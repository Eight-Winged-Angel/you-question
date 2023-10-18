<template>
  <el-container >
    <!-- #4a5064 -->
    <el-header style="width: 100%;">
      <base-breadcrumb class="breadcrumb;" :items="breadcrumbItems "></base-breadcrumb>
    </el-header>
    <el-main style="width: 100% ;">
      <el-tabs >
        <el-tab-pane>
          <template slot="label">
            <span class="tab-title">课程简介</span>
          </template>
          <the-intro></the-intro>
        </el-tab-pane>
        <el-tab-pane class="tab-title">
          <template slot="label">
            <span class="tab-title">通知公告</span>
          </template>
          <the-notice></the-notice>
        </el-tab-pane>
        <el-tab-pane class="tab-title" >
          <template slot="label">
            <span class="tab-title">课程练习</span>
          </template>
        </el-tab-pane>
      </el-tabs>
    </el-main>
  </el-container>
</template>

<script>
import BaseBreadcrumb from '@/components/BaseBreadcrumb'
import TheIntro from '@/views/course/TheIntro'
import TheNotice from '@/views/course/TheNotice'

export default {
  components: { TheNotice, TheIntro, BaseBreadcrumb },
  data() {
    return {
      breadcrumbItems: [
        '我的课程',
        '114514',
        this.$route.params.id
      ]
    }
  }
}
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
