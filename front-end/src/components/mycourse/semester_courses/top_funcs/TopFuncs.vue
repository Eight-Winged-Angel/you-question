<template>
  <div>
    <el-row>
      <el-col :span="4">
        {{ Semester.SemesterNum[0] }}-{{ Semester.SemesterNum[0] + 1 }} 第
        {{ Semester.SemesterNum[1] }} 学年
      </el-col>
      <el-col :span="2" :offset="16">
        <el-dropdown>
          <span class="el-dropdown-link">
            课堂排序<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>正序</el-dropdown-item>
            <el-dropdown-item>逆序</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-col>
      <el-col :span="2">
        <span @click="packUp">
          <i :class="IsPackUp ? IconClasses[0] : IconClasses[1]"></i>
          {{ IsPackUp ? PackUpText[0] : PackUpText[1] }}
        </span>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "TopFuncs",
  components: {},
  props: {
    Semester: {},
  },
  data() {
    return {
      IconClasses: ["el-icon-caret-bottom", "el-icon-caret-top"],
      IsPackUp: false,
      PackUpText: ["展开", "收起"],
    };
  },
  methods: {
    packUp() {
      this.IsPackUp = !this.IsPackUp;
    },
  },
};
</script>

<style>
</style>