<template>
  <div>
    <el-row>
      <el-col :offset="1" :span="4">
          <el-button :type="answer===true?'success':''" @click="judge(true)">{{contents[0]}}</el-button>
      </el-col>
    </el-row>
    <el-row>
      <el-col :offset="1" :span="4">
          <el-button :type="answer===false?'success':''" @click="judge(false)">{{contents[1]}}</el-button>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  props: {
    checked: {
      type: Boolean,
      default: () => {
        let checked = false;
        return checked;
      },
    },
    type: { type: String, default: "单选题" },
  },
  data() {
    return {
      contents: ["正确", "错误"],
      answer: this.checked,
    };
  },
  watch: {
    answer() {
      this.$emit("update:checked", this.answer);
      console.log("checked变化", this.answer);
    },
  },
  methods: {
    judge(answer) {
      this.answer = answer
    },
  },
};
</script>

<style>
</style>