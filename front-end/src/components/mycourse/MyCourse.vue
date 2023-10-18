<template>
  <div>
    <el-row>
      <el-col :span="4" :offset="20">
        <el-button type="primary" style="margin-top: 40px"
          >+加入/创建课程</el-button
        >
      </el-col>
    </el-row>
    <el-row style="margin-top: 45px">
      <el-col :span="6" style="">
        <TagLeft />
      </el-col>
      <el-col :span="2" :offset="12" style=""
        ><el-button>归档管理</el-button></el-col
      >
      <el-col :span="3" :offset="1" style="">
        <el-input
          placeholder="请输入内容"
          prefix-icon="el-icon-search"
          v-model="SearchCourse"
        >
        </el-input
      ></el-col>
    </el-row>
    <el-row> 
      <TopFuncs/>
    </el-row>
  </div>
</template>

<script>
import TagLeft from "./top_fun/TagLeft";
import TopFuncs from "./semester_courses/SemesterCourse";

export default {
  name: "MyCourse",
  components: { TagLeft, TopFuncs },
  props: [],
  data() {
    return {
      SearchCourse: "",
    };
  },
  methods: {},
};
</script>

<style>
</style>