<template>
  <div>
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="课程封面">
        <el-input
          name="file"
          type="file"
          accept="image/png,image/gif,image/jpeg"
          @change="update"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="input" placeholder="请输入内容"></el-input>
      </el-form-item>
    </el-form>
    
    <el-button> 上传 </el-button>
  </div>
</template>

<script>
export default {
  name: "CourseBuild",
  props: {},
  mounted() {},
  data() {
    return {
      form: {
        courseCover: "",
        courseID:'',
        courseIntroduction:'',
        courseName:''
      },
    };
  },
  methods: {
    update(value) {
      this.form.courseCover = value.target.files[0];
    },
  },
};
</script>

<style>
</style>