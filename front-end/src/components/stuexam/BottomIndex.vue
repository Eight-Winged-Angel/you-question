<template>
  <div style="height: 100%">
    <el-card style="height: 100%" class="buttons">
      <el-button
        v-for="value of length"
        :key="value"
        :type="isActive(value)"
        @click="switchIndex(value)"
        style="margin-bottom: 8px"
      >
        {{ value }}
      </el-button>
    </el-card>
  </div>
</template>

<script>
export default {
  props: ["length", "index", "paper"],
  computed: {},
  data() {
    return {
      thePaper: this.paper,
    };
  },
  mounted() {
    this.theIndex = this.index;
    this.thePaper = this.paper;
    console.log("右侧导航", this.paper, this.length);
  },
  watch: {
    paper: {
      handler() {
        this.thePaper = this.paper;
      },
      deep: true,
    },
  },
  methods: {
    isActive(value) {
      console.log("isActive", "value", value);
      return this.thePaper[value - 1].answer === null ? "" : "success";
    },
    switchIndex(value) {
      this.theIndex = value - 1;
      // console.log("切换第",index,"题");
      let data = {
        toIndex: value - 1,
      };
      this.$bus.$emit("switchTopic", data);
    },
  },
};
</script>

<style scoped>
.buttons {
  display: flex;
  justify-content: flex-start;
  flex-wrap: wrap;
}
</style>