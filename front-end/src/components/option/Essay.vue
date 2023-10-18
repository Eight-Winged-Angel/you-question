<template>
  <div>
    <el-row>
      <el-col :offset="1" :span="22">
        <el-input
          v-model="gapAnswer"
          :placeholder="'请输入答案'"
          type="textarea"
          show-word-limit
          maxlength="255"
          autosize
          :clearable="true"
          @change="inputFinish"
          style="height:100px;"
        ></el-input>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  props: {
    checked: {
      type: String,
      default: () => {
        let checked = [];
        return checked;
      },
    },
  },
  mounted(){
    this.gapAnswer = this.checked;
  },
  data() {
    return {
      gapAnswer: "",
    };
  },
  watch: {
    checked() {
        this.gapAnswer = this.checked;
    },
  },
  methods: {
    inputFinish() {
      console.log("用户输入完毕");
      this.$emit('update:checked', this.gapAnswer)
    },
  },
};
</script>

<style>
</style>