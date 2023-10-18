<template>
  <div>
    <el-row>
      <el-col :offset="1" :span="22">
        <el-input
          v-model="gapAnswer"
          :placeholder="'请输入答案，使用半角;分割多个答案'"
          type="textarea"
          show-word-limit
          maxlength="255"
          autosize
          :clearable="true"
          @change="inputFinish"
        ></el-input>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  props: {
    checked: {
      type: Array,
      default: () => {
        let checked = [];
        return checked;
      },
    },
  },
  mounted() {
    this.answerList = this.checked;
    this.gapAnswer = this.answerList.join(";");
  },
  data() {
    return {
      gapAnswer: "",
      answerList: [],
    };
  },
  watch: {
    checked() {
      this.answerList = this.checked;
      this.gapAnswer = this.answerList.join(";");
    },
  },
  methods: {
    inputFinish() {
      console.log("用户输入完毕");
      this.answerList = this.gapAnswer.split(";");
      this.$emit("update:checked", this.answerList);
    },
  },
};
</script>

<style>
</style>