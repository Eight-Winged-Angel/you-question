<template>
  <el-container style="background-color:#f4f4f4">
    <el-aside class="vh100"  style="width:20%;background-color: #eaeaea;">
      <base-teacher-nav></base-teacher-nav>
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
    <router-view></router-view>    
  </el-container>
</template>

<script>
import BaseBreadcrumb from '@/components/BaseBreadcrumb'
import BaseTeacherNav from '@/components/teacher/BaseTeacherNav'
import BaseTeacherCourse from '@/views/teacherCourse/BaseTeacherCourse'


export default {
  components: { BaseTeacherNav, BaseBreadcrumb, BaseTeacherCourse },
  data() {
    return {
      breadcrumbItems: [
        '我的课程',
        '114514',
        this.$route.params.id
      ],
     fits: [ 'cover', ],
     url:'https://img0.baidu.com/it/u=4042238712,2798828956&fm=253&fmt=auto&app=138&f=JPEG?w=610&h=457'

    }
  },

}
</script>

<style scoped>
.vh100 {
  height: 100vh;
}
.breadcrumb {
  margin: 0.5em 0;
}

</style>