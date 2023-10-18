<template>
  <el-tag :type="type" :size="size">{{ name }}</el-tag>
</template>

<script>
export default {
  data() {
    return {
      type: 'primary'
    }
  },
  props: {
    name: { type: String },
    size: { type: String, default: 'mini' }
  },
  created() {
    this.render();
  },
  methods: {
    render() {
      switch (this.name) {
        case '单选题':
          this.type = 'primary'
          break
        case '多选题':
          this.type = 'success'
          break
        case '判断题':
          this.type = 'warning'
          break
        case '填空题':
          this.type = 'danger'
          break
        case '应用题':
        default:
          this.type = 'info'
      }
    }
  },
  watch: {
    name() {
      this.render();
    }
  }
}
</script>

<style scoped>

</style>