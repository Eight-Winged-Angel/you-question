<template>
  <div>
    <el-card :body-style="{ padding: '0px' }" style="width:348px; margin-top:30px;">
        <div style="background-color:#637b6f; width:100%; height:209px;">
            <div style="padding:55px 0  0 10px; ">{{Course.CourseName}}</div>           
            <div>课堂码:{{Course.CourseId}}</div>            
        </div>
        <div style="background-color:#fff; width:100%; height:111px;">
            <div>成员{{Course.Students.length}}人</div>           
                       
        </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "CourseCard",
  components: {},
  props: {
    Course: {},
  },
  data() {
    return {};
  },
  methods: {},
};
</script>

<style>
</style>