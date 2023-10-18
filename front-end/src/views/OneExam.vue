<template>
  <div style="height: 550px">
    <el-row style="height: 100%">
      <el-col :span="18" style="height: 100%">
        <Exercise
          :exercise.sync="paper[active]"
          :index="active"
          :length="paper.length"
        />
      </el-col>
      <el-col :span="6" style="height: 100%">
        <BottomIndex :length="paper.length" :index="active" :paper="paper" />
      </el-col>
    </el-row>

    <el-dialog
      @close="submitAll"
      title="确认提交"
      :visible.sync="dialogVisible"
      width="30%"
    >
      <span>提交后无法撤回</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false"
          >确 定</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>``

<script>
import Exercise from "../components/stuexam/Exercise";
import BottomIndex from "../components/stuexam/BottomIndex";
import axios from "axios";

export default {
  components: {
    Exercise,
    BottomIndex,
  },
  props: [],
  data() {
    return {
      active: 0,
      dialogVisible: false,
      paper: [],
      practiceId: this.$store.state.practiceId,
    };
  },
  watch: {
    active: {
      handler(value) {
        console.log("active变化:value");
      },
    },
  },
  activated() {
    console.log("跳转成功");
    this.paper = this.$store.state.paper;
    console.log("做题", this.paper);
    this.$bus.$on("switchTopic", (value) => {
      this.active = value.toIndex;
    });
    this.$bus.$on("nextTopic", (value) => {
      this.active = value.toIndex;
    });
    this.$bus.$on("buttomSwitchTopic", (value) => {
      this.active = value.toIndex;
    });
    this.$bus.$on("submit", () => {
      this.dialogVisible = true;
    });
  },
  methods: {
    submitAll() {
      let that = this;

      for (let item of this.paper) {
        console.log("****", item);
        let data = {
          practiceId: this.practiceId,
          questionId: item.id,
          answer: item.answer,
        };
        axios
          .post("/student/course/practice/submit", data, {
            headers: {
              token: this.$store.state.token,
            },
          })
          .then((res) => {
            if (res.data.code === "00000") {
            }
          })
          .catch((resp) => {
            that.$message.error("提交答案错误");
          });
      }
      that.$message({
        message: "提交答案成功",
        type: "success",
      });
    },
  },
};
</script>

<style>
</style>